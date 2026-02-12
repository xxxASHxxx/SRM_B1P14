package WEEK1and2;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class ParkingLotSystem {
    private final ParkingSpot[] spots;
    private final int capacity;
    private int occupiedCount;
    private int totalProbes;
    private int totalParkings;
    private final Map<Integer, Integer> hourlyOccupancy; // Track by hour

    enum SpotStatus {
        EMPTY, OCCUPIED, DELETED
    }

    static class ParkingSpot {
        SpotStatus status;
        String licensePlate;
        LocalDateTime entryTime;
        int spotNumber;

        public ParkingSpot(int spotNumber) {
            this.spotNumber = spotNumber;
            this.status = SpotStatus.EMPTY;
        }
    }

    static class ParkingResult {
        boolean success;
        int spotNumber;
        int probeCount;
        String message;

        public ParkingResult(boolean success, int spotNumber, int probeCount, String message) {
            this.success = success;
            this.spotNumber = spotNumber;
            this.probeCount = probeCount;
            this.message = message;
        }

        @Override
        public String toString() {
            if (success) {
                return String.format("Assigned spot #%d (%d probes)", spotNumber, probeCount);
            }
            return message;
        }
    }

    static class ExitResult {
        int spotNumber;
        Duration duration;
        double fee;

        public ExitResult(int spotNumber, Duration duration, double fee) {
            this.spotNumber = spotNumber;
            this.duration = duration;
            this.fee = fee;
        }

        @Override
        public String toString() {
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            return String.format("Spot #%d freed, Duration: %dh %dm, Fee: $%.2f",
                    spotNumber, hours, minutes, fee);
        }
    }

    public ParkingLotSystem(int capacity) {
        this.capacity = capacity;
        this.spots = new ParkingSpot[capacity];
        this.occupiedCount = 0;
        this.totalProbes = 0;
        this.totalParkings = 0;
        this.hourlyOccupancy = new HashMap<>();

        for (int i = 0; i < capacity; i++) {
            spots[i] = new ParkingSpot(i);
        }
    }

    // Hash function for license plate
    private int hashLicensePlate(String licensePlate) {
        int hash = 0;
        for (char c : licensePlate.toCharArray()) {
            hash = (hash * 31 + c) % capacity;
        }
        return Math.abs(hash);
    }

    // Park vehicle with linear probing
    public ParkingResult parkVehicle(String licensePlate) {
        if (occupiedCount >= capacity) {
            return new ParkingResult(false, -1, 0, "Parking lot full");
        }

        int preferredSpot = hashLicensePlate(licensePlate);
        int probeCount = 0;
        int currentSpot = preferredSpot;

        // Linear probing
        while (spots[currentSpot].status == SpotStatus.OCCUPIED) {
            probeCount++;
            currentSpot = (currentSpot + 1) % capacity;

            // Full loop check (shouldn't happen if occupiedCount < capacity)
            if (currentSpot == preferredSpot) {
                return new ParkingResult(false, -1, probeCount, "No available spot found");
            }
        }

        // Assign spot
        spots[currentSpot].status = SpotStatus.OCCUPIED;
        spots[currentSpot].licensePlate = licensePlate;
        spots[currentSpot].entryTime = LocalDateTime.now();

        occupiedCount++;
        totalProbes += probeCount;
        totalParkings++;

        // Track hourly occupancy
        int hour = LocalDateTime.now().getHour();
        hourlyOccupancy.merge(hour, 1, Integer::sum);

        return new ParkingResult(true, currentSpot, probeCount,
                probeCount == 0 ? "Preferred spot" : "After probing");
    }

    // Exit vehicle and calculate fee
    public ExitResult exitVehicle(String licensePlate) {
        // Find vehicle
        int spotNumber = findVehicle(licensePlate);

        if (spotNumber == -1) {
            return null;
        }

        ParkingSpot spot = spots[spotNumber];
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(spot.entryTime, exitTime);

        // Calculate fee: $5 base + $2.50 per hour
        double hours = duration.toMinutes() / 60.0;
        double fee = 5.0 + (hours * 2.50);

        // Free the spot
        spot.status = SpotStatus.DELETED; // Use DELETED to maintain probe chain
        spot.licensePlate = null;
        spot.entryTime = null;

        occupiedCount--;

        return new ExitResult(spotNumber, duration, fee);
    }

    // Find vehicle by license plate
    private int findVehicle(String licensePlate) {
        int preferredSpot = hashLicensePlate(licensePlate);
        int currentSpot = preferredSpot;

        do {
            if (spots[currentSpot].status == SpotStatus.OCCUPIED &&
                    licensePlate.equals(spots[currentSpot].licensePlate)) {
                return currentSpot;
            }

            if (spots[currentSpot].status == SpotStatus.EMPTY) {
                break; // Vehicle not found
            }

            currentSpot = (currentSpot + 1) % capacity;
        } while (currentSpot != preferredSpot);

        return -1;
    }

    // Find nearest available spot to entrance (assume entrance at spot 0)
    public int findNearestSpot() {
        for (int i = 0; i < capacity; i++) {
            if (spots[i].status != SpotStatus.OCCUPIED) {
                return i;
            }
        }
        return -1;
    }

    // Get parking statistics
    public String getStatistics() {
        double occupancyRate = (occupiedCount * 100.0) / capacity;
        double avgProbes = totalParkings > 0 ? (totalProbes * 1.0 / totalParkings) : 0;

        // Find peak hour
        int peakHour = -1;
        int maxOccupancy = 0;
        for (Map.Entry<Integer, Integer> entry : hourlyOccupancy.entrySet()) {
            if (entry.getValue() > maxOccupancy) {
                maxOccupancy = entry.getValue();
                peakHour = entry.getKey();
            }
        }

        String peakHourStr = peakHour >= 0 ?
                String.format("%d-%d PM", peakHour, peakHour + 1) : "N/A";

        return String.format("Occupancy: %.0f%%, Avg Probes: %.1f, Peak Hour: %s",
                occupancyRate, avgProbes, peakHourStr);
    }

    // Get current load factor
    public double getLoadFactor() {
        return (occupiedCount * 1.0) / capacity;
    }

    public static void main(String[] args) throws InterruptedException {
        ParkingLotSystem parkingLot = new ParkingLotSystem(500);

        // Test parking
        System.out.println(parkingLot.parkVehicle("ABC-1234"));
        System.out.println(parkingLot.parkVehicle("ABC-1235"));
        System.out.println(parkingLot.parkVehicle("XYZ-9999"));

        // Simulate some time passing
        Thread.sleep(100);

        // Exit vehicle
        ExitResult exit = parkingLot.exitVehicle("ABC-1234");
        System.out.println(exit);

        // Park more vehicles to test load
        System.out.println("\nParking 100 more vehicles...");
        for (int i = 0; i < 100; i++) {
            parkingLot.parkVehicle("VEH-" + String.format("%04d", i));
        }

        // Statistics
        System.out.println("\n" + parkingLot.getStatistics());
        System.out.println("Load Factor: " + String.format("%.2f", parkingLot.getLoadFactor()));
        System.out.println("Nearest available spot to entrance: #" + parkingLot.findNearestSpot());
    }
}
