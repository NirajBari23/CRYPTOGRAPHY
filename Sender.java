package CRYPTOGRAPHY;
import java.io.OutputStream;
import java.net.Socket;

public class Sender {
    public static void main(String[] args) {
        String word = System.getenv("WORD").toUpperCase();
        String direction = System.getenv("DIRECTION").toUpperCase();

        String encryptedWord = encrypt(word, direction);
        System.out.println("Original word: " + word);
        System.out.println("Encrypted word: " + encryptedWord);

        // Send encrypted word to receiver
        try (Socket socket = new Socket("receiver", 12345); // Connect to the receiver service
             OutputStream out = socket.getOutputStream()) {
            out.write(encryptedWord.getBytes());
            System.out.println("Message sent to receiver.");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
