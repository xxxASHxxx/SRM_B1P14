package WEEK1and2;
import java.util.*;

public class PlagiarismDetector {
    // N-gram to document IDs mapping
    private final HashMap<String, Set<String>> ngramIndex;
    private final HashMap<String, List<String>> documentNgrams;
    private final int ngramSize;

    public PlagiarismDetector(int ngramSize) {
        this.ngramIndex = new HashMap<>();
        this.documentNgrams = new HashMap<>();
        this.ngramSize = ngramSize;
    }

    // Extract n-grams from text
    private List<String> extractNgrams(String text) {
        String[] words = text.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .split("\\s+");

        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - ngramSize; i++) {
            StringBuilder ngram = new StringBuilder();
            for (int j = 0; j < ngramSize; j++) {
                if (j > 0) ngram.append(" ");
                ngram.append(words[i + j]);
            }
            ngrams.add(ngram.toString());
        }

        return ngrams;
    }

    // Analyze and index a document
    public AnalysisResult analyzeDocument(String docId, String content) {
        List<String> ngrams = extractNgrams(content);
        documentNgrams.put(docId, ngrams);

        // Add n-grams to index
        for (String ngram : ngrams) {
            ngramIndex.computeIfAbsent(ngram, k -> new HashSet<>()).add(docId);
        }

        // Find similarities with existing documents
        Map<String, Integer> matchCounts = new HashMap<>();

        for (String ngram : ngrams) {
            Set<String> matchingDocs = ngramIndex.get(ngram);
            if (matchingDocs != null) {
                for (String matchDoc : matchingDocs) {
                    if (!matchDoc.equals(docId)) {
                        matchCounts.put(matchDoc, matchCounts.getOrDefault(matchDoc, 0) + 1);
                    }
                }
            }
        }

        // Calculate similarities
        List<SimilarityMatch> matches = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
            String otherDoc = entry.getKey();
            int matchingNgrams = entry.getValue();
            double similarity = (matchingNgrams * 100.0) / ngrams.size();

            if (similarity > 5.0) { // Only report if > 5% similarity
                matches.add(new SimilarityMatch(otherDoc, matchingNgrams, similarity));
            }
        }

        // Sort by similarity descending
        matches.sort((a, b) -> Double.compare(b.similarity, a.similarity));

        return new AnalysisResult(docId, ngrams.size(), matches);
    }

    // Compare two specific documents
    public double compareDocuments(String docId1, String docId2) {
        List<String> ngrams1 = documentNgrams.get(docId1);
        List<String> ngrams2 = documentNgrams.get(docId2);

        if (ngrams1 == null || ngrams2 == null) {
            return 0.0;
        }

        Set<String> set1 = new HashSet<>(ngrams1);
        Set<String> set2 = new HashSet<>(ngrams2);

        // Count intersection
        int matches = 0;
        for (String ngram : set1) {
            if (set2.contains(ngram)) {
                matches++;
            }
        }

        return (matches * 100.0) / Math.min(ngrams1.size(), ngrams2.size());
    }

    static class SimilarityMatch {
        String documentId;
        int matchingNgrams;
        double similarity;

        public SimilarityMatch(String documentId, int matchingNgrams, double similarity) {
            this.documentId = documentId;
            this.matchingNgrams = matchingNgrams;
            this.similarity = similarity;
        }

        public String getStatus() {
            if (similarity > 50) return "PLAGIARISM DETECTED";
            if (similarity > 20) return "SUSPICIOUS";
            return "Low similarity";
        }

        @Override
        public String toString() {
            return String.format("  %s: %d matching n-grams, %.1f%% similarity (%s)",
                    documentId, matchingNgrams, similarity, getStatus());
        }
    }

    static class AnalysisResult {
        String documentId;
        int totalNgrams;
        List<SimilarityMatch> matches;

        public AnalysisResult(String documentId, int totalNgrams, List<SimilarityMatch> matches) {
            this.documentId = documentId;
            this.totalNgrams = totalNgrams;
            this.matches = matches;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Document: ").append(documentId).append("\n");
            sb.append("Extracted ").append(totalNgrams).append(" n-grams\n");

            if (matches.isEmpty()) {
                sb.append("No significant matches found\n");
            } else {
                sb.append("Similarity matches:\n");
                for (SimilarityMatch match : matches) {
                    sb.append(match).append("\n");
                }
            }

            return sb.toString();
        }
    }

    public static void main(String[] args) {
        PlagiarismDetector detector = new PlagiarismDetector(5); // 5-grams

        // Sample documents
        String essay089 = "The quick brown fox jumps over the lazy dog. " +
                "This is a sample essay about animals and nature. " +
                "The fox is known for its cunning and agility.";

        String essay092 = "The quick brown fox jumps over the lazy dog. " +
                "The fox is known for its cunning and agility. " +
                "This demonstrates clear plagiarism from another source.";

        String essay123 = "The quick brown fox jumps over the lazy dog. " +
                "However, this essay takes a different approach. " +
                "We will explore unique perspectives on animal behavior.";

        // Analyze documents
        detector.analyzeDocument("essay_089.txt", essay089);
        detector.analyzeDocument("essay_092.txt", essay092);

        System.out.println("Analyzing new submission:");
        AnalysisResult result = detector.analyzeDocument("essay_123.txt", essay123);
        System.out.println(result);
    }
}
