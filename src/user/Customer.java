package user;

import java.util.ArrayList;
import java.util.List;
import movie.Movie;
import movie.Schedule;
import reservation.Reservation;

public class Customer extends User {

    public Customer(String username, String password) {
        super(username, password);
    }

    public void bookTickets(Movie movie, Schedule schedule, List<Integer> seatNumbers, List<Reservation> reservations) {
        List<Reservation> newReservations = new ArrayList<>();

        if (!schedule.isAvailable()) {
            System.out.println("Maaf, kursi sudah penuh untuk jadwal ini.");
            return;
        }

        for (int seatNumber : seatNumbers) {
            if (seatNumber <= 0 || seatNumber > (schedule.getAvailableSeats() + schedule.getBookedSeats())) {
                System.out.println("Nomor kursi " + seatNumber + " tidak valid.");
                continue;
            }

            if (!schedule.isSeatAvailable(seatNumber)) {
                System.out.println("Maaf, kursi " + seatNumber + " sudah terpesan.");
                continue;
            }

            if (schedule.bookSeat(seatNumber)) {
                Reservation newReservation = new Reservation(this, schedule, seatNumber);
                newReservations.add(newReservation);
            } else {
                System.out.println("Maaf, pemesanan gagal untuk kursi " + seatNumber + ".");
            }
        }

        if (!newReservations.isEmpty()) {
            reservations.addAll(newReservations);
            System.out.println(newReservations.size() + " tiket berhasil dipesan.");
        } else {
            System.out.println("Tidak ada tiket yang berhasil dipesan.");
        }
    }

    public void viewReservations(List<Reservation> allReservations) {
        List<Reservation> userReservations = new ArrayList<>();

        for (Reservation reservation : allReservations) {
            if (reservation.getCustomer().getUsername().equals(this.getUsername())) {
                userReservations.add(reservation);
            }
        }

        if (userReservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }

        System.out.println("\n===== RIWAYAT RESERVASI =====");
        for (int i = 0; i < userReservations.size(); i++) {
            System.out.println("Reservasi #" + (i + 1));
            userReservations.get(i).printInfo();
            System.out.println();
        }
    }

    @Override
    public void printInfo() {
        System.out.println("=== INFORMASI USER ===");
        System.out.println("Username: " + username);
        System.out.println("Role: Customer");

    }
}
