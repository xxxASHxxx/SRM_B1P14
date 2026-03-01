import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

        String word = "madam";
        String reversed = new StringBuilder(word).reverse().toString();

        if (word.equals(reversed)) {
            System.out.println("\"" + word + "\" is a Palindrome.");
        } else {
            System.out.println("\"" + word + "\" is NOT a Palindrome.");
        }
    }

    // ==========================================
    // UC3: Palindrome Check Using String Reverse
    // ==========================================
    /**
     * Checks palindrome by reversing string using a for loop
     * and string concatenation, then comparing with original.
     *
     * Key Concepts: for loop, String immutability,
     * String concatenation (+), equals() method
     */
    public static void checkUsingStringReverse() {

        System.out.println("\n==========================================");
        System.out.println("  UC3: String Reverse Based Palindrome Check");
        System.out.println("==========================================");

        String original = "racecar";
        String reversed = "";

        for (int i = original.length() - 1; i >= 0; i--) {
            reversed = reversed + original.charAt(i);
        }

        System.out.println("Original : " + original);
        System.out.println("Reversed : " + reversed);

        if (original.equals(reversed)) {
            System.out.println("\"" + original + "\" is a Palindrome.");
        } else {
            System.out.println("\"" + original + "\" is NOT a Palindrome.");
        }
    }

    // ==========================================
    // UC4: Character Array Based Palindrome Check
    // ==========================================
    /**
     * Validates palindrome by converting string to char array
     * and applying the two-pointer technique.
     *
     * Key Concepts: char[], array indexing,
     * two-pointer technique, time complexity awareness
     */
    public static void checkUsingCharArray() {

        System.out.println("\n==========================================");
        System.out.println("  UC4: Character Array Based Palindrome Check");
        System.out.println("==========================================");

        String input = "radar";
        char[] chars = input.toCharArray();

        int start = 0;
        int end = chars.length - 1;
        boolean isPalindrome = true;

        while (start < end) {
            if (chars[start] != chars[end]) {
                isPalindrome = false;
                break;
            }
            start++;
            end--;
        }

        System.out.println("Input : " + input);

        if (isPalindrome) {
            System.out.println("\"" + input + "\" is a Palindrome.");
        } else {
            System.out.println("\"" + input + "\" is NOT a Palindrome.");
        }
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
    /**
     * Validates palindrome using Queue (FIFO) and Stack (LIFO).
     * Characters are enqueued and pushed, then dequeued and
     * popped simultaneously for comparison.
     *
     * Key Concepts: Queue, Stack, FIFO, LIFO,
     * enqueue/dequeue, push/pop, logical comparison
     */
    public static void checkUsingQueueAndStack() {

        System.out.println("\n==========================================");
        System.out.println("  UC6: Queue + Stack Based Palindrome Check");
        System.out.println("==========================================");

        String input = "civic";

        Queue<Character> queue = new LinkedList<>();
        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            queue.add(c);
            stack.push(c);
        }

        boolean isPalindrome = true;

        while (!queue.isEmpty()) {
            char fromQueue = queue.poll();
            char fromStack = stack.pop();

            if (fromQueue != fromStack) {
                isPalindrome = false;
                break;
            }
        }

        System.out.println("Input : " + input);

        if (isPalindrome) {
            System.out.println("\"" + input + "\" is a Palindrome.");
        } else {
            System.out.println("\"" + input + "\" is NOT a Palindrome.");
        }
    }

    // ==========================================
    // UC7: Deque-Based Optimized Palindrome Checker
    // ==========================================
    /**
     * Validates palindrome using a Deque (Double Ended Queue).
     * Characters are inserted, then removed from both front
     * and rear simultaneously for direct comparison.
     *
     * Key Concepts: Deque, ArrayDeque, removeFirst(),
     * removeLast(), front and rear access, optimized traversal
     */
    public static void checkUsingDeque() {

        System.out.println("\n==========================================");
        System.out.println("  UC7: Deque-Based Optimized Palindrome Check");
        System.out.println("==========================================");

        // Define the input string
        String input = "refer";

        // Create a Deque to store characters
        Deque<Character> deque = new ArrayDeque<>();

        // Add each character to the rear of the deque
        for (char c : input.toCharArray()) {
            deque.addLast(c);
        }

        // Flag to track palindrome result
        boolean isPalindrome = true;

        // Continue comparison while more than one element exists
        // removeFirst() → takes from front
        // removeLast()  → takes from rear
        while (deque.size() > 1) {
            char front = deque.removeFirst();
            char rear  = deque.removeLast();

            if (front != rear) {
                isPalindrome = false;
                break;
            }
        }

        System.out.println("Input : " + input);

        if (isPalindrome) {
            System.out.println("\"" + input + "\" is a Palindrome.");
        } else {
            System.out.println("\"" + input + "\" is NOT a Palindrome.");
        }
    }
}
