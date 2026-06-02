package cc.reconnected.client.misc;

import net.minecraft.client.MinecraftClient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

public class FilterList {
    private static final List<String> BLACKLIST = List.of("6799efcd3b9f3f86eeffe23de95d439a695620247b2ab951b8c987ecc2f1373ced88f4475192383d8f84a27d9e2104d8451ae5a339af49d683206a32e25d5dc5","6a86e5f651b0fb60f6e7a2935ebf3fdedb44d2ffe8c5a0e04ca3aad2977c0010724619ff0dd6e61b41e62a98140a8aed65ef9ab2400a10baf4b457ea4ab0b66a");
    private static final List<String> BYPASS = List.of("6ae36085ce66c9bfaac2aa73baa13353c7b03cea8a8fa5e55e6fb33df79c8e431c14b3f7353869a47d8e3051ed02117432563bcd851edf916895515def5ba974");

    private static final String ENV_NAME = "FILTER_PW";
    private static final String PW = "250aecf4eb66aa30342b8d6d23a744996c1c25b4bff5be2f5c0c5fce8fd58fabeb8a7d6d8b2907035b6311766675d4e9fbf25da9ac5e553bf491b83afde2662b";

    public static boolean isFiltered(String name) {
        try {
            var pw = System.getenv(ENV_NAME);
            if (pw != null) {
                var digest = sha512(pw);
                if (digest.equals(PW)) {
                    return false;
                }
            }
        } catch (Exception e) {
        }

        var uuid = MinecraftClient.getInstance().getSession().getUuidOrNull();
        var uuidHash = sha512(String.valueOf(uuid));
        var nameHash = sha512(name);

        return BLACKLIST.contains(nameHash) && !BYPASS.contains(uuidHash);
    }

    private static String sha512(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
