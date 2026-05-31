package cc.reconnected.client.misc;

import net.minecraft.client.MinecraftClient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

public class FilterList {
    private static final List<String> BLACKLIST = List.of("6799efcd3b9f3f86eeffe23de95d439a695620247b2ab951b8c987ecc2f1373ced88f4475192383d8f84a27d9e2104d8451ae5a339af49d683206a32e25d5dc5");
    private static final List<String> BYPASS = List.of("6ae36085ce66c9bfaac2aa73baa13353c7b03cea8a8fa5e55e6fb33df79c8e431c14b3f7353869a47d8e3051ed02117432563bcd851edf916895515def5ba974");

    public static boolean isFiltered(String name) {
        var uuid = MinecraftClient.getInstance().getSession().getUuid();
        var uuidHash = sha512(uuid);
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
