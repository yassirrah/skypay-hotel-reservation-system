package org.skypay.hotelreservationsystem;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service {
    // as requested: use ArrayList, no repositories
    private final List<Room> rooms = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    private int nextBookingId = 1;

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        Room existing = findRoom(roomNumber);
        if (existing == null) {
            rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
        } else {
            // update room; previous bookings are safe because we snapshot values
            existing.setType(roomType);
            existing.setPricePerNight(roomPricePerNight);
        }
    }

    public void setUser(int userId, int balance) {
        User existing = findUser(userId);
        if (existing == null) {
            users.add(new User(userId, balance));
        } else {
            existing.setBalance(balance);
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("checkIn and checkOut cannot be null");
        }

        // Only year/month/day matter
        LocalDate in = toLocalDate(checkIn);
        LocalDate out = toLocalDate(checkOut);

        if (!in.isBefore(out)) {
            throw new IllegalArgumentException("check-in date must be before check-out date");
        }

        Room room = findRoom(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room " + roomNumber + " does not exist");
        }

        User user = findUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("User " + userId + " does not exist");
        }

        // Check availability: no overlapping bookings for this room
        for (Booking b : bookings) {
            if (b.getRoomNumberAtBooking() == roomNumber &&
                    datesOverlap(in, out, toLocalDate(b.getCheckIn()), toLocalDate(b.getCheckOut()))) {
                throw new IllegalStateException("Room " + roomNumber + " is not available in that period");
            }
        }

        long nights = ChronoUnit.DAYS.between(in, out);
        if (nights <= 0) {
            throw new IllegalArgumentException("stay must be at least one night");
        }

        int totalPrice = (int) (nights * room.getPricePerNight());
        if (user.getBalance() < totalPrice) {
            throw new IllegalStateException("User " + userId + " does not have enough balance");
        }

        // debit first, then snapshot
        user.debit(totalPrice);

        Booking booking = new Booking(nextBookingId++, room, user, checkIn, checkOut);
        bookings.add(booking);
    }

    public void printAll() {
        System.out.println("=== ROOMS (latest to oldest) ===");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            System.out.println(rooms.get(i));
        }

        System.out.println("=== BOOKINGS (latest to oldest) ===");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            System.out.println(bookings.get(i));
        }
    }

    public void printAllUsers() {
        System.out.println("=== USERS (latest to oldest) ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            System.out.println(users.get(i));
        }
    }

    private Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return r;
            }
        }
        return null;
    }

    private User findUser(int userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return null;
    }

    private static LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // [start1, end1) overlaps [start2, end2) if start1 < end2 and start2 < end1
    private static boolean datesOverlap(LocalDate s1, LocalDate e1,
                                        LocalDate s2, LocalDate e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }
}
