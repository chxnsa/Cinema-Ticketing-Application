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

            if (choice.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
                continue;
            }


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

            if (choice.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
                continue;
            }


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

        String user, pass;
        while (true) {
            System.out.print("Username: ");
            user = scanner.nextLine().trim();
            if (user.isEmpty()) {
                System.out.println("Username tidak boleh kosong.");
                continue;
            }
            if (user.length() < 7) {
                System.out.println("Username minimal 7 karakter.");
                continue;
            }
            boolean exists = false;
            for (User u : users) {
                if (u.getUsername().equals(user)) {
                    System.out.println("Username sudah terdaftar!");
                    exists = true;
                    break;
                }
            }
            if (!exists)
                break;
        }
        while (true) {
            System.out.print("Password: ");
            pass = scanner.nextLine().trim();
            if (pass.isEmpty()) {
                System.out.println("Password tidak boleh kosong.");
                continue;
            }
            int letters = 0, digits = 0;
            for (char c : pass.toCharArray()) {
                if (Character.isLetter(c))
                    letters++;
                else if (Character.isDigit(c))
                    digits++;
            }
            if (letters < 5 || digits < 3) {
                System.out.println("Password minimal 5 huruf dan 3 angka!");
                continue;
            }
            break;
        }
        users.add(new Customer(user, pass));
        System.out.println("Registrasi berhasil. Silahkan login.");

    }

    private static void loginUser() {
        System.out.println("\n--- LOGIN PELANGGAN/ADMIN ---");

        String user, pass;
        while (true) {
            System.out.print("Username: ");
            user = scanner.nextLine().trim();
            if (!user.isEmpty())
                break;
            System.out.println("Username tidak boleh kosong.");
        }
        while (true) {
            System.out.print("Password: ");
            pass = scanner.nextLine().trim();
            if (!pass.isEmpty())
                break;
            System.out.println("Password tidak boleh kosong.");
        }

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

        String title, genre, description, director;
        int duration;
        while (true) {
            System.out.print("Judul Film: ");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Judul film tidak boleh kosong.");
                continue;
            }
            System.out.print("Genre Film: ");
            genre = scanner.nextLine().trim();
            if (genre.isEmpty()) {
                System.out.println("Genre film tidak boleh kosong.");
                continue;
            }
            while (true) {
                System.out.print("Durasi (menit): ");
                String di = scanner.nextLine().trim();
                if (di.isEmpty()) {
                    System.out.println("Durasi tidak boleh kosong.");
                    continue;
                }
                try {
                    duration = Integer.parseInt(di);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Durasi harus berupa angka.");
                }
            }
            System.out.print("Deskripsi: ");
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Deskripsi tidak boleh kosong.");
                continue;
            }
            System.out.print("Sutradara: ");
            director = scanner.nextLine().trim();
            if (director.isEmpty()) {
                System.out.println("Sutradara tidak boleh kosong.");
                continue;
            }
            break;
        }
        Movie movie = new Movie(title, genre, duration, description, director);
        admin.addMovie(movie, movies);
        System.out.println("Film berhasil ditambahkan.");

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

        int idx;
        while (true) {
            System.out.print("Pilih nomor film: ");
            String fi = scanner.nextLine().trim();
            if (fi.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
                continue;
            }
            try {
                idx = Integer.parseInt(fi) - 1;
                if (idx < 0 || idx >= movies.size()) {
                    System.out.println("Pilihan film tidak valid.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka valid.");
            }
        }
        Movie selMovie = movies.get(idx);

        String dt, studio;
        int seats;
        double price;
        while (true) {
            System.out.print("Tanggal & Waktu (yyyy-MM-dd HH:mm): ");
            dt = scanner.nextLine().trim();
            if (dt.isEmpty()) {
                System.out.println("Tanggal & Waktu tidak boleh kosong.");
                continue;
            }
            while (true) {
                System.out.print("Jumlah Kursi Tersedia: ");
                String si = scanner.nextLine().trim();
                if (si.isEmpty()) {
                    System.out.println("Jumlah kursi tidak boleh kosong.");
                    continue;
                }
                try {
                    seats = Integer.parseInt(si);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Angka tidak valid.");
                }
            }
            while (true) {
                System.out.print("Harga per Tiket: ");
                String pi = scanner.nextLine().trim();
                if (pi.isEmpty()) {
                    System.out.println("Harga tiket tidak boleh kosong.");
                    continue;
                }
                try {
                    price = Double.parseDouble(pi);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Angka tidak valid.");
                }
            }
            System.out.print("Nama Studio: ");
            studio = scanner.nextLine().trim();
            if (studio.isEmpty()) {
                System.out.println("Studio tidak boleh kosong.");
                continue;
            }
            break;
        }

        Schedule schedule = new Schedule(selMovie, dt, seats, price, studio);
        selMovie.addSchedule(schedule);
        admin.updateSchedule(selMovie, schedule);
        System.out.println("Jadwal berhasil ditambahkan.");

    }

    private static void TicketReservation(Customer cust) {
        if (movies.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia saat ini.");
            return;
        }

        System.out.println("\n===== PESAN TIKET =====");
        cust.viewMovies(movies);

        int filmIndex;
        Schedule selectedSchedule;
        int seatNumber;

        while (true) {
            try {
                System.out.print("Pilih nomor film: ");
                String ifi = scanner.nextLine().trim();
                if (ifi.isEmpty()) {
                    System.out.println("Input tidak boleh kosong.");
                    continue;
                }
                filmIndex = Integer.parseInt(ifi) - 1;
                if (filmIndex < 0 || filmIndex >= movies.size()) {
                    System.out.println("Pilihan film tidak valid.");
                    continue;
                }

                Movie selectedMovie = movies.get(filmIndex);
                List<Schedule> schedules = selectedMovie.getSchedules();
                if (schedules.isEmpty()) {
                    System.out.println("Film ini belum memiliki jadwal tayang.");
                    return;
                }

                int scheduleIndex;
                while (true) {
                    System.out.println("\n--- PILIH JADWAL ---");
                    for (int i = 0; i < schedules.size(); i++) {
                        System.out.printf("%d. %s\n", i + 1, schedules.get(i));
                    }
                    System.out.print("Pilih nomor jadwal: ");
                    String ijs = scanner.nextLine().trim();
                    if (ijs.isEmpty()) {
                        System.out.println("Input tidak boleh kosong.");
                        continue;
                    }
                    scheduleIndex = Integer.parseInt(ijs) - 1;
                    if (scheduleIndex < 0 || scheduleIndex >= schedules.size()) {
                        System.out.println("Pilihan jadwal tidak valid.");
                        continue;
                    }
                    selectedSchedule = schedules.get(scheduleIndex);
                    break;
                }

                while (true) {
                    System.out.print("Masukkan nomor kursi (1 - " + selectedSchedule.getTotalSeats() + "): ");
                    String si = scanner.nextLine().trim();
                    if (si.isEmpty()) {
                        System.out.println("Nomor kursi tidak boleh kosong.");
                        continue;
                    }
                    seatNumber = Integer.parseInt(si);
                    if (seatNumber < 1 || seatNumber > selectedSchedule.getTotalSeats()) {
                        System.out.println("Nomor kursi tidak valid.");
                        continue;
                    }
                    break;
                }

                System.out.println("\n--- KONFIRMASI PESANAN ---");
                System.out.println("Film   : " + movies.get(filmIndex).getTitle());
                System.out.println("Jadwal : " + selectedSchedule.getDateTime());
                System.out.println("Studio : " + selectedSchedule.getStudioName());
                System.out.println("Kursi  : " + seatNumber);
                System.out.println("Harga  : Rp " + String.format("%,.0f", selectedSchedule.getPrice()));

                while (true) {
                    System.out.print("Lanjutkan? (y/n): ");
                    String c = scanner.nextLine().trim();
                    if (c.equalsIgnoreCase("y")) {
                        Reservation r = cust.bookTicket(movies.get(filmIndex), selectedSchedule, seatNumber);
                        if (r != null) {
                            reservations.add(r);
                            System.out.println("Reservasi berhasil!");
                            r.printInfo();
                        }
                        return;
                    }
                    if (c.equalsIgnoreCase("n")) {
                        System.out.println("Reservasi dibatalkan.");
                        return;
                    }
                    System.out.println("Input hanya y atau n.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }
    }
}
