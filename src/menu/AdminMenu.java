package menu;

import java.util.Scanner;
import java.util.List;
import user.User;
import user.Admin;
import movie.Movie;
import movie.Schedule;
import reservation.Reservation;

public class AdminMenu implements Menu {
    private Scanner scanner;
    private User currentUser;
    private List<Movie> movies;
    private List<Reservation> reservations;

    public AdminMenu(Scanner scanner, List<User> users, List<Movie> movies, List<Reservation> reservations,
            User currentUser) {
        this.scanner = scanner;
        this.currentUser = currentUser;
        this.movies = movies;
        this.reservations = reservations;
    }

    @Override
    public void display() {
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Tambah Film");
        System.out.println("2. Tambah Jadwal Film");
        System.out.println("3. Lihat Film & Jadwal");
        System.out.println("4. Lihat Semua Reservasi");
        System.out.println("5. Hapus Film");
        System.out.println("6. Hapus Jadwal Film");
        System.out.println("7. Logout");
        System.out.print("Pilih (1-7): ");
    }

    @Override
    public void processChoice(String choice) {
        Admin admin = (Admin) currentUser;

        if (choice.isEmpty()) {
            System.out.println("Input tidak boleh kosong.");
            return;
        }

        switch (choice) {
            case "1":
                addMovieFlow(admin);
                break;
            case "2":
                addScheduleFlow();
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
                System.out.println("Logout berhasil.");
                return;
            default:
                System.out.println("Pilihan tidak valid. Silakan pilih 1-7.");
        }
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            display();
            String choice = scanner.nextLine().trim();
            if (choice.equals("7")) {
                running = false;
            } else {
                processChoice(choice);
            }
        }
    }

    private void addMovieFlow(Admin admin) {
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

    private void addScheduleFlow() {
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

    private void deleteMovieFlow(Admin admin) {
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

    private void deleteScheduleFlow(Admin admin) {
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
}