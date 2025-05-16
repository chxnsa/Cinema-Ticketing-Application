import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import user.User;
import user.Admin;
import movie.Movie;
import reservation.Reservation;
import menu.MainMenu;

public class MainCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<User> users = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();

        users.add(new Admin("admin", "root123"));

        MainMenu mainMenu = new MainMenu(scanner, users, movies, reservations);
        mainMenu.run();

        scanner.close();
    }
}