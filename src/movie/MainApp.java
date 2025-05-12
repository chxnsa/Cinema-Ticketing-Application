import java.util.Scanner;
import user.User;
import user.Customer;
import user.Admin;
import movie.Movie;
import movie.Schedule;
import reservation.Reservation;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Main application class for the Cinema Ticketing System
 */
public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final List<Movie> movies = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        // Seed default admin
        users.add(new Admin("admin", "admin"));

        // Load data from files
        loadData();

        // Show welcome message and main menu
        showWelcomeScreen();

        // Save data before exit
        saveData();
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
                    saveData();
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
                    cust.viewMovies();
                    break;
                case "2":
                    cust.bookTicket();
                    saveData();
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
            System.out.println("3. Lihat Semua Reservasi");
            System.out.println("4. Logout");
            System.out.print("Pilih (1-4): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addMovieFlow(admin);
                    saveData();
                    break;
                case "2":
                    addScheduleFlow(admin);
                    saveData();
                    break;
                case "3":
                    viewAllReservations();
                    break;
                case "4":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1-4.");
            }
        }
    }

    private static void registerUser() {
        System.out.println("\n--- REGISTRASI PELANGGAN BARU ---");
        System.out.print("Username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            System.out.println("Username dan password tidak boleh kosong.");
            return;
        }

        for (User u : users) {
            if (u.getUsername().equals(user)) {
                System.out.println("Username sudah terdaftar!");
                return;
            }
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
            Movie movie = new Movie(title, genre, duration);
            movies.add(movie);
            admin.addMovie(movie);
            System.out.println("Film berhasil ditambahkan.");
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
            System.out.print("Tanggal & Waktu (yyyy-MM-dd HH:mm): ");
            String dt = scanner.nextLine().trim();
            System.out.print("Jumlah Kursi Tersedia: ");
            int seats = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Harga per Tiket: ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            Schedule schedule = new Schedule(selMovie, dt, seats, price);
            selMovie.addSchedule(schedule);
            admin.updateSchedule(selMovie, schedule);
            System.out.println("Jadwal berhasil ditambahkan.");
        } catch (Exception e) {
            System.out.println("Input atau pilihan tidak valid.");
        }
    }

    private static void viewAllReservations() {
        System.out.println("\n--- DAFTAR SEMUA RESERVASI ---");
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
        } else {
            for (Reservation r : reservations) {
                r.printInfo();
            }
        }
    }

    /**
     * Load data from files
     */
    private static void loadData() {
        // TODO: Implementasi muat data oleh Anggota 5
    }

    /**
     * Save data to files
     */
    private static void saveData() {
        // TODO: Implementasi simpan data oleh Anggota 5
    }
}
