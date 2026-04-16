package com.bookmystay.model;

/**
 * Represents a room reservation request.
 */
public class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String status;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.status = "PENDING";
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ResID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType + ", Status: " + status;
    }
}
