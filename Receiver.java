package CRYPTOGRAPHY;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Receiver is listening on port 12345...");
            Socket socket = serverSocket.accept();
            System.out.println("Connection established with sender.");

            InputStream in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String encryptedWord = new String(buffer, 0, bytesRead);
            System.out.println("Encrypted message received: " + encryptedWord);

            String direction = System.getenv("DIRECTION").toUpperCase();
            String decryptedWord = decrypt(encryptedWord, direction);
            System.out.println("Decrypted word: " + decryptedWord);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decrypt(String word, String direction) {
        // Inverse the direction for decryption
        char inverseDirection = direction.charAt(0) == 'L' ? 'R' : 'L';
        String inverseShift = inverseDirection + direction.substring(1);
        return encrypt(word, inverseShift);
    }

    public static String encrypt(String word, String direction) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : word.toCharArray()) {
            char encryptedChar = shiftChar(c, direction);
            encrypted.append(encryptedChar);
        }

        return encrypted.toString();
    }

    public static char shiftChar(char c, String direction) {
        int shift = Integer.parseInt(direction.substring(1));
        if (direction.charAt(0) == 'L') {
            return (char) ((c - 'A' - shift + 26) % 26 + 'A');
        } else if (direction.charAt(0) == 'R') {
            return (char) ((c - 'A' + shift) % 26 + 'A');
        } else {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
}
