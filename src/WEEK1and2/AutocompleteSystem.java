package WEEK1and2;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AutocompleteSystem {
    // Global query frequency storage
    private final ConcurrentHashMap<String, Integer> queryFrequency;
    // Trie for prefix-based search
    private final TrieNode root;
    // Cache for popular prefix results
    private final ConcurrentHashMap<String, List<SearchSuggestion>> prefixCache;
    private final int maxSuggestions;
    private final int cacheThreshold;

    static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        String query;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    static class SearchSuggestion implements Comparable<SearchSuggestion> {
        String query;
        int frequency;

        public SearchSuggestion(String query, int frequency) {
            this.query = query;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(SearchSuggestion other) {
            // Sort by frequency descending, then alphabetically
            if (this.frequency != other.frequency) {
                return Integer.compare(other.frequency, this.frequency);
            }
            return this.query.compareTo(other.query);
        }

        @Override
        public String toString() {
            return String.format("\"%s\" (%,d searches)", query, frequency);
        }
    }

    public AutocompleteSystem() {
        this.queryFrequency = new ConcurrentHashMap<>();
        this.root = new TrieNode();
        this.prefixCache = new ConcurrentHashMap<>();
        this.maxSuggestions = 10;
        this.cacheThreshold = 100; // Cache results for prefixes searched 100+ times
    }

    // Insert query into trie
    private void insertQuery(String query) {
        TrieNode current = root;

        for (char ch : query.toLowerCase().toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }

        current.isEndOfWord = true;
        current.query = query;
    }

    // Update frequency and insert into trie
    public void updateFrequency(String query) {
        query = query.toLowerCase().trim();

        int newFrequency = queryFrequency.merge(query, 1, Integer::sum);

        // Insert into trie if new query
        if (newFrequency == 1) {
            insertQuery(query);
        }

        // Invalidate cache for all prefixes of this query
        for (int i = 1; i <= query.length(); i++) {
            String prefix = query.substring(0, i);
            prefixCache.remove(prefix);
        }
    }

    // Search for suggestions with prefix
    public List<SearchSuggestion> search(String prefix) {
        long startTime = System.nanoTime();

        prefix = prefix.toLowerCase().trim();

        // Check cache first
        if (prefixCache.containsKey(prefix)) {
            long elapsed = (System.nanoTime() - startTime) / 1_000_000;
            System.out.println("Cache hit! Response time: " + elapsed + "ms");
            return prefixCache.get(prefix);
        }

        // Find all queries with this prefix
        List<SearchSuggestion> suggestions = new ArrayList<>();
        TrieNode current = root;

        // Navigate to prefix node
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return suggestions; // No matches
            }
        }

        // Collect all queries with this prefix
        collectQueries(current, suggestions);

        // Sort by frequency and get top N using min-heap
        PriorityQueue<SearchSuggestion> topK = new PriorityQueue<>(
                Comparator.comparingInt((SearchSuggestion s) -> s.frequency)
                        .thenComparing(s -> s.query, Comparator.reverseOrder())
        );

        for (SearchSuggestion suggestion : suggestions) {
            topK.offer(suggestion);
            if (topK.size() > maxSuggestions) {
                topK.poll();
            }
        }

        // Build result in descending order
        List<SearchSuggestion> result = new ArrayList<>(topK);
        result.sort(Collections.reverseOrder());

        // Cache if this prefix is searched frequently
        prefixCache.put(prefix, result);

        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Response time: " + elapsed + "ms");

        return result;
    }

    // Collect all queries from trie node
    private void collectQueries(TrieNode node, List<SearchSuggestion> results) {
        if (node.isEndOfWord) {
            int frequency = queryFrequency.getOrDefault(node.query, 0);
            results.add(new SearchSuggestion(node.query, frequency));
        }

        for (TrieNode child : node.children.values()) {
            collectQueries(child, results);
        }
    }

    // Simple typo correction using edit distance
    public List<String> suggestCorrections(String query) {
        List<String> corrections = new ArrayList<>();

        for (String knownQuery : queryFrequency.keySet()) {
            if (editDistance(query.toLowerCase(), knownQuery.toLowerCase()) <= 2) {
                corrections.add(knownQuery);
            }
        }

        corrections.sort((a, b) -> Integer.compare(
                queryFrequency.get(b), queryFrequency.get(a)
        ));

        return corrections.subList(0, Math.min(5, corrections.size()));
    }

    // Calculate Levenshtein distance
    private int editDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    // Get memory usage estimate
    public long estimateMemoryUsage() {
        long queryMemory = queryFrequency.size() * 50; // Avg 30 chars + overhead
        long cacheMemory = prefixCache.size() * 200; // Cached results
        return queryMemory + cacheMemory;
    }

    public static void main(String[] args) {
        AutocompleteSystem autocomplete = new AutocompleteSystem();

        // Populate with sample data
        autocomplete.updateFrequency("java tutorial");
        autocomplete.updateFrequency("javascript");
        autocomplete.updateFrequency("java download");
        autocomplete.updateFrequency("java 21 features");
        autocomplete.updateFrequency("java spring boot");
        autocomplete.updateFrequency("javascript react");
        autocomplete.updateFrequency("java interview questions");

        // Simulate popularity
        for (int i = 0; i < 1234567; i++) {
            if (i % 1000000 == 0) autocomplete.updateFrequency("java tutorial");
        }
        for (int i = 0; i < 987654; i++) {
            if (i % 1000000 == 0) autocomplete.updateFrequency("javascript");
        }
        for (int i = 0; i < 456789; i++) {
            if (i % 1000000 == 0) autocomplete.updateFrequency("java download");
        }

        // Set frequencies manually for demo
        autocomplete.queryFrequency.put("java tutorial", 1234567);
        autocomplete.queryFrequency.put("javascript", 987654);
        autocomplete.queryFrequency.put("java download", 456789);
        autocomplete.queryFrequency.put("java 21 features", 123456);
        autocomplete.queryFrequency.put("java spring boot", 98765);

        // Search
        System.out.println("Search results for 'jav':");
        List<SearchSuggestion> results = autocomplete.search("jav");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        // Test trending
        System.out.println("\n\nUpdating 'java 21 features' frequency:");
        for (int i = 1; i <= 3; i++) {
            autocomplete.updateFrequency("java 21 features");
            System.out.println("Frequency: " + autocomplete.queryFrequency.get("java 21 features"));
        }

        // Typo correction
        System.out.println("\nTypo corrections for 'jaav':");
        List<String> corrections = autocomplete.suggestCorrections("jaav");
        corrections.forEach(System.out::println);
    }
}

