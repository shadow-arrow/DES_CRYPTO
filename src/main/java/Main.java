import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*String text = "0123456789ABCDEF";
        String key = "133457799BBCDFF1";*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("------- The DES Algorithm Illustrated --------");
        System.out.println("Enter plain text: ");
        String context = scanner.nextLine();
        System.out.println("Enter key: ");
        String key = scanner.nextLine();
        Solution cipher = new Solution();
        System.out.println("------- Encryption: --------" + "\n");
        System.out.println("Encrypt Text:" + context);
        System.out.println("Key:" + key);
        context = cipher.encrypt(context, key);
        System.out.println("Cipher Text: " + context + "\n");
        System.out.println("-------- Decryption --------" + "\n");
        System.out.println("Decrypt Text:" + context);
        System.out.println("Key:" + key);
        context = cipher.decrypt(context, key);
        System.out.println("Plain Text: " + context + "\n");
    }
}
