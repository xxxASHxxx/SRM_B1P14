package WEEK1and2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UsernameAvailabilityChecker {
    // O(1) lookup for username existence
    private final ConcurrentHashMap<String, Long> usernameToUserId;
    // Track attempt frequency
    private final ConcurrentHashMap<String, AtomicLong> attemptFrequency;
    private long nextUserId;

    public UsernameAvailabilityChecker() {
        this.usernameToUserId = new ConcurrentHashMap<>();
        this.attemptFrequency = new ConcurrentHashMap<>();
        this.nextUserId = 1L;
    }

    // O(1) availability check
    public boolean checkAvailability(String username) {
        // Track attempt
        attemptFrequency.computeIfAbsent(username, k -> new AtomicLong(0))
                .incrementAndGet();
        return !usernameToUserId.containsKey(username);
    }

    // Register new username
    public boolean registerUsername(String username, long userId) {
        if (usernameToUserId.containsKey(username)) {
            return false;
        }
        usernameToUserId.putIfAbsent(username, userId);
        return usernameToUserId.get(username).equals(userId);
    }

    // Suggest alternatives if username taken
    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        // Numeric suffixes
        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!usernameToUserId.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        // Dot separator
        int lastUnderscore = username.lastIndexOf('_');
        if (lastUnderscore > 0) {
            String dotVersion = username.substring(0, lastUnderscore) + "." +
                    username.substring(lastUnderscore + 1);
            if (!usernameToUserId.containsKey(dotVersion)) {
                suggestions.add(dotVersion);
            }
        }

        // Underscore variations
        String underscoreVersion = username + "_official";
        if (!usernameToUserId.containsKey(underscoreVersion)) {
            suggestions.add(underscoreVersion);
        }

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {
        return attemptFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue(Comparator.comparing(AtomicLong::get)))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // Get attempt count for username
    public long getAttemptCount(String username) {
        AtomicLong count = attemptFrequency.get(username);
        return count != null ? count.get() : 0;
    }

    public static void main(String[] args) {
        UsernameAvailabilityChecker checker = new UsernameAvailabilityChecker();

        // Register some users
        checker.registerUsername("john_doe", 1L);
        checker.registerUsername("jane_smith", 2L);
        checker.registerUsername("admin", 3L);

        // Test availability
        System.out.println("john_doe available: " + checker.checkAvailability("john_doe")); // false
        System.out.println("jane_smith available: " + checker.checkAvailability("jane_smith")); // false
        System.out.println("new_user available: " + checker.checkAvailability("new_user")); // true

        // Simulate multiple attempts
        for (int i = 0; i < 10543; i++) {
            checker.checkAvailability("admin");
        }

        // Get suggestions
        System.out.println("Suggestions for john_doe: " + checker.suggestAlternatives("john_doe"));

        // Most attempted
        System.out.println("Most attempted: " + checker.getMostAttempted() +
                " (" + checker.getAttemptCount("admin") + " attempts)");
    }
}

