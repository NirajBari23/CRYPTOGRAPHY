package CRYPTOGRAPHY;
import java.util.Scanner;

public class CaesarCipherLR {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine().toUpperCase();
        

        // Get user input for the L/R shift
        System.out.println("Enter shift for all characters (format L/R<number>, e.g., L2 or R4): ");
        String direction = scanner.nextLine().toUpperCase();

        String encryptedWord = encrypt(word, direction);
        System.out.println("Original word: " + word);
        System.out.println("Encrypted word: " + encryptedWord);

        scanner.close();
    }

    public static String encrypt(String word, String direction) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : word.toCharArray()) {                 //W
            char encryptedChar = shiftChar(c, direction);       //
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