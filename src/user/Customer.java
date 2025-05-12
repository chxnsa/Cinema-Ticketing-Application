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

    public void viewMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            System.out.println("Belum ada film yang tersedia.");
            return;
        }

        System.out.println("\n===== DAFTAR FILM DAN JADWAL =====");
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            System.out.println((i + 1) + ". " + movie);

            List<Schedule> schedules = movie.getSchedules();
            if (schedules.isEmpty()) {
                System.out.println("   - Belum ada jadwal untuk film ini.");
            } else {
                System.out.println("   Jadwal:");
                for (int j = 0; j < schedules.size(); j++) {
                    System.out.println("   - " + schedules.get(j));
                }
            }
            System.out.println();
        }
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
    @Override
    public void printInfo() {
        System.out.println("=== INFORMASI USER ===");
        System.out.println("Username: " + username);
        System.out.println("Role: Customer");
        System.out.println("Jumlah Reservasi: " + reservations.size());
    }

}
