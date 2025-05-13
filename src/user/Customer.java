package user;

import java.util.ArrayList;
import java.util.List;
import movie.Movie;
import movie.Schedule;
import reservation.Reservation;

public class Customer extends User {
    private List<Reservation> reservations;

    public Customer(String username, String password) {
        super(username, password);
        this.reservations = new ArrayList<>();
    }

    public Reservation bookTicket(Movie movie, Schedule schedule, int seatNumber) {

        if (!schedule.isAvailable()) {
            System.out.println("Maaf, kursi sudah penuh untuk jadwal ini.");
            return null;
        }

        if (seatNumber <= 0 || seatNumber > (schedule.getAvailableSeats() + schedule.getBookedSeats())) {
            System.out.println("Nomor kursi tidak valid.");
            return null;
        }

        if (schedule.bookSeat()) {
            Reservation newReservation = new Reservation(this, schedule, seatNumber);
            reservations.add(newReservation);
            return newReservation;
        } else {
            System.out.println("Maaf, pemesanan gagal. Kursi tidak tersedia.");
            return null;
        }
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }

        System.out.println("\n===== RIWAYAT RESERVASI =====");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println("Reservasi #" + (i + 1));
            reservations.get(i).printInfo();
            System.out.println();
        }
    }

    @Override
    public void printInfo() {
        System.out.println("=== INFORMASI USER ===");
        System.out.println("Username: " + username);
        System.out.println("Role: Customer");
        System.out.println("Jumlah Reservasi: " + reservations.size());
    }

}

