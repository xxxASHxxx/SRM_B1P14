package WEEK1and2;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RealTimeAnalyticsDashboard {
    // Page view counts
    private final ConcurrentHashMap<String, AtomicLong> pageViews;
    // Unique visitors per page
    private final ConcurrentHashMap<String, Set<String>> uniqueVisitors;
    // Traffic source counts
    private final ConcurrentHashMap<String, AtomicLong> trafficSources;
    // Scheduler for dashboard updates
    private final ScheduledExecutorService scheduler;

    static class PageViewEvent {
        String url;
        String userId;
        String source;
        long timestamp;

        public PageViewEvent(String url, String userId, String source) {
            this.url = url;
            this.userId = userId;
            this.source = source;
            this.timestamp = System.currentTimeMillis();
        }
    }

    public RealTimeAnalyticsDashboard() {
        this.pageViews = new ConcurrentHashMap<>();
        this.uniqueVisitors = new ConcurrentHashMap<>();
        this.trafficSources = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);

        // Schedule dashboard updates every 5 seconds
        startDashboardUpdates();
    }

    // Process incoming page view event
    public void processEvent(PageViewEvent event) {
        // Increment page view count
        pageViews.computeIfAbsent(event.url, k -> new AtomicLong(0))
                .incrementAndGet();

        // Track unique visitor
        uniqueVisitors.computeIfAbsent(event.url, k ->
                        Collections.synchronizedSet(new HashSet<>()))
                .add(event.userId);

        // Increment traffic source count
        trafficSources.computeIfAbsent(event.source, k -> new AtomicLong(0))
                .incrementAndGet();
    }

    // Get top N pages by views
    public List<PageStats> getTopPages(int n) {
        List<PageStats> stats = new ArrayList<>();

        for (Map.Entry<String, AtomicLong> entry : pageViews.entrySet()) {
            String url = entry.getKey();
            long views = entry.getValue().get();
            int unique = uniqueVisitors.getOrDefault(url, Collections.emptySet()).size();
            stats.add(new PageStats(url, views, unique));
        }

        // Sort by views descending
        stats.sort((a, b) -> Long.compare(b.views, a.views));

        return stats.subList(0, Math.min(n, stats.size()));
    }

    // Get traffic source distribution
    public Map<String, Double> getTrafficSourceDistribution() {
        long total = trafficSources.values().stream()
                .mapToLong(AtomicLong::get)
                .sum();

        Map<String, Double> distribution = new HashMap<>();

        for (Map.Entry<String, AtomicLong> entry : trafficSources.entrySet()) {
            double percentage = (entry.getValue().get() * 100.0) / total;
            distribution.put(entry.getKey(), percentage);
        }

        return distribution;
    }

    // Generate dashboard snapshot
    public DashboardSnapshot getDashboard() {
        List<PageStats> topPages = getTopPages(10);
        Map<String, Double> sources = getTrafficSourceDistribution();
        return new DashboardSnapshot(topPages, sources);
    }

    // Start periodic dashboard updates
    private void startDashboardUpdates() {
        scheduler.scheduleAtFixedRate(() -> {
            // Simulate dashboard update
            // In production, this would push to frontend
        }, 5, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    static class PageStats {
        String url;
        long views;
        int uniqueVisitors;

        public PageStats(String url, long views, int uniqueVisitors) {
            this.url = url;
            this.views = views;
            this.uniqueVisitors = uniqueVisitors;
        }

        @Override
        public String toString() {
            return String.format("%s - %,d views (%,d unique)",
                    url, views, uniqueVisitors);
        }
    }

    static class DashboardSnapshot {
        List<PageStats> topPages;
        Map<String, Double> trafficSources;
        long timestamp;

        public DashboardSnapshot(List<PageStats> topPages, Map<String, Double> trafficSources) {
            this.topPages = topPages;
            this.trafficSources = trafficSources;
            this.timestamp = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Real-Time Dashboard ===\n\n");
            sb.append("Top Pages:\n");

            int rank = 1;
            for (PageStats page : topPages) {
                sb.append(String.format("%d. %s\n", rank++, page));
            }

            sb.append("\nTraffic Sources:\n");
            List<Map.Entry<String, Double>> sortedSources = new ArrayList<>(trafficSources.entrySet());
            sortedSources.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

            for (Map.Entry<String, Double> entry : sortedSources) {
                sb.append(String.format("%s: %.1f%%\n", entry.getKey(), entry.getValue()));
            }

            return sb.toString();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RealTimeAnalyticsDashboard dashboard = new RealTimeAnalyticsDashboard();

        // Simulate incoming events
        Random rand = new Random();
        String[] pages = {
                "/article/breaking-news",
                "/sports/championship",
                "/tech/ai-breakthrough",
                "/entertainment/movie-review"
        };
        String[] sources = {"google", "facebook", "direct", "twitter"};

        System.out.println("Processing events...\n");

        // Process 1000 events
        for (int i = 0; i < 1000; i++) {
            String page = pages[rand.nextInt(pages.length)];
            String userId = "user_" + rand.nextInt(500);
            String source = sources[rand.nextInt(sources.length)];

            dashboard.processEvent(new PageViewEvent(page, userId, source));
        }

        // Display dashboard
        System.out.println(dashboard.getDashboard());

        dashboard.shutdown();
    }
}
