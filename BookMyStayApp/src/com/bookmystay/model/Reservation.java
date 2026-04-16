package com.bookmystay.model;

import java.io.Serializable;

/**
 * Represents a room reservation request.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationId;
    private String guestName;
    private String roomType;
    private String status;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.status = "PENDING";
        this.roomId = null;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    @Override
    public String toString() {
        return "ResID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType + ", Status: " + status + (roomId != null ? ", RoomID: " + roomId : "");
    }
}
