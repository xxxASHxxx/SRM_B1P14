package WEEK1and2;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FlashSaleInventoryManager {
    // Product inventory mapping
    private final ConcurrentHashMap<String, ProductStock> inventory;
    // Waiting lists for out-of-stock products
    private final ConcurrentHashMap<String, LinkedList<Long>> waitingLists;

    static class ProductStock {
        private int stockCount;
        private final ReentrantLock lock;

        public ProductStock(int initialStock) {
            this.stockCount = initialStock;
            this.lock = new ReentrantLock();
        }

        public int getStock() {
            return stockCount;
        }

        public boolean decrementStock() {
            lock.lock();
            try {
                if (stockCount > 0) {
                    stockCount--;
                    return true;
                }
                return false;
            } finally {
                lock.unlock();
            }
        }
    }

    public FlashSaleInventoryManager() {
        this.inventory = new ConcurrentHashMap<>();
        this.waitingLists = new ConcurrentHashMap<>();
    }

    // Add product with initial stock
    public void addProduct(String productId, int initialStock) {
        inventory.put(productId, new ProductStock(initialStock));
        waitingLists.put(productId, new LinkedList<>());
    }

    // O(1) stock check
    public int checkStock(String productId) {
        ProductStock stock = inventory.get(productId);
        return stock != null ? stock.getStock() : 0;
    }

    // Purchase with thread safety
    public PurchaseResult purchaseItem(String productId, long userId) {
        ProductStock stock = inventory.get(productId);

        if (stock == null) {
            return new PurchaseResult(false, 0, "Product not found", -1);
        }

        boolean success = stock.decrementStock();

        if (success) {
            int remaining = stock.getStock();
            return new PurchaseResult(true, remaining, "Success", -1);
        } else {
            // Add to waiting list
            LinkedList<Long> waitingList = waitingLists.get(productId);
            synchronized (waitingList) {
                waitingList.add(userId);
                int position = waitingList.size();
                return new PurchaseResult(false, 0,
                        "Added to waiting list", position);
            }
        }
    }

    // Restock product (notifies waiting list)
    public void restockProduct(String productId, int quantity) {
        ProductStock stock = inventory.get(productId);
        if (stock == null) return;

        stock.lock.lock();
        try {
            stock.stockCount += quantity;
        } finally {
            stock.lock.unlock();
        }

        // Process waiting list
        LinkedList<Long> waitingList = waitingLists.get(productId);
        synchronized (waitingList) {
            int processed = 0;
            while (!waitingList.isEmpty() && processed < quantity) {
                Long userId = waitingList.poll();
                System.out.println("Notifying user " + userId + " - item available");
                processed++;
            }
        }
    }

    static class PurchaseResult {
        boolean success;
        int remainingStock;
        String message;
        int waitingListPosition;

        public PurchaseResult(boolean success, int remainingStock,
                              String message, int waitingListPosition) {
            this.success = success;
            this.remainingStock = remainingStock;
            this.message = message;
            this.waitingListPosition = waitingListPosition;
        }

        @Override
        public String toString() {
            if (success) {
                return message + ", " + remainingStock + " units remaining";
            } else if (waitingListPosition > 0) {
                return message + ", position #" + waitingListPosition;
            }
            return message;
        }
    }

    public static void main(String[] args) {
        FlashSaleInventoryManager manager = new FlashSaleInventoryManager();

        // Add product with limited stock
        manager.addProduct("IPHONE15_256GB", 100);

        System.out.println("Initial stock: " + manager.checkStock("IPHONE15_256GB") + " units available");

        // Simulate purchases
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 12345L));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 67890L));

        // Simulate selling out
        for (int i = 0; i < 98; i++) {
            manager.purchaseItem("IPHONE15_256GB", 10000L + i);
        }

        // Try to purchase when out of stock
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 99999L));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 99998L));
    }
}
