package movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
    private Movie movie;
    private String dateTime;
    private int availableSeats;
    private int bookedSeats;
    private double price;
    private String studioName;
    private boolean[] seats;

    public Schedule(Movie movie, String dateTime, int totalSeats, double price, String studioName) {
        this.movie = movie;
        this.dateTime = dateTime;
        this.availableSeats = totalSeats;
        this.bookedSeats = 0;
        this.price = price;
        this.studioName = studioName;
        this.seats = new boolean[totalSeats];
    }

    public boolean bookSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            System.out.println("Nomor kursi tidak valid.");
            return false;
        }

        if (seats[seatNumber - 1]) {
            System.out.println("Maaf, kursi " + seatNumber + " sudah terpesan.");
            return false;
        }

        seats[seatNumber - 1] = true;
        availableSeats--;
        bookedSeats++;
        return true;
    }

    public boolean isSeatAvailable(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            System.out.println("Nomor kursi tidak valid.");
            return false;
        }

        return !seats[seatNumber - 1];

    }

    public boolean isAvailable() {
        return availableSeats > 0;
    }

    public static boolean isValidDateTime(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setLenient(false);

        try {
            format.parse(dateTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isPast() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date scheduleDate = format.parse(dateTime);
            Date now = new Date();
            return scheduleDate.before(now);
        } catch (ParseException e) {
            return false;
        }
    }

    public String getTitle() {
        return movie.getTitle();
    }

    public int getTotalSeats() {
        return availableSeats + bookedSeats;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public double getPrice() {
        return price;
    }

    public String getStudioName() {
        return studioName;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] | Tanggal: %s | Studio: %s | Harga: Rp %,d | Tersedia: %d/%d",
                movie.getTitle(),
                dateTime,
                studioName,
                (int) price,
                availableSeats,
                getTotalSeats());
    }

}
