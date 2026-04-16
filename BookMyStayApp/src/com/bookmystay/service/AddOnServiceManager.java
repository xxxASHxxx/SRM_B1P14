package com.bookmystay.service;

import com.bookmystay.model.AddOnService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for handling add-on services for reservations.
 */
public class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
        System.out.println("Service '" + service.getServiceName() + "' added to reservation " + reservationId);
    }

    public double getTotalCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        if (services == null) return 0.0;
        
        double total = 0.0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }

    public void displayServicesForReservation(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        System.out.println("Add-On Services for Reservation ID: " + reservationId);
        if (services == null || services.isEmpty()) {
            System.out.println("No services added.");
            return;
        }
        for (AddOnService service : services) {
            System.out.println("- " + service.getServiceName() + " : Rs. " + service.getCost());
        }
    }
}
