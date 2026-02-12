package WEEK1and2;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DNSCache {
    private final ConcurrentHashMap<String, DNSEntry> cache;
    private final int maxCacheSize;
    private final LinkedHashMap<String, Long> accessOrder; // For LRU
    private long cacheHits;
    private long cacheMisses;

    static class DNSEntry {
        String domain;
        String ipAddress;
        long timestamp;
        long expiryTime;

        public DNSEntry(String domain, String ipAddress, int ttlSeconds) {
            this.domain = domain;
            this.ipAddress = ipAddress;
            this.timestamp = System.currentTimeMillis();
            this.expiryTime = this.timestamp + (ttlSeconds * 1000L);
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    public DNSCache(int maxCacheSize) {
        this.cache = new ConcurrentHashMap<>();
        this.maxCacheSize = maxCacheSize;
        this.accessOrder = new LinkedHashMap<>(16, 0.75f, true);
        this.cacheHits = 0;
        this.cacheMisses = 0;

        // Start background cleanup thread
        startCleanupThread();
    }

    // Resolve domain name
    public String resolve(String domain) {
        DNSEntry entry = cache.get(domain);

        // Check if cached and not expired
        if (entry != null && !entry.isExpired()) {
            cacheHits++;
            updateAccessOrder(domain);
            System.out.println("Cache HIT → " + entry.ipAddress);
            return entry.ipAddress;
        }

        // Cache miss or expired
        cacheMisses++;
        if (entry != null && entry.isExpired()) {
            System.out.println("Cache EXPIRED → Querying upstream");
            cache.remove(domain);
        } else {
            System.out.println("Cache MISS → Querying upstream");
        }

        // Query upstream DNS (simulated)
        String ipAddress = queryUpstreamDNS(domain);

        // Add to cache with TTL
        addToCache(domain, ipAddress, 300); // 300s TTL
        return ipAddress;
    }

    // Simulate upstream DNS query
    private String queryUpstreamDNS(String domain) {
        // Simulate network delay
        try {
            Thread.sleep(100); // 100ms delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Generate mock IP
        Random rand = new Random();
        return String.format("172.%d.%d.%d",
                rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    // Add entry to cache with LRU eviction
    private void addToCache(String domain, String ipAddress, int ttlSeconds) {
        // Evict LRU if cache is full
        if (cache.size() >= maxCacheSize) {
            evictLRU();
        }

        DNSEntry entry = new DNSEntry(domain, ipAddress, ttlSeconds);
        cache.put(domain, entry);
        updateAccessOrder(domain);
    }

    // Update access order for LRU
    private synchronized void updateAccessOrder(String domain) {
        accessOrder.put(domain, System.currentTimeMillis());
    }

    // Evict least recently used entry
    private synchronized void evictLRU() {
        if (accessOrder.isEmpty()) return;

        String lruDomain = accessOrder.keySet().iterator().next();
        cache.remove(lruDomain);
        accessOrder.remove(lruDomain);
        System.out.println("Evicted LRU entry: " + lruDomain);
    }

    // Get cache statistics
    public String getCacheStats() {
        long total = cacheHits + cacheMisses;
        double hitRate = total > 0 ? (cacheHits * 100.0 / total) : 0;
        return String.format("Hit Rate: %.1f%%, Total Lookups: %d", hitRate, total);
    }

    // Background thread to clean expired entries
    private void startCleanupThread() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000); // Clean every 30 seconds
                    cleanExpiredEntries();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    // Remove expired entries
    private void cleanExpiredEntries() {
        int removed = 0;
        for (Map.Entry<String, DNSEntry> entry : cache.entrySet()) {
            if (entry.getValue().isExpired()) {
                cache.remove(entry.getKey());
                synchronized (this) {
                    accessOrder.remove(entry.getKey());
                }
                removed++;
            }
        }
        if (removed > 0) {
            System.out.println("Cleaned " + removed + " expired entries");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DNSCache dnsCache = new DNSCache(1000);

        // First lookup - cache miss
        String ip1 = dnsCache.resolve("google.com");
        System.out.println("Resolved: " + ip1);

        // Second lookup - cache hit
        String ip2 = dnsCache.resolve("google.com");
        System.out.println("Resolved: " + ip2);

        // Multiple lookups
        dnsCache.resolve("facebook.com");
        dnsCache.resolve("google.com");
        dnsCache.resolve("amazon.com");

        System.out.println(dnsCache.getCacheStats());
    }
}
