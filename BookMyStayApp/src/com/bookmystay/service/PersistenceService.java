package com.bookmystay.service;

import com.bookmystay.inventory.RoomInventory;
import java.io.*;

/**
 * Service to handle saving and loading application state.
 */
public class PersistenceService {
    private static final String FILE_NAME = "hotel_data.dat";

    public static class AppState implements Serializable {
        private static final long serialVersionUID = 1L;
        public RoomInventory inventory;
        public BookingHistoryService history;
    }

    public void saveState(RoomInventory inventory, BookingHistoryService history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            AppState state = new AppState();
            state.inventory = inventory;
            state.history = history;
            oos.writeObject(state);
            System.out.println("System state persisted successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Failed to persist state: " + e.getMessage());
        }
    }

    public AppState loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            AppState state = (AppState) ois.readObject();
            System.out.println("System state recovered successfully from " + FILE_NAME);
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load persisted state or corrupted. Starting fresh. Reason: " + e.getMessage());
            return null;
        }
    }
}
