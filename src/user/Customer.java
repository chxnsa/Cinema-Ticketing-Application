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

    public void bookTickets(Movie movie, Schedule schedule, List<Integer> seatNumbers) {
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