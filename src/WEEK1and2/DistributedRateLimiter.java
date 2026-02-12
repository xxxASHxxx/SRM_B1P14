package WEEK1and2;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DistributedRateLimiter {
    private final ConcurrentHashMap<String, TokenBucket> clientBuckets;
    private final int maxRequestsPerHour;
    private final long windowSizeMillis;

    static class TokenBucket {
        private int tokens;
        private long lastRefillTime;
        private final int maxTokens;
        private final long refillIntervalMillis;

        public TokenBucket(int maxTokens, long refillIntervalMillis) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.refillIntervalMillis = refillIntervalMillis;
            this.lastRefillTime = System.currentTimeMillis();
        }

        public synchronized RateLimitResult tryConsume() {
            refillTokens();

            if (tokens > 0) {
                tokens--;
                return new RateLimitResult(true, tokens, 0);
            } else {
                long resetTime = lastRefillTime + refillIntervalMillis;
                long retryAfterSeconds = (resetTime - System.currentTimeMillis()) / 1000;
                return new RateLimitResult(false, 0, retryAfterSeconds);
            }
        }

        private void refillTokens() {
            long now = System.currentTimeMillis();
            long timeSinceRefill = now - lastRefillTime;

            // Refill if window has passed
            if (timeSinceRefill >= refillIntervalMillis) {
                tokens = maxTokens;
                lastRefillTime = now;
            }
        }

        public synchronized RateLimitStatus getStatus() {
            refillTokens();
            int used = maxTokens - tokens;
            long resetTime = lastRefillTime + refillIntervalMillis;
            return new RateLimitStatus(used, maxTokens, resetTime / 1000);
        }
    }

    static class RateLimitResult {
        boolean allowed;
        int remaining;
        long retryAfterSeconds;

        public RateLimitResult(boolean allowed, int remaining, long retryAfterSeconds) {
            this.allowed = allowed;
            this.remaining = remaining;
            this.retryAfterSeconds = retryAfterSeconds;
        }

        @Override
        public String toString() {
            if (allowed) {
                return String.format("Allowed (%d requests remaining)", remaining);
            } else {
                return String.format("Denied (0 requests remaining, retry after %ds)",
                        retryAfterSeconds);
            }
        }
    }

    static class RateLimitStatus {
        int used;
        int limit;
        long resetTimestamp;

        public RateLimitStatus(int used, int limit, long resetTimestamp) {
            this.used = used;
            this.limit = limit;
            this.resetTimestamp = resetTimestamp;
        }

        @Override
        public String toString() {
            return String.format("{used: %d, limit: %d, reset: %d}",
                    used, limit, resetTimestamp);
        }
    }

    public DistributedRateLimiter(int maxRequestsPerHour) {
        this.clientBuckets = new ConcurrentHashMap<>();
        this.maxRequestsPerHour = maxRequestsPerHour;
        this.windowSizeMillis = 60 * 60 * 1000; // 1 hour in milliseconds
    }

    // Check rate limit for client (O(1) operation)
    public RateLimitResult checkRateLimit(String clientId) {
        TokenBucket bucket = clientBuckets.computeIfAbsent(clientId,
                k -> new TokenBucket(maxRequestsPerHour, windowSizeMillis));

        long startTime = System.nanoTime();
        RateLimitResult result = bucket.tryConsume();
        long endTime = System.nanoTime();

        long latencyMicros = (endTime - startTime) / 1000;
        if (latencyMicros > 1000) { // More than 1ms
            System.out.println("Warning: Rate limit check took " + latencyMicros + "μs");
        }

        return result;
    }

    // Get rate limit status for client
    public RateLimitStatus getRateLimitStatus(String clientId) {
        TokenBucket bucket = clientBuckets.get(clientId);
        if (bucket == null) {
            return new RateLimitStatus(0, maxRequestsPerHour,
                    (System.currentTimeMillis() + windowSizeMillis) / 1000);
        }
        return bucket.getStatus();
    }

    // Clear all rate limits (for testing)
    public void clearAll() {
        clientBuckets.clear();
    }

    // Get total number of tracked clients
    public int getTrackedClientsCount() {
        return clientBuckets.size();
    }

    public static void main(String[] args) throws InterruptedException {
        DistributedRateLimiter limiter = new DistributedRateLimiter(1000);

        String clientId = "abc123";

        // Test normal requests
        System.out.println("Testing rate limiter:\n");
        System.out.println("Request 1: " + limiter.checkRateLimit(clientId));
        System.out.println("Request 2: " + limiter.checkRateLimit(clientId));

        // Simulate burst traffic
        System.out.println("\nSimulating 998 more requests...");
        for (int i = 0; i < 998; i++) {
            limiter.checkRateLimit(clientId);
        }

        // This should be denied
        System.out.println("\nRequest 1001: " + limiter.checkRateLimit(clientId));

        // Check status
        System.out.println("\nRate limit status: " + limiter.getRateLimitStatus(clientId));

        // Test multiple clients
        System.out.println("\nTesting multiple clients:");
        System.out.println("Client xyz789: " + limiter.checkRateLimit("xyz789"));
        System.out.println("Client def456: " + limiter.checkRateLimit("def456"));

        System.out.println("\nTotal tracked clients: " + limiter.getTrackedClientsCount());
    }
}
