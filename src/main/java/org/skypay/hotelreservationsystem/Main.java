package org.skypay.hotelreservationsystem;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Service service = new Service();

        // Create 3 rooms
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        // Create 2 users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        Date d3006 = date(30, 6, 2026);
        Date d0707 = date(7, 7, 2026);
        Date d0807 = date(8, 7, 2026);
        Date d0907 = date(9, 7, 2026);

        // 1) User 1 tries booking Room 2 from 30/06/2026 to 07/07/2026 (7 nights)
        try {
            service.bookRoom(1, 2, d3006, d0707);
            System.out.println("Booking 1 succeeded");
        } catch (Exception e) {
            System.out.println("Booking 1 failed because: " + e.getMessage());
        }

        // 2) User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026 (invalid dates)
        try {
            service.bookRoom(1, 2, d0707, d3006);
            System.out.println("Booking 2 succeeded");
        } catch (Exception e) {
            System.out.println("Booking 2 failed because: " + e.getMessage());
        }

        // 3) User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026 (1 night)
        try {
            service.bookRoom(1, 1, d0707, d0807);
            System.out.println("Booking 3 succeeded");
        } catch (Exception e) {
            System.out.println("Booking 3 failed because: " + e.getMessage());
        }

        // 4) User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)
        try {
            service.bookRoom(2, 1, d0707, d0907);
            System.out.println("Booking 4 succeeded");
        } catch (Exception e) {
            System.out.println("Booking 4 failed because: " + e.getMessage());
        }

        // 5) User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)
        try {
            service.bookRoom(2, 3, d0707, d0807);
            System.out.println("Booking 5 succeeded");
        } catch (Exception e) {
            System.out.println("Booking 5 failed because: " + e.getMessage());
        }

        // 6) setRoom(1, suite, 10000)
        service.setRoom(1, RoomType.SUITE, 10000);

        // Final result
        service.printAll();
        service.printAllUsers();
    }

    private static Date date(int day, int month, int year) {
        LocalDate ld = LocalDate.of(year, month, day);
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
