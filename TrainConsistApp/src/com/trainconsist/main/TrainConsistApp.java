package com.trainconsist.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Main entry point for the Train Consist Management Application.
 *
 * @author Ashmit
 * @version 1.0
 */
public class TrainConsistApp {

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
    }
}
