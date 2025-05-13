import java.util.Scanner;
import user.User;
import user.Customer;
import user.Admin;
import movie.Movie;
import movie.Schedule;
import reservation.Reservation;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final List<Movie> movies = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        users.add(new Admin("admin", "admin"));

        showWelcomeScreen();

        scanner.close();
    }

    private static void showWelcomeScreen() {
        while (true) {
            System.out.println("\n=== SELAMAT DATANG DI SISTEM RESERVASI BIOSKOP ===");
            System.out.println("1. Registrasi");
            System.out.println("2. Login");
            System.out.println("3. Keluar");
            System.out.print("Pilih (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerUser();

                    break;
                case "2":
                    loginUser();
                    if (currentUser != null) {
                        showMainMenu();
                    } else {
                        System.out.println("Login gagal. Silakan coba lagi.");
                    }
                    break;
                case "3":
                    System.out.println("Terima kasih telah menggunakan sistem. Sampai jumpa!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1-3.");
            }
        }
    }

    private static void showMainMenu() {
        if (currentUser instanceof Admin) {
            showAdminMenu();
        } else {
            showCustomerMenu();
        }
    }

    private static void showCustomerMenu() {
        Customer cust = (Customer) currentUser;
        while (true) {
            System.out.println("\n--- MENU PELANGGAN ---");
            System.out.println("1. Lihat Film & Jadwal");
            System.out.println("2. Pesan Tiket");
            System.out.println("3. Lihat Riwayat Reservasi");
            System.out.println("4. Logout");
            System.out.print("Pilih (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    cust.viewMovies(movies);
                    break;
                case "2":
                    TicketReservation(cust);
                    break;
                case "3":
                    cust.viewReservations();
                    break;
                case "4":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1-4.");
            }
        }
    }

    private static void showAdminMenu() {
        Admin admin = (Admin) currentUser;
        while (true) {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1. Tambah Film");
            System.out.println("2. Tambah Jadwal Film");
            System.out.println("3. Lihat Film & Jadwal");
            System.out.println("4. Lihat Semua Reservasi");
            System.out.println("5. Logout");
            System.out.print("Pilih (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addMovieFlow(admin);
                    break;
                case "2":
                    addScheduleFlow(admin);
                    break;
                case "3":
                    admin.viewMovies(movies);
                    break;
                case "4":
                    admin.viewAllReservations(reservations);
                    break;
                case "5":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1-4.");
            }
        }
    }

    private static void registerUser() {
        System.out.println("\n--- REGISTRASI PELANGGAN BARU ---");

        String user;
        while (true) {
            System.out.print("Username: ");
            user = scanner.nextLine().trim();

            boolean isUsernameTaken = false;
            for (User u : users) {
                if (u.getUsername().equals(user)) {
                    isUsernameTaken = true;
                    break;
                }
            }

            if (isUsernameTaken) {
                System.out.println("Username sudah terdaftar! Silakan masukkan username lain.");
            } else {
                break;
            }
        }

        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            System.out.println("Username dan password tidak boleh kosong.");
            return;
        }

        Customer cust = new Customer(user, pass);
        users.add(cust);
        System.out.println("Registrasi berhasil. Silakan login.");
    }

    private static void loginUser() {
        System.out.println("\n--- LOGIN PELANGGAN/ADMIN ---");
        System.out.print("Username: ");

        String user = scanner.nextLine().trim();
        System.out.print("Password: ");

        String pass = scanner.nextLine().trim();

        for (User u : users) {
            if (u.login(user, pass)) {
                currentUser = u;
                System.out.println("Login berhasil. Selamat datang, " + u.getUsername() + "!");
                return;
            }
        }
        currentUser = null;
        System.out.println("Username atau password salah.");
    }

    private static void addMovieFlow(Admin admin) {
        try {
            System.out.print("Judul Film: ");
            String title = scanner.nextLine().trim();

            System.out.print("Genre Film: ");
            String genre = scanner.nextLine().trim();

            System.out.print("Durasi (menit): ");
            int duration = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Deskripsi: ");
            String description = scanner.nextLine().trim();

            System.out.print("Sutradara: ");
            String director = scanner.nextLine().trim();

            Movie movie = new Movie(title, genre, duration, description, director);

            admin.addMovie(movie, movies);
        } catch (NumberFormatException e) {
            System.out.println("Input durasi tidak valid.");
        }
    }

    private static void addScheduleFlow(Admin admin) {
        if (movies.isEmpty()) {
            System.out.println("Belum ada film. Tambah film terlebih dahulu.");
            return;
        }
        System.out.println("Pilih film untuk tambah jadwal:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, movies.get(i).getTitle());
        }
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            Movie selMovie = movies.get(idx);

            String dt;
            while (true) {
                System.out.print("Tanggal & Waktu (yyyy-MM-dd HH:mm): ");
                dt = scanner.nextLine().trim();

                if (!Schedule.isValidDateTime(dt)) {
                    System.out.println("Format tanggal tidak valid. Harap gunakan format yyyy-MM-dd HH:mm.");
                } else {
                    break;
                }
            }

            System.out.print("Jumlah Kursi Tersedia: ");
            int seats = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Harga per Tiket: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Nama Studio: ");
            String studio = scanner.nextLine().trim();

            Schedule schedule = new Schedule(selMovie, dt, seats, price, studio);

            selMovie.addSchedule(schedule);
            admin.updateSchedule(selMovie, schedule);
            System.out.println("Jadwal berhasil ditambahkan.");
        } catch (Exception e) {
            System.out.println("Input atau pilihan tidak valid.");
        }
    }

    private static void TicketReservation(Customer cust) {
        if (movies.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia saat ini.");
            return;
        }

        System.out.println("\n===== PESAN TIKET =====");
        cust.viewMovies(movies);

        try {
            System.out.print("Pilih nomor film: ");
            int filmIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (filmIndex < 0 || filmIndex >= movies.size()) {
                System.out.println("Pilihan film tidak valid.");
                return;
            }

            Movie selectedMovie = movies.get(filmIndex);
            List<Schedule> schedules = selectedMovie.getSchedules();

            if (schedules.isEmpty()) {
                System.out.println("Film ini belum memiliki jadwal tayang.");
                return;
            }

            System.out.println("\n--- PILIH JADWAL ---");
            for (int i = 0; i < schedules.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, schedules.get(i));
            }

            System.out.print("Pilih nomor jadwal: ");
            int scheduleIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (scheduleIndex < 0 || scheduleIndex >= schedules.size()) {
                System.out.println("Pilihan jadwal tidak valid.");
                return;
            }

            Schedule selectedSchedule = schedules.get(scheduleIndex);

            if (selectedSchedule.isPast()) {
                System.out.println("Jadwal ini sudah lewat. Tidak dapat melakukan reservasi.");
                return;
            }

            int maxSeat = selectedSchedule.getTotalSeats();
            System.out.print("Masukkan nomor kursi (1 - " + maxSeat + "): ");
            int seatNumber = Integer.parseInt(scanner.nextLine());

            if (seatNumber < 1 || seatNumber > maxSeat) {
                System.out.println("Nomor kursi tidak valid.");
                return;
            }

            System.out.println("\n--- KONFIRMASI PESANAN ---");
            System.out.println("Film   : " + selectedMovie.getTitle());
            System.out.println("Jadwal : " + selectedSchedule.getDateTime());
            System.out.println("Studio : " + selectedSchedule.getStudioName());
            System.out.println("Kursi  : " + seatNumber);
            System.out.println("Harga  : Rp " + String.format("%,.0f", selectedSchedule.getPrice()));
            System.out.print("Lanjutkan? (y/n): ");
            String confirm = scanner.nextLine().trim();

            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Reservasi dibatalkan.");
                return;
            }

            Reservation reservation = cust.bookTicket(selectedMovie, selectedSchedule, seatNumber);
            if (reservation != null) {
                reservations.add(reservation);
                System.out.println("Reservasi berhasil!");
                reservation.printInfo();
            }
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Harap masukkan angka.");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat reservasi: " + e.getMessage());
        }
    }

}
