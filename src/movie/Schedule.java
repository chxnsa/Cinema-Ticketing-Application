import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private Movie movie;
    private LocalDateTime dateTime;
    private double price;
    private int availableSeats;

    public Schedule(Movie movie, LocalDateTime dateTime, double price, int availableSeats) {
        this.movie = movie;
        this.dateTime = dateTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    // Getters
    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return movie.getTitle() + " - " +
               dateTime.format(formatter) + " | Rp" + price + " | Seats: " + availableSeats;
    }
}

