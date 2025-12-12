package org.skypay.hotelreservationsystem;

public class Room {
    private final int roomNumber;
    private RoomType type;
    private int pricePerNight;

    public Room(int roomNumber, RoomType type, int pricePerNight) {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("roomNumber must be > 0");
        }
        if (type == null) {
            throw new IllegalArgumentException("room type cannot be null");
        }
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("pricePerNight must be > 0");
        }
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        if (type == null) {
            throw new IllegalArgumentException("room type cannot be null");
        }
        this.type = type;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("pricePerNight must be > 0");
        }
        this.pricePerNight = pricePerNight;
    }


    @Override
    public String toString() {
        return "Room{" +
                "number=" + roomNumber +
                ", type=" + type +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}
