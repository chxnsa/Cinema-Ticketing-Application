package user;

import java.util.List;
import movie.Movie;
import movie.Schedule;
import reservation.Reservation;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public boolean addMovie(Movie movie, List<Movie> movieList) {

        for (Movie existingMovie : movieList) {
            if (existingMovie.getTitle().equalsIgnoreCase(movie.getTitle())) {
                System.out.println("Film dengan judul yang sama sudah ada!");
                return false;
            }
        }

        movieList.add(movie);
        System.out.println("Film " + movie.getTitle() + " berhasil ditambahkan");
        return true;
    }

    public boolean updateSchedule(Movie movie, Schedule schedule) {

        if (!Schedule.isValidDateTime(schedule.getDateTime())) {
            System.out.println("Format tanggal dan waktu tidak valid!");
            return false;
        }

        movie.addSchedule(schedule);
        System.out.println("Jadwal untuk film " + movie.getTitle() + " berhasil diupdate");
        return true;

    }

    public void viewAllReservations(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }

        System.out.println("\n===== DAFTAR SEMUA RESERVASI =====");
        for (Reservation reservation : reservations) {
            reservation.printInfo();
            System.out.println();
        }
    }

    public boolean deleteMovie(String movieTitle, List<Movie> movieList) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().equalsIgnoreCase(movieTitle)) {
                movieList.remove(i);
                System.out.println("Film " + movieTitle + " berhasil dihapus");
                return true;
            }
        }
        System.out.println("Film " + movieTitle + " tidak ditemukan");
        return false;
    }

    @Override
    public void printInfo() {
        System.out.println("=== INFORMASI USER ===");
        System.out.println("Username: " + username);
        System.out.println("Role: Admin");
    }

}