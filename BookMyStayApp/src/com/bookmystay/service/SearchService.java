package com.bookmystay.service;

import com.bookmystay.inventory.RoomInventory;
import com.bookmystay.model.Room;
import java.util.List;

/**
 * Service to search available rooms.
 */
public class SearchService {
    private RoomInventory inventory;
    private List<Room> rooms;

    public SearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms Search Results:");
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Availability: " + available);
            }
        }
    }
}
