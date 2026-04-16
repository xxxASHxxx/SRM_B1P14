package com.trainconsist.main;

import com.trainconsist.exception.CargoSafetyException;

import com.trainconsist.exception.InvalidCapacityException;
import com.trainconsist.model.Bogie;
import com.trainconsist.model.GoodsBogie;
import com.trainconsist.model.PassengerBogie;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Main entry point for the Train Consist Management Application.
 *
 * @author Ashmit
 * @version 1.0
 */
public class TrainConsistApp {

    static void assignCargo(String shape, String cargo) throws CargoSafetyException {
        if ("rectangular".equals(shape) && "Petroleum".equals(cargo)) {
            throw new CargoSafetyException("Petroleum cannot be assigned to rectangular bogie.");
        }
        System.out.println("Cargo assigned: " + cargo + " to " + shape + " bogie");
    }

    static void searchBogie(List<String> bogies, String key) {
        if (bogies.isEmpty()) {
            throw new IllegalStateException("Cannot search: Train has no bogies loaded.");
        }
        boolean found = false;
        for (String b : bogies) {
            if (b.equals(key)) {
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println(key + " found.");
        } else {
            System.out.println(key + " not found.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App ===");

        // UC1: Initialize an empty bogie list
        List<String> bogies = new ArrayList<>();
        System.out.println("Initial bogie count: " + bogies.size());

        // UC2: Dynamic Bogie Insertion and Removal
        System.out.println("\n--- UC2: Dynamic Bogie Insertion and Removal ---");
        ArrayList<String> passengerBogies = new ArrayList<>();
        passengerBogies.add("Sleeper");
        passengerBogies.add("AC Chair");
        passengerBogies.add("First Class");
        System.out.println("After insertions: " + passengerBogies);

        passengerBogies.remove("AC Chair");
        System.out.println("Contains 'Sleeper': " + passengerBogies.contains("Sleeper"));
        System.out.println("Final list: " + passengerBogies);

        // UC3: Unique Bogie ID Enforcement with HashSet
        System.out.println("\n--- UC3: Unique Bogie ID Enforcement with HashSet ---");
        Set<String> bogieIdSet = new HashSet<>();
        bogieIdSet.add("BG-001");
        bogieIdSet.add("BG-002");
        bogieIdSet.add("BG-003");
        bogieIdSet.add("BG-001"); // duplicate
        bogieIdSet.add("BG-004");
        System.out.println("Bogie ID Set: " + bogieIdSet);
        System.out.println("Total unique bogie IDs: " + bogieIdSet.size());

        // UC4: Ordered Consist Management with LinkedList
        System.out.println("\n--- UC4: Ordered Consist Management with LinkedList ---");
        LinkedList<String> trainConsist = new LinkedList<>();
        trainConsist.add("Engine");
        trainConsist.add("Sleeper");
        trainConsist.add("AC");
        trainConsist.add("Cargo");
        trainConsist.add("Guard");
        System.out.println("Initial consist: " + trainConsist);

        trainConsist.add(2, "Pantry Car");
        System.out.println("After inserting Pantry Car at index 2: " + trainConsist);

        trainConsist.removeFirst();
        trainConsist.removeLast();
        System.out.println("Final ordered consist: " + trainConsist);

        // UC5: Insertion-Ordered Unique Formation with LinkedHashSet
        System.out.println("\n--- UC5: Insertion-Ordered Unique Formation with LinkedHashSet ---");
        LinkedHashSet<String> trainFormation = new LinkedHashSet<>();
        trainFormation.add("Engine");
        trainFormation.add("Sleeper");
        trainFormation.add("Cargo");
        trainFormation.add("Guard");
        trainFormation.add("Sleeper"); // duplicate
        System.out.println("Train Formation (insertion order preserved):");
        for (String bogie : trainFormation) {
            System.out.println("  " + bogie);
        }
        System.out.println("Formation size: " + trainFormation.size());

        // UC6: Bogie-Capacity Mapping with HashMap
        System.out.println("\n--- UC6: Bogie-Capacity Mapping with HashMap ---");
        Map<String, Integer> bogieCapacity = new HashMap<>();
        bogieCapacity.put("Sleeper", 72);
        bogieCapacity.put("AC Chair", 64);
        bogieCapacity.put("First Class", 48);
        
        System.out.println("Bogie Capacities:");
        for (Map.Entry<String, Integer> entry : bogieCapacity.entrySet()) {
            System.out.println("Bogie: " + entry.getKey() + " | Capacity: " + entry.getValue());
        }

        // UC7: Custom Object Sorting with Comparator
        System.out.println("\n--- UC7: Custom Object Sorting with Comparator ---");
        List<Bogie> passengerBogieObjects = new ArrayList<>();
        passengerBogieObjects.add(new Bogie("Sleeper", 72));
        passengerBogieObjects.add(new Bogie("AC Chair", 64));
        passengerBogieObjects.add(new Bogie("First Class", 48));

        passengerBogieObjects.sort(Comparator.comparingInt(b -> b.capacity));
        
        System.out.println("Bogies sorted by capacity:");
        for (Bogie b : passengerBogieObjects) {
            System.out.println(b);
        }

        // UC8: Stream Filtering by Capacity
        System.out.println("\n--- UC8: Stream Filtering by Capacity ---");
        List<Bogie> highCapacityBogies = passengerBogieObjects.stream()
            .filter(b -> b.capacity > 60)
            .collect(Collectors.toList());
        
        System.out.println("Bogies with capacity > 60:");
        for (Bogie b : highCapacityBogies) {
            System.out.println(b);
        }

        // UC9: Grouping Bogies with Stream Collectors
        System.out.println("\n--- UC9: Grouping Bogies with Stream Collectors ---");
        Map<String, List<Bogie>> groupedBogies = passengerBogieObjects.stream()
            .collect(Collectors.groupingBy(b -> b.name));

        System.out.println("Grouped Bogies:");
        for (Map.Entry<String, List<Bogie>> entry : groupedBogies.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // UC10: Capacity Aggregation with Stream reduce()
        System.out.println("\n--- UC10: Capacity Aggregation with Stream reduce() ---");
        int totalCapacity = passengerBogieObjects.stream()
            .map(b -> b.capacity)
            .reduce(0, Integer::sum);
        
        System.out.println("Total seating capacity: " + totalCapacity);

        // UC11: Input Validation with Regular Expressions
        System.out.println("\n--- UC11: Input Validation with Regular Expressions ---");
        Pattern trainIdPattern = Pattern.compile("TRN-\\d{4}");
        Pattern cargoCodePattern = Pattern.compile("PET-[A-Z]{2}");

        String[] trainIdTests = {"TRN-1234", "TRN-AB"};
        for (String id : trainIdTests) {
            Matcher m = trainIdPattern.matcher(id);
            System.out.println(id + " -> " + (m.matches() ? "Valid" : "Invalid"));
        }

        String[] cargoCodeTests = {"PET-AB", "PET-12"};
        for (String code : cargoCodeTests) {
            Matcher m = cargoCodePattern.matcher(code);
            System.out.println(code + " -> " + (m.matches() ? "Valid" : "Invalid"));
        }

        // UC12: Safety Compliance Check with Stream allMatch()
        System.out.println("\n--- UC12: Safety Compliance Check with Stream allMatch() ---");
        List<GoodsBogie> goodsBogies = new ArrayList<>();
        goodsBogies.add(new GoodsBogie("cylindrical", "Petroleum"));
        goodsBogies.add(new GoodsBogie("cylindrical", "Petroleum"));
        goodsBogies.add(new GoodsBogie("rectangular", "Coal"));

        boolean isSafeCompliant = goodsBogies.stream()
            .allMatch(g -> g.shape.equals("cylindrical") ? g.cargo.equals("Petroleum") : true);

        System.out.println("Train safety compliant: " + isSafeCompliant);

        // UC13: Performance Benchmarking Loop vs Stream
        System.out.println("\n--- UC13: Performance Benchmarking Loop vs Stream ---");
        for (int i = 0; i < 10; i++) {
            passengerBogieObjects.add(new Bogie("Bogie " + i, 30 + (i * 10)));
        }

        long startLoop = System.nanoTime();
        List<Bogie> loopFiltered = new ArrayList<>();
        for (Bogie b : passengerBogieObjects) {
            if (b.capacity > 60) {
                loopFiltered.add(b);
            }
        }
        long endLoop = System.nanoTime();
        System.out.println("Loop time: " + (endLoop - startLoop) + " ns");

        long startStream = System.nanoTime();
        List<Bogie> streamFiltered = passengerBogieObjects.stream()
            .filter(b -> b.capacity > 60)
            .collect(Collectors.toList());
        long endStream = System.nanoTime();
        System.out.println("Stream time: " + (endStream - startStream) + " ns");

        // UC14: Custom Exception for Invalid Bogie Capacity
        System.out.println("\n--- UC14: Custom Exception for Invalid Bogie Capacity ---");
        try {
            PassengerBogie pb1 = new PassengerBogie("Sleeper", 72);
            System.out.println("Success: Created " + pb1);
        } catch (InvalidCapacityException e) {
            System.out.println("Failed: " + e.getMessage());
        }

        try {
            PassengerBogie pb2 = new PassengerBogie("Invalid", -5);
            System.out.println("Success: Created " + pb2);
        } catch (InvalidCapacityException e) {
            System.out.println("Exception Caught: " + e.getMessage());
        }

        // UC15: Runtime Exception for Unsafe Cargo Assignment
        System.out.println("\n--- UC15: Runtime Exception for Unsafe Cargo Assignment ---");
        assignCargo("cylindrical", "Petroleum");

        try {
            assignCargo("rectangular", "Petroleum");
        } catch (CargoSafetyException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        } finally {
            System.out.println("Cargo assignment attempt complete.");
        }

        // UC16: Bubble Sort on Bogie Capacities
        System.out.println("\n--- UC16: Bubble Sort on Bogie Capacities ---");
        int[] bogieCapacities = {72, 48, 64, 36, 80, 52};
        System.out.println("Before Bubble Sort: " + Arrays.toString(bogieCapacities));

        for (int i = 0; i < bogieCapacities.length - 1; i++) {
            for (int j = 0; j < bogieCapacities.length - i - 1; j++) {
                if (bogieCapacities[j] > bogieCapacities[j + 1]) {
                    int temp = bogieCapacities[j];
                    bogieCapacities[j] = bogieCapacities[j + 1];
                    bogieCapacities[j + 1] = temp;
                }
            }
        }
        System.out.println("After Bubble Sort: " + Arrays.toString(bogieCapacities));

        // UC17: Alphabetical Sort Using Arrays.sort()
        System.out.println("\n--- UC17: Alphabetical Sort Using Arrays.sort() ---");
        String[] bogieTypes = {"Sleeper", "Guard", "Engine", "AC Chair", "Pantry", "First Class"};
        System.out.println("Before Arrays.sort(): " + Arrays.toString(bogieTypes));
        Arrays.sort(bogieTypes);
        System.out.println("After Arrays.sort(): " + Arrays.toString(bogieTypes));

        // UC18: Linear Search for Bogie ID
        System.out.println("\n--- UC18: Linear Search for Bogie ID ---");
        String[] bogieIds = {"BG-001", "BG-005", "BG-009", "BG-003", "BG-007"};
        String searchKey = "BG-003";
        boolean found = false;

        System.out.println("Linear Search Result:");
        for (int i = 0; i < bogieIds.length; i++) {
            if (searchKey.equals(bogieIds[i])) {
                System.out.println("Bogie " + searchKey + " found at index: " + i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Bogie not found.");
        }

        // UC19: Binary Search on Sorted Bogie IDs
        System.out.println("\n--- UC19: Binary Search on Sorted Bogie IDs ---");
        String[] sortedBogieIds = {"BG-001", "BG-003", "BG-005", "BG-007", "BG-009"};
        String binaryKey = "BG-007";
        boolean binaryFound = false;

        int low = 0;
        int high = sortedBogieIds.length - 1;

        System.out.println("Binary Search Result:");
        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison = binaryKey.compareTo(sortedBogieIds[mid]);

            if (comparison == 0) {
                System.out.println("Bogie " + binaryKey + " found at index: " + mid);
                binaryFound = true;
                break;
            } else if (comparison < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (!binaryFound) {
            System.out.println("Bogie not found.");
        }

        // UC20: Defensive Search with IllegalStateException
        System.out.println("\n--- UC20: Defensive Search with IllegalStateException ---");
        List<String> emptyList = new ArrayList<>();
        System.out.println("Testing with empty list:");
        try {
            searchBogie(emptyList, "Sleeper");
        } catch (IllegalStateException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }

        System.out.println("\nTesting with populated list:");
        List<String> populatedList = new ArrayList<>();
        populatedList.add("Sleeper");
        populatedList.add("AC");
        populatedList.add("Guard");
        searchBogie(populatedList, "AC");
    }
}
