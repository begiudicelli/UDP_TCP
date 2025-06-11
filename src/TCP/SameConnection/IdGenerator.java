package TCP.SameConnection;

import java.util.Random;

public class IdGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 5;
    private static final Random random = new Random();

    public static String generateClientId() {
        StringBuilder id = new StringBuilder("Cliente-");
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(index));
        }
        return id.toString();
    }
}
