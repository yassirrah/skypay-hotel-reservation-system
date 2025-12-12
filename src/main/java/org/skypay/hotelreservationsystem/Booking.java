package org.skypay.hotelreservationsystem;

import java.util.Date;

public class Booking {
    private final int bookingId;

    // Snapshot of room at booking time
    private final int roomNumberAtBooking;
    private final RoomType roomTypeAtBooking;
    private final int roomPricePerNightAtBooking;

    // Snapshot of user at booking time
    private final int userIdAtBooking;
    private final int userBalanceAfterBooking;

    private final Date checkIn;
    private final Date checkOut;

    public Booking(int bookingId, Room room, User user,
                   Date checkIn, Date checkOut) {

        if (room == null || user == null) {
            throw new IllegalArgumentException("room and user cannot be null");
        }
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("dates cannot be null");
        }

        this.bookingId = bookingId;

        this.roomNumberAtBooking = room.getRoomNumber();
        this.roomTypeAtBooking = room.getType();
        this.roomPricePerNightAtBooking = room.getPricePerNight();

        this.userIdAtBooking = user.getId();
        this.userBalanceAfterBooking = user.getBalance();

        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getRoomNumberAtBooking() {
        return roomNumberAtBooking;
    }

    public RoomType getRoomTypeAtBooking() {
        return roomTypeAtBooking;
    }

    public int getRoomPricePerNightAtBooking() {
        return roomPricePerNightAtBooking;
    }

    public int getUserIdAtBooking() {
        return userIdAtBooking;
    }

    public int getUserBalanceAfterBooking() {
        return userBalanceAfterBooking;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + bookingId +
                ", room=" + roomNumberAtBooking +
                ", roomType=" + roomTypeAtBooking +
                ", pricePerNight=" + roomPricePerNightAtBooking +
                ", user=" + userIdAtBooking +
                ", userBalanceAfterBooking=" + userBalanceAfterBooking +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
