package WEEK1and2;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MultiLevelCacheSystem {
    // L1: In-memory cache (fastest)
    private final LinkedHashMap<String, VideoData> l1Cache;
    // L2: SSD cache (medium speed)
    private final LinkedHashMap<String, String> l2Cache; // videoId -> filePath
    // L3: Database (slowest) - simulated with HashMap
    private final Map<String, VideoData> database;

    // Access counters for promotion logic
    private final ConcurrentHashMap<String, Integer> accessCounts;

    // Cache configuration
    private final int L1_CAPACITY = 10_000;
    private final int L2_CAPACITY = 100_000;
    private final int PROMOTION_THRESHOLD = 5;

    // Statistics
    private long l1Hits, l1Misses;
    private long l2Hits, l2Misses;
    private long l3Hits;
    private long l1TotalTime, l2TotalTime, l3TotalTime;

    static class VideoData {
        String videoId;
        String title;
        String url;
        long sizeBytes;
        byte[] content; // Simulated video data

        public VideoData(String videoId, String title, String url, long sizeBytes) {
            this.videoId = videoId;
            this.title = title;
            this.url = url;
            this.sizeBytes = sizeBytes;
            this.content = new byte[0]; // Placeholder
        }

        @Override
        public String toString() {
            return String.format("Video{id='%s', title='%s', size=%dMB}",
                    videoId, title, sizeBytes / (1024 * 1024));
        }
    }

    static class CacheResult {
        VideoData data;
        String hitLevel; // "L1", "L2", "L3"
        long responseTimeMs;
        boolean promoted;

        public CacheResult(VideoData data, String hitLevel, long responseTimeMs, boolean promoted) {
            this.data = data;
            this.hitLevel = hitLevel;
            this.responseTimeMs = responseTimeMs;
            this.promoted = promoted;
        }

        @Override
        public String toString() {
            String promotion = promoted ? " → Promoted to L1" : "";
            return String.format("%s Cache %s (%.1fms)%s",
                    hitLevel, hitLevel.equals("L3") ? "HIT" : "HIT",
                    responseTimeMs / 1000000.0, promotion);
        }
    }

    public MultiLevelCacheSystem() {
        // L1: LRU cache with access-order
        this.l1Cache = new LinkedHashMap<>(L1_CAPACITY, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                if (size() > L1_CAPACITY) {
                    // Demote to L2
                    demoteToL2(eldest.getKey(), eldest.getValue());
                    return true;
                }
                return false;
            }
        };

        // L2: LRU cache with access-order
        this.l2Cache = new LinkedHashMap<>(L2_CAPACITY, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > L2_CAPACITY;
            }
        };

        this.database = new ConcurrentHashMap<>();
        this.accessCounts = new ConcurrentHashMap<>();
    }

    // Get video from cache hierarchy
    public CacheResult getVideo(String videoId) {
        long startTime = System.nanoTime();

        // Check L1 Cache
        synchronized (l1Cache) {
            if (l1Cache.containsKey(videoId)) {
                l1Hits++;
                long elapsed = System.nanoTime() - startTime;
                l1TotalTime += elapsed;

                VideoData data = l1Cache.get(videoId);
                accessCounts.merge(videoId, 1, Integer::sum);

                return new CacheResult(data, "L1", elapsed, false);
            }
        }
        l1Misses++;

        // Check L2 Cache
        synchronized (l2Cache) {
            if (l2Cache.containsKey(videoId)) {
                l2Hits++;
                long elapsed = System.nanoTime() - startTime;
                l2TotalTime += elapsed;

                // Simulate SSD read delay
                simulateDelay(5);

                // Load from L2
                VideoData data = loadFromL2(videoId);

                // Increment access count
                int count = accessCounts.merge(videoId, 1, Integer::sum);

                // Promote to L1 if accessed frequently
                boolean promoted = false;
                if (count >= PROMOTION_THRESHOLD) {
                    promoteToL1(videoId, data);
                    promoted = true;
                }

                return new CacheResult(data, "L2", System.nanoTime() - startTime, promoted);
            }
        }
        l2Misses++;

        // Check L3 (Database)
        VideoData data = database.get(videoId);
        if (data != null) {
            l3Hits++;

            // Simulate database query delay
            simulateDelay(150);

            long elapsed = System.nanoTime() - startTime;
            l3TotalTime += elapsed;

            // Add to L2
            addToL2(videoId, data);
            accessCounts.put(videoId, 1);

            return new CacheResult(data, "L3", elapsed, false);
        }

        return null; // Video not found
    }

    // Add video to database (simulates initial upload)
    public void addVideo(VideoData video) {
        database.put(video.videoId, video);
    }

    // Promote from L2 to L1
    private void promoteToL1(String videoId, VideoData data) {
        synchronized (l1Cache) {
            l1Cache.put(videoId, data);
        }
        System.out.println("→ Promoted " + videoId + " to L1");
    }

    // Demote from L1 to L2
    private void demoteToL2(String videoId, VideoData data) {
        synchronized (l2Cache) {
            l2Cache.put(videoId, "ssd://path/to/" + videoId);
        }
    }

    // Add to L2 cache
    private void addToL2(String videoId, VideoData data) {
        synchronized (l2Cache) {
            l2Cache.put(videoId, "ssd://path/to/" + videoId);
        }
    }

    // Simulate loading from L2 (SSD)
    private VideoData loadFromL2(String videoId) {
        // In real implementation, read from SSD
        return database.get(videoId);
    }

    // Invalidate cache entry (when content updates)
    public void invalidateCache(String videoId) {
        synchronized (l1Cache) {
            l1Cache.remove(videoId);
        }
        synchronized (l2Cache) {
            l2Cache.remove(videoId);
        }
        accessCounts.remove(videoId);
        System.out.println("Cache invalidated for " + videoId);
    }

    // Simulate I/O delay
    private void simulateDelay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Get comprehensive statistics
    public String getStatistics() {
        long totalL1 = l1Hits + l1Misses;
        long totalL2 = l2Hits + l2Misses;
        long totalL3 = l3Hits;
        long totalRequests = totalL1;

        double l1HitRate = totalL1 > 0 ? (l1Hits * 100.0 / totalL1) : 0;
        double l2HitRate = totalL2 > 0 ? (l2Hits * 100.0 / totalL2) : 0;
        double overallHitRate = totalRequests > 0 ?
                ((l1Hits + l2Hits + l3Hits) * 100.0 / totalRequests) : 0;

        double avgL1Time = l1Hits > 0 ? (l1TotalTime / 1_000_000.0 / l1Hits) : 0;
        double avgL2Time = l2Hits > 0 ? (l2TotalTime / 1_000_000.0 / l2Hits) : 0;
        double avgL3Time = l3Hits > 0 ? (l3TotalTime / 1_000_000.0 / l3Hits) : 0;

        double avgOverallTime = totalRequests > 0 ?
                ((l1TotalTime + l2TotalTime + l3TotalTime) / 1_000_000.0 / totalRequests) : 0;

        StringBuilder sb = new StringBuilder();
        sb.append("=== Multi-Level Cache Statistics ===\n\n");
        sb.append(String.format("L1: Hit Rate %.1f%%, Avg Time: %.1fms\n", l1HitRate, avgL1Time));
        sb.append(String.format("L2: Hit Rate %.1f%%, Avg Time: %.1fms\n", l2HitRate, avgL2Time));
        sb.append(String.format("L3: Hit Rate N/A, Avg Time: %.1fms\n", avgL3Time));
        sb.append(String.format("\nOverall: Hit Rate %.1f%%, Avg Time: %.1fms\n",
                overallHitRate, avgOverallTime));
        sb.append(String.format("\nCache Sizes: L1=%d, L2=%d, DB=%d\n",
                l1Cache.size(), l2Cache.size(), database.size()));

        return sb.toString();
    }

    public static void main(String[] args) {
        MultiLevelCacheSystem cache = new MultiLevelCacheSystem();

        // Populate database with videos
        for (int i = 1; i <= 1000; i++) {
            cache.addVideo(new VideoData(
                    "video_" + i,
                    "Video Title " + i,
                    "https://cdn.example.com/video_" + i,
                    100 * 1024 * 1024L // 100 MB
            ));
        }

        // Test cache hierarchy
        System.out.println("=== Testing Multi-Level Cache ===\n");

        // First request - L3 miss
        System.out.println("getVideo(\"video_123\")");
        CacheResult result1 = cache.getVideo("video_123");
        System.out.println(result1);
        System.out.println("→ Total: " + (result1.responseTimeMs / 1_000_000.0) + "ms\n");

        // Second request - should be in L2
        System.out.println("getVideo(\"video_123\") [second request]");
        CacheResult result2 = cache.getVideo("video_123");
        System.out.println(result2);
        System.out.println("→ Total: " + (result2.responseTimeMs / 1_000_000.0) + "ms\n");

        // Access multiple times to trigger promotion
        for (int i = 0; i < 5; i++) {
            cache.getVideo("video_123");
        }

        // Should now be in L1
        System.out.println("getVideo(\"video_123\") [after multiple accesses]");
        CacheResult result3 = cache.getVideo("video_123");
        System.out.println(result3);

        // Test another video
        System.out.println("\ngetVideo(\"video_999\")");
        CacheResult result4 = cache.getVideo("video_999");
        System.out.println(result4);

        // Print statistics
        System.out.println("\n" + cache.getStatistics());

        // Test cache invalidation
        System.out.println("\nTesting cache invalidation:");
        cache.invalidateCache("video_123");
    }
}

