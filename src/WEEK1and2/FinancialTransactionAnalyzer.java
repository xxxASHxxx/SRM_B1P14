package WEEK1and2;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class FinancialTransactionAnalyzer {
    private final List<Transaction> transactions;
    private final Map<Double, List<Transaction>> amountIndex;

    static class Transaction {
        int id;
        double amount;
        String merchant;
        LocalDateTime timestamp;
        String accountId;

        public Transaction(int id, double amount, String merchant,
                           LocalDateTime timestamp, String accountId) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.timestamp = timestamp;
            this.accountId = accountId;
        }

        @Override
        public String toString() {
            return String.format("Transaction{id=%d, amount=%.2f, merchant='%s', time=%s}",
                    id, amount, merchant, timestamp.toString());
        }
    }

    static class TransactionPair {
        Transaction t1;
        Transaction t2;

        public TransactionPair(Transaction t1, Transaction t2) {
            this.t1 = t1;
            this.t2 = t2;
        }

        @Override
        public String toString() {
            return String.format("(id:%d, id:%d)", t1.id, t2.id);
        }
    }

    static class DuplicateGroup {
        double amount;
        String merchant;
        List<String> accounts;

        public DuplicateGroup(double amount, String merchant, List<String> accounts) {
            this.amount = amount;
            this.merchant = merchant;
            this.accounts = accounts;
        }

        @Override
        public String toString() {
            return String.format("{amount:%.2f, merchant:\"%s\", accounts:%s}",
                    amount, merchant, accounts);
        }
    }

    public FinancialTransactionAnalyzer() {
        this.transactions = new ArrayList<>();
        this.amountIndex = new HashMap<>();
    }

    // Add transaction
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        amountIndex.computeIfAbsent(transaction.amount, k -> new ArrayList<>())
                .add(transaction);
    }

    // Classic Two-Sum: O(n) with hash table
    public List<TransactionPair> findTwoSum(double target) {
        long startTime = System.nanoTime();

        List<TransactionPair> pairs = new ArrayList<>();
        Map<Double, Transaction> complementMap = new HashMap<>();

        for (Transaction t : transactions) {
            double complement = target - t.amount;

            if (complementMap.containsKey(t.amount)) {
                pairs.add(new TransactionPair(complementMap.get(t.amount), t));
            }

            complementMap.put(complement, t);
        }

        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Two-Sum completed in " + elapsed + "ms");

        return pairs;
    }

    // Two-Sum with time window (within 1 hour)
    public List<TransactionPair> findTwoSumWithTimeWindow(double target, int windowMinutes) {
        long startTime = System.nanoTime();

        List<TransactionPair> pairs = new ArrayList<>();

        // Sort by timestamp for efficient window processing
        List<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort(Comparator.comparing(t -> t.timestamp));

        for (int i = 0; i < sorted.size(); i++) {
            Transaction t1 = sorted.get(i);
            double complement = target - t1.amount;

            // Check transactions within time window
            for (int j = i + 1; j < sorted.size(); j++) {
                Transaction t2 = sorted.get(j);

                long minutesDiff = ChronoUnit.MINUTES.between(t1.timestamp, t2.timestamp);
                if (minutesDiff > windowMinutes) {
                    break; // Outside window
                }

                if (Math.abs(t2.amount - complement) < 0.01) { // Float comparison
                    pairs.add(new TransactionPair(t1, t2));
                }
            }
        }

        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Two-Sum with time window completed in " + elapsed + "ms");

        return pairs;
    }

    // K-Sum: Find K transactions that sum to target
    public List<List<Transaction>> findKSum(int k, double target) {
        long startTime = System.nanoTime();

        List<List<Transaction>> results = new ArrayList<>();
        List<Transaction> current = new ArrayList<>();

        findKSumRecursive(0, k, target, current, results);

        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("K-Sum completed in " + elapsed + "ms");

        return results;
    }

    private void findKSumRecursive(int start, int k, double target,
                                   List<Transaction> current,
                                   List<List<Transaction>> results) {
        if (k == 0) {
            if (Math.abs(target) < 0.01) { // Found valid combination
                results.add(new ArrayList<>(current));
            }
            return;
        }

        if (start >= transactions.size()) {
            return;
        }

        // Include current transaction
        Transaction t = transactions.get(start);
        current.add(t);
        findKSumRecursive(start + 1, k - 1, target - t.amount, current, results);
        current.remove(current.size() - 1);

        // Exclude current transaction
        findKSumRecursive(start + 1, k, target, current, results);
    }

    // Detect duplicate transactions (same amount, merchant, different accounts)
    public List<DuplicateGroup> detectDuplicates() {
        long startTime = System.nanoTime();

        // Group by amount and merchant
        Map<String, List<Transaction>> groups = new HashMap<>();

        for (Transaction t : transactions) {
            String key = t.amount + "|" + t.merchant;
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(t);
        }

        List<DuplicateGroup> duplicates = new ArrayList<>();

        for (Map.Entry<String, List<Transaction>> entry : groups.entrySet()) {
            List<Transaction> group = entry.getValue();

            if (group.size() > 1) {
                // Check for different accounts
                Set<String> accounts = new HashSet<>();
                for (Transaction t : group) {
                    accounts.add(t.accountId);
                }

                if (accounts.size() > 1) {
                    duplicates.add(new DuplicateGroup(
                            group.get(0).amount,
                            group.get(0).merchant,
                            new ArrayList<>(accounts)
                    ));
                }
            }
        }

        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Duplicate detection completed in " + elapsed + "ms");

        return duplicates;
    }

    // Detect potential fraud patterns
    public List<String> detectFraudPatterns() {
        List<String> alerts = new ArrayList<>();

        // Pattern 1: Round-trip transactions (A→B and B→A with same amount)
        Map<String, List<Transaction>> merchantPairs = new HashMap<>();

        for (Transaction t : transactions) {
            merchantPairs.computeIfAbsent(t.merchant, k -> new ArrayList<>()).add(t);
        }

        // Pattern 2: Frequent small transactions just below reporting threshold
        Map<String, Integer> smallTransactionCount = new HashMap<>();
        double threshold = 10000.0;

        for (Transaction t : transactions) {
            if (t.amount > threshold * 0.9 && t.amount < threshold) {
                smallTransactionCount.merge(t.accountId, 1, Integer::sum);
            }
        }

        for (Map.Entry<String, Integer> entry : smallTransactionCount.entrySet()) {
            if (entry.getValue() > 5) {
                alerts.add("Suspicious pattern: Account " + entry.getKey() +
                        " has " + entry.getValue() +
                        " transactions just below reporting threshold");
            }
        }

        return alerts;
    }

    public static void main(String[] args) {
        FinancialTransactionAnalyzer analyzer = new FinancialTransactionAnalyzer();

        LocalDateTime baseTime = LocalDateTime.now().minusHours(2);

        // Add sample transactions
        analyzer.addTransaction(new Transaction(1, 500, "Store A", baseTime, "acc1"));
        analyzer.addTransaction(new Transaction(2, 300, "Store B", baseTime.plusMinutes(15), "acc2"));
        analyzer.addTransaction(new Transaction(3, 200, "Store C", baseTime.plusMinutes(30), "acc3"));
        analyzer.addTransaction(new Transaction(4, 500, "Store A", baseTime.plusMinutes(45), "acc2"));
        analyzer.addTransaction(new Transaction(5, 150, "Store D", baseTime.plusMinutes(50), "acc1"));

        // Test Two-Sum
        System.out.println("Finding pairs that sum to 500:");
        List<TransactionPair> pairs = analyzer.findTwoSum(500);
        pairs.forEach(System.out::println);

        // Test Two-Sum with time window
        System.out.println("\nFinding pairs within 60-minute window that sum to 500:");
        List<TransactionPair> windowPairs = analyzer.findTwoSumWithTimeWindow(500, 60);
        windowPairs.forEach(System.out::println);

        // Test K-Sum
        System.out.println("\nFinding 3 transactions that sum to 1000:");
        List<List<Transaction>> kSumResults = analyzer.findKSum(3, 1000);
        for (List<Transaction> combo : kSumResults) {
            System.out.println(combo.stream().map(t -> "id:" + t.id).toList());
        }

        // Test duplicate detection
        System.out.println("\nDetecting duplicate transactions:");
        List<DuplicateGroup> duplicates = analyzer.detectDuplicates();
        duplicates.forEach(System.out::println);

        // Fraud detection
        System.out.println("\nFraud pattern detection:");
        List<String> alerts = analyzer.detectFraudPatterns();
        alerts.forEach(System.out::println);
    }
}

