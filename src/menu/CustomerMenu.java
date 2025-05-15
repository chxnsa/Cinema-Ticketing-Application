package menu;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import user.User;
import user.Customer;
import movie.Movie;
import movie.Schedule;
import reservation.Reservation;

public class CustomerMenu implements Menu {
    private Scanner scanner;
    private User currentUser;
    private List<Movie> movies;
    private List<Reservation> reservations;

    public CustomerMenu(Scanner scanner, List<Movie> movies, List<Reservation> reservations, User currentUser) {
        this.scanner = scanner;
        this.currentUser = currentUser;
        this.movies = movies;
        this.reservations = reservations;
    }

    @Override
    public void display() {
        System.out.println("\n=== MENU PELANGGAN ===");
        System.out.println("1. Lihat Film & Jadwal");
        System.out.println("2. Pesan Tiket");
        System.out.println("3. Lihat Riwayat Reservasi");
        System.out.println("4. Logout");
        System.out.print("Pilih (1-4): ");
    }

    @Override
    public void processChoice(String choice) {
        Customer cust = (Customer) currentUser;

        if (choice.isEmpty()) {
            System.out.println("Input tidak boleh kosong.");
            return;
        }

        switch (choice) {
            case "1":
                cust.viewMovies(movies);
                break;
            case "2":
                ticketReservation(cust);
                break;
            case "3":
                cust.viewReservations(reservations);
                break;
            case "4":
                System.out.println("Logout berhasil.");
                return;
            default:
                System.out.println("Pilihan tidak valid. Silakan pilih 1-4.");
        }
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            display();
            String choice = scanner.nextLine().trim();
            if (choice.equals("4")) {
                running = false;
            } else {
                processChoice(choice);
            }
        }
    }

    private void ticketReservation(Customer cust) {
        if (movies.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia saat ini.");
            return;
        }

        System.out.println("\n===== PESAN TIKET =====");
        cust.viewMovies(movies);

        int filmIndex;
        Schedule selectedSchedule;

        while (true) {
            System.out.print("Pilih nomor film: ");
            String ifi = scanner.nextLine().trim();
            if (ifi.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
                continue;
            }
            try {
                filmIndex = Integer.parseInt(ifi) - 1;
                if (filmIndex < 0 || filmIndex >= movies.size()) {
                    System.out.println("Pilihan film tidak valid.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }

        Movie selectedMovie = movies.get(filmIndex);
        List<Schedule> schedules = selectedMovie.getSchedules();

        if (schedules.isEmpty()) {
            System.out.println("Film ini belum memiliki jadwal tayang.");
            return;
        }

        List<Schedule> futureSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (!schedule.isPast()) {
                futureSchedules.add(schedule);
            }
        }

        if (futureSchedules.isEmpty()) {
            System.out.println("Tidak ada jadwal dalam waktu mendatang untuk film ini.");
            return;
        }

        int scheduleIndex;
        while (true) {
            System.out.println("\n=== PILIH JADWAL ===");
            for (int i = 0; i < schedules.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, schedules.get(i));
            }
            System.out.print("Pilih nomor jadwal: ");
            String ijs = scanner.nextLine().trim();
            if (ijs.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
                continue;
            }
            try {
                scheduleIndex = Integer.parseInt(ijs) - 1;
                if (scheduleIndex < 0 || scheduleIndex >= schedules.size()) {
                    System.out.println("Pilihan jadwal tidak valid.");
                    continue;
                }
                selectedSchedule = schedules.get(scheduleIndex);

                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }

        int numTickets;
        while (true) {
            System.out.print("Masukkan jumlah tiket yang ingin dipesan: ");
            String ti = scanner.nextLine().trim();
            if (ti.isEmpty()) {
                System.out.println("Jumlah tiket tidak boleh kosong.");
                continue;
            }
            try {
                numTickets = Integer.parseInt(ti);
                if (numTickets <= 0) {
                    System.out.println("Jumlah tiket harus lebih dari 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }

        List<Integer> seatNumbers = new ArrayList<>();
        int maxSeat = selectedSchedule.getTotalSeats();

        for (int i = 0; i < numTickets; i++) {
            while (true) {
                System.out.print("Masukkan nomor kursi untuk tiket " + (i + 1) + " (1 - " + maxSeat + "): ");
                String si = scanner.nextLine().trim();
                if (si.isEmpty()) {
                    System.out.println("Nomor kursi tidak boleh kosong.");
                    continue;
                }
                try {
                    int seatNumber = Integer.parseInt(si);
                    if (seatNumber < 1 || seatNumber > maxSeat) {
                        System.out.println("Nomor kursi tidak valid.");
                        continue;
                    }
                    if (!selectedSchedule.isSeatAvailable(seatNumber)) {
                        System.out.println("Maaf, kursi " + seatNumber + " sudah terpesan.");
                        continue;
                    }
                    if (seatNumbers.contains(seatNumber)) {
                        System.out.println("Anda sudah memilih kursi ini. Silakan pilih kursi lain.");
                        continue;
                    }
                    seatNumbers.add(seatNumber);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Harap masukkan angka.");
                }
            }
        }

        double totalPrice = selectedSchedule.getPrice() * numTickets;

        System.out.println("\n=== KONFIRMASI PESANAN ===");
        System.out.println("Film   : " + selectedMovie.getTitle());
        System.out.println("Jadwal : " + selectedSchedule.getDateTime());
        System.out.println("Studio : " + selectedSchedule.getStudioName());
        System.out.println("Kursi  : ");
        for (int i = 0; i < numTickets; i++) {
            System.out.println("        - Kursi " + seatNumbers.get(i));
        }
        System.out.println("Harga per tiket  : Rp " + String.format("%,.0f", selectedSchedule.getPrice()));
        System.out.println("Total Harga      : Rp " + String.format("%,.0f", totalPrice));

        while (true) {
            System.out.print("Lanjutkan pemesanan? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                cust.bookTickets(selectedMovie, selectedSchedule, seatNumbers, reservations);
                System.out.println("Reservasi berhasil!");
                return;
            } else if (confirm.equalsIgnoreCase("n")) {
                System.out.println("Reservasi dibatalkan.");
                return;
            } else {
                System.out.println("Input hanya y atau n.");
            }
        }
    }
}