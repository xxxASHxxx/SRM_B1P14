/**
 * ==========================================================
 * MAIN CLASS - PalindromeCheckerApp
 * ==========================================================
 *
 * Use Case 1: Application Entry & Welcome Message
 *
 * Description:
 * This class represents the entry point of the
 * Palindrome Checker Management System.
 *
 * At this stage, the application:
 * - Starts execution from the main() method
 * - Displays a welcome message
 * - Shows application version
 *
 * No palindrome logic is implemented yet.
 *
 * The goal is to establish a clear startup flow.
 *
 * @author Developer
 * @version 1.0
 */
public class PalindromeCheckerApp {

    /**
     * Application entry point.
     *
     * This is the first method executed by the JVM
     * when the program starts.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // UC1 - Welcome Message
        System.out.println("==========================================");
        System.out.println("     Welcome to Palindrome Checker App   ");
        System.out.println("     Version : 1.0                       ");
        System.out.println("==========================================");
        System.out.println("Application started successfully.");

        // UC2 - Hardcoded Palindrome
        checkHardcodedPalindrome();

        // UC3 - Palindrome Check Using String Reverse
        checkUsingStringReverse();

        // UC4 - Character Array Based Palindrome Check
        checkUsingCharArray();

        // UC5 - Stack-Based Palindrome Checker
        checkUsingStack();

        // UC6 - Queue + Stack Based Palindrome Check
        checkUsingQueueAndStack();

        // UC7 - Deque-Based Optimized Palindrome Checker
        checkUsingDeque();
    }

    // ==========================================
    // UC2: Hardcoded Palindrome Result
    // ==========================================
    public static void checkHardcodedPalindrome() {

        System.out.println("\n==========================================");
        System.out.println("  UC2: Hardcoded Palindrome Check");
        System.out.println("==========================================");

        // Hardcoded string stored as a String literal
        String word = "madam";

        // Reverse the string
        String reversed = new StringBuilder(word).reverse().toString();

        // Conditional check
        if (word.equals(reversed)) {
            System.out.println("\"" + word + "\" is a Palindrome.");
        } else {
            System.out.println("\"" + word + "\" is NOT a Palindrome.");
        }
    }

    // ==========================================
    // UC3: Palindrome Check Using String Reverse
    // ==========================================
    public static void checkUsingStringReverse() {
        // TODO: UC3 logic here
    }

    // ==========================================
    // UC4: Character Array Based Palindrome Check
    // ==========================================
    public static void checkUsingCharArray() {
        // TODO: UC4 logic here
    }

    // ==========================================
    // UC5: Stack-Based Palindrome Checker
    // ==========================================
    public static void checkUsingStack() {
        // TODO: UC5 logic here
    }

    // ==========================================
    // UC6: Queue + Stack Based Palindrome Check
    // ==========================================
    public static void checkUsingQueueAndStack() {
        // TODO: UC6 logic here
    }

    // ==========================================
    // UC7: Deque-Based Optimized Palindrome Checker
    // ==========================================
    public static void checkUsingDeque() {
        // TODO: UC7 logic here
    }
}
