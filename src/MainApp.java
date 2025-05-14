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
        users.add(new Admin("admin", "root123"));

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
            System.out.println("\n=== MENU PELANGGAN ===");
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
                    ticketReservation(cust);
                    break;
                case "3":
                    cust.viewReservations(reservations);
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
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Tambah Film");
            System.out.println("2. Tambah Jadwal Film");
            System.out.println("3. Lihat Film & Jadwal");
            System.out.println("4. Lihat Semua Reservasi");
            System.out.println("5. Hapus Film");
            System.out.println("6. Hapus Jadwal Film");
            System.out.println("7. Logout");
            System.out.print("Pilih (1-7): ");

            String choice = scanner.nextLine().trim();

            if (choice.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
                continue;
            }

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
                    deleteMovieFlow(admin);
                    break;
                case "6":
                    deleteScheduleFlow(admin);
                    break;
                case "7":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1-7.");
            }
        }
    }

    private static void registerUser() {
        System.out.println("\n=== REGISTRASI PELANGGAN BARU ===");

        String user, pass;
        while (true) {
            System.out.print("Username: ");
            user = scanner.nextLine().trim();
            if (user.isEmpty()) {
                System.out.println("Username tidak boleh kosong.");
                continue;
            }
            if (user.length() < 5) {
                System.out.println("Username minimal 5 karakter.");
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

        Customer cust = new Customer(user, pass);
        users.add(cust);
        System.out.println("Registrasi berhasil. Silakan login.");
    }

    private static void loginUser() {
        System.out.println("\n=== LOGIN PELANGGAN/ADMIN ===");

        String user, pass;
        while (true) {
            System.out.print("Username: ");
            user = scanner.nextLine().trim();
            if (user.isEmpty()) {
                System.out.println("Username tidak boleh kosong.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Password: ");
            pass = scanner.nextLine().trim();
            if (pass.isEmpty()) {
                System.out.println("Password tidak boleh kosong.");
                continue;
            }
            break;
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

            boolean exists = false;
            for (Movie m : movies) {
                if (m.getTitle().equalsIgnoreCase(title)) {
                    System.out.println("Film dengan judul yang sama sudah ada!");
                    exists = true;
                    return;
                }
            }
            if (exists) {
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Genre Film: ");
            genre = scanner.nextLine().trim();
            if (genre.isEmpty()) {
                System.out.println("Genre film tidak boleh kosong.");
                continue;
            }
            break;
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
                if (duration <= 0) {
                    System.out.println("Durasi harus lebih dari 0 menit.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Durasi harus berupa angka.");
            }
        }

        while (true) {
            System.out.print("Deskripsi: ");
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Deskripsi tidak boleh kosong.");
                continue;
            }
            break;
        }

        while (true) {
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
            if (!Schedule.isValidDateTime(dt)) {
                System.out.println("Format tanggal dan waktu tidak valid! Gunakan format: yyyy-MM-dd HH:mm");
                continue;
            }

            Schedule tempSchedule = new Schedule(selMovie, dt, 1, 1.0, "temp");
            if (tempSchedule.isPast()) {
                System.out.println("Tidak dapat menambahkan jadwal yang sudah lewat waktu!");
                continue;
            }

            break;
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
                if (seats <= 0) {
                    System.out.println("Jumlah kursi harus lebih dari 0.");
                    continue;
                }
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
                if (price <= 0) {
                    System.out.println("Harga tiket harus lebih dari 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Angka tidak valid.");
            }
        }

        while (true) {
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

    }

    private static void deleteMovieFlow(Admin admin) {
        if (movies.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia.");
            return;
        }

        System.out.println("Pilih film untuk dihapus:");
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

        String movieTitle = movies.get(idx).getTitle();

        while (true) {
            System.out.print("Yakin ingin menghapus film ini? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                admin.deleteMovie(movieTitle, movies);
                return;
            } else if (confirm.equalsIgnoreCase("n")) {
                System.out.println("Penghapusan film dibatalkan.");
                return;
            } else {
                System.out.println("Input hanya y atau n.");
            }
        }
    }

    private static void deleteScheduleFlow(Admin admin) {
        if (movies.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia.");
            return;
        }

        System.out.println("Pilih film untuk menghapus jadwal:");
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

        Movie selectedMovie = movies.get(idx);
        List<Schedule> schedules = selectedMovie.getSchedules();

        if (schedules.isEmpty()) {
            System.out.println("Film ini tidak memiliki jadwal.");
            return;
        }

        System.out.println("Pilih jadwal untuk dihapus:");
        for (int i = 0; i < schedules.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, schedules.get(i));
        }

        int scheduleIdx;
        while (true) {
            System.out.print("Pilih nomor jadwal: ");
            String si = scanner.nextLine().trim();
            if (si.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
                continue;
            }
            try {
                scheduleIdx = Integer.parseInt(si) - 1;
                if (scheduleIdx < 0 || scheduleIdx >= schedules.size()) {
                    System.out.println("Pilihan jadwal tidak valid.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka valid.");
            }
        }

        Schedule selectedSchedule = schedules.get(scheduleIdx);
        String scheduleDateTime = selectedSchedule.getDateTime();

        while (true) {
            System.out.print("Yakin ingin menghapus jadwal ini? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                admin.deleteSchedule(selectedMovie.getTitle(), scheduleDateTime, movies);
                return;
            } else if (confirm.equalsIgnoreCase("n")) {
                System.out.println("Penghapusan jadwal dibatalkan.");
                return;
            } else {
                System.out.println("Input hanya y atau n.");
            }
        }

    }

    private static void ticketReservation(Customer cust) {
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
