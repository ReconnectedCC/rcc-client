package cc.reconnected.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Pattern;

public class Utils {
    public static final Pattern VANILLA = Pattern.compile("^<(?<user>.+)> (?<text>.*)$");
    public static final Pattern MODERN = Pattern.compile("^(?<user>.+): (?<text>.*)$");
    public static final Pattern USERNAME = Pattern.compile("[^A-Za-z0-9_]");

    public static boolean isMentioning(String input) {
        var message = parseMessage(input);
        var profile = MinecraftClient.getInstance().getSession().getProfile();
        var username = profile.getName().toLowerCase();
        if (message.chat) {
            if (message.user.toLowerCase().equals(username)) {
                return false;
            }

            return message.message.toLowerCase().contains(username);
        }
        return false;
    }

    public static ChatMessage parseMessage(String input) {
        if (VANILLA.matcher(input).matches()) {
            var message = extract(VANILLA, input);
            if (message != null) {
                return message;
            }
        } else if (MODERN.matcher(input).matches()) {
            var message = extract(MODERN, input);
            if (message != null) {
                return message;
            }
        }
        return new ChatMessage("System", input, false);
    }

    public static ChatMessage extract(Pattern pattern, String input) {
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            var user = matcher.group("user");
            var text = matcher.group("text");
            user = user.replaceAll(USERNAME.pattern(), "");
            return new ChatMessage(user, text, true);
        }
        return null;
    }

    public record ChatMessage(String user, String message, boolean chat) {
    }

    public static Text renderCurrency(long cents){
        String centsStr = String.valueOf(cents);
        centsStr = String.format("%1$3s",centsStr).replace(" ","0");//Pad zeroes left if necessary
        centsStr = new StringBuilder(centsStr).insert(centsStr.length()-2, ".").toString();
        if (centsStr.endsWith("00")) {
            centsStr = centsStr.substring(0,centsStr.length()-3);
        }

        return Text.literal(centsStr);
    }
}
