import movie.Movie;
import movie.Schedule;
import reservation.Reservation;
import user.Admin;
import user.Costumer;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static List<User> users = new ArrayList<>();
    private static List<Movie> movies = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {

        //TODO: data dari file (users, movies, dan reservations)
        
        users.add(new Admin("admin","admin"));

        //TODO: membuat looping utama untuk menu register, login, dan keluar
    }

    //TODO: menambahkan method kerangkanya
}