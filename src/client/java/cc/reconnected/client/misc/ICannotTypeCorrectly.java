package cc.reconnected.client.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ICannotTypeCorrectly {
    public static final String[] MESSAGES = new String[]{
            "I cannot type correctly.",
            "I cannot type.",
            "I cannot type at all."
    };

    public static String dyslexia(String address) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(address);
        int ops = rand.nextInt(2) + 1;

        for (int i = 0; i < ops; i++) {
            List<Integer> candidates = new ArrayList<>();
            for (int j = 0; j < sb.length(); j++) {
                char c = sb.charAt(j);
                if (Character.isLetterOrDigit(c)) candidates.add(j);
            }
            if (candidates.isEmpty()) break;

            int idx = candidates.get(rand.nextInt(candidates.size()));
            int op = rand.nextInt(2);

            if (op == 0) {
                sb.deleteCharAt(idx);
            } else {
                List<Integer> updated = new ArrayList<>();
                for (int j = 0; j < sb.length(); j++) {
                    if (Character.isLetterOrDigit(sb.charAt(j))) updated.add(j);
                }
                int pos = updated.indexOf(idx);
                if (pos >= 0 && pos + 1 < updated.size()) {
                    int nextIdx = updated.get(pos + 1);
                    char tmp = sb.charAt(idx);
                    sb.setCharAt(idx, sb.charAt(nextIdx));
                    sb.setCharAt(nextIdx, tmp);
                }
            }
        }

        return sb.toString();
    }
}
