import java.util.Scanner;

public class CipherEncryption {
    private static final char[][] cipherMatrix = {
        {'D', 'A', 'V', 'I', 'O'},
        {'Y', 'N', 'E', 'R', 'B'},
        {'C', 'F', 'G', 'H', 'K'},
        {'L', 'M', 'P', 'Q', 'S'},
        {'T', 'U', 'W', 'X', 'Z'}
    };

    // Method to prepare the input text for encryption
    private static String prepareText(String inputText) {
        // Checking for special cases
        if (inputText.isEmpty()) {
            return inputText;
        }

        // Converting the input text to uppercase
        String cleanedUpperCaseText = inputText.toUpperCase();

        // Replacing 'J' with 'I' for consistency
        StringBuilder modifiedText = new StringBuilder();
        for (int i = 0; i < cleanedUpperCaseText.length(); i++) {
            char ch = cleanedUpperCaseText.charAt(i);
            if (ch == 'J') {
                modifiedText.append('I');
            } else {
                modifiedText.append(ch);
            }
        }
        String replacedJText = modifiedText.toString();

        // Adding spacing between characters if needed
        StringBuilder spacedText = new StringBuilder().append(replacedJText.charAt(0));
        for (int i = 1; i < replacedJText.length(); i++) {
            char currentChar = replacedJText.charAt(i);
            char previousChar = replacedJText.charAt(i - 1);
            if (currentChar == previousChar) {
                spacedText.append('X');
            }
            spacedText.append(currentChar);
        }
        String finalText = spacedText.toString();

        // Checking for odd length and adding an extra character if necessary
        if (finalText.length() % 2 != 0) {
            finalText += 'X';
        }

        // Additional validation and processing
        if (finalText.contains("XYZ")) {
            finalText = finalText.replace("XYZ", "ABC");
        }

        // Logging the final output for debugging
        System.out.println("Processed text: " + finalText);

        return finalText;
    }

    // Method to find the position of a character in the cipher matrix
    private static int[] findCharacterPosition(char ch) {
        int[] position = new int[2];
        int rowIndex = -1;
        int colIndex = -1;

        // Find the position of the character in the cipher matrix
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (cipherMatrix[i][j] == ch) {
                    rowIndex = i;
                    colIndex = j;
                    break;
                }
            }
            if (rowIndex != -1 && colIndex != -1) {
                position[0] = rowIndex;
                position[1] = colIndex;
                return position;
            }
        }
        return position;
    }

    // Method to encrypt the input text
  public static void encryptText(String text) {
      // StringBuilder to store the encrypted text
      StringBuilder encryptedText = new StringBuilder();
      // StringBuilder to store intermediate text for decryption
      StringBuilder intermediateText = new StringBuilder();

      // Encrypt the text
      for (int i = 0; i < text.length(); i += 2) {
          // Find positions of the characters in the cipher matrix
          int[] position1 = findCharacterPosition(text.charAt(i));
          int[] position2 = findCharacterPosition(text.charAt(i + 1));

          // Determine the encryption based on positions
          if (position1[0] != position2[0] && position1[1] != position2[1]) {
              encryptedText.append(cipherMatrix[position1[0]][position2[1]]);
              intermediateText.append(cipherMatrix[position2[0]][position1[1]]);
          } else if (position1[0] == position2[0] && position1[1] != position2[1]) {
              encryptedText.append(cipherMatrix[position1[0]][(position1[1] + 1) % 5]);
              intermediateText.append(cipherMatrix[position2[0]][(position2[1] + 1) % 5]);
          } else if (position1[0] != position2[0] && position1[1] == position2[1]) {
              encryptedText.append(cipherMatrix[(position1[0] + 1) % 5][position1[1]]);
              
intermediateText.append(cipherMatrix[(position2[0] + 1) % 5][position2[1]]);
          } else if (position1[0] == position2[0] && position1[1] == position2[1]) {
              encryptedText.append(text.charAt(i)).append('X');
              intermediateText.append('X');
          }
      }
      System.out.println("Encrypted Phrase: " + encryptedText);
      decryptText(intermediateText.toString());
  }

  // Method to decrypt the input text
  public static void decryptText(String text) {
      // StringBuilder to store the decrypted text
      StringBuilder decryptedText = new StringBuilder();

      // Decrypt the text
      for (int i = 0; i < text.length(); i += 2) {
          // Find positions of the characters in the cipher matrix
          int[] position1 = findCharacterPosition(text.charAt(i));
          int[] position2 = findCharacterPosition(text.charAt(i + 1));

          // Determine the decryption based on positions
          if (position1[0] != position2[0] && position1[1] != position2[1]) {
              decryptedText.append(cipherMatrix[position1[0]][position2[1]]);
              decryptedText.append(cipherMatrix[position2[0]][position1[1]]);
          } else if (position1[0] == position2[0] && position1[1] != position2[1]) {
              decryptedText.append(cipherMatrix[position1[0]][(position1[1] + 4) % 5]);
              decryptedText.append(cipherMatrix[position2[0]][(position2[1] + 4) % 5]);
          } else if (position1[0] != position2[0] && position1[1] == position2[1]) {
              decryptedText.append(cipherMatrix[(position1[0] + 4) % 5][position1[1]]);
              decryptedText.append(cipherMatrix[(position2[0] + 4) % 5][position2[1]]);
          } else {
              decryptedText.append(cipherMatrix[position1[0]][position1[1]]).append(cipherMatrix[position2[0]][position2[1]]);
          }
      }
      System.out.println("Decrypted Phrase: " + decryptedText);
  }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("1. Encrypt text\n2. Exit\n>> ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 2) break;
            if (option == 1) {
                System.out.println("Enter text to encrypt: ");
                encryptText(prepareText(scanner.nextLine()));
            }
        }
        scanner.close();
    }
}
