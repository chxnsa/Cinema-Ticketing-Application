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


public class MainApp {
    private static Scanner scanner;
    private static List<User> users;
    private static List<Movie> movies;
    private static List<Reservation> reservations;
    private static User currentUser;
    public static void main(String[] args) {
        
        scanner = new Scanner(System.in);
        users = new ArrayList<>();
        movies = new ArrayList<>();
        reservations = new ArrayList<>();
        
        loadData();
        
        showWelcomeScreen();
        
        scanner.close();
    }
    
    private static void showWelcomeScreen() {
    }
    
    private static void loadData() {
    }
    
    private static void saveData() {
    }
    public static List<User> getUsers() {
        return users;
    }
    
    public static List<Movie> getMovies() {
        return movies;
    }
    
    public static List<Reservation> getReservations() {
        return reservations;
    }
    
    private static void showMainMenu() {
    }
    
    private static void showCustomerMenu() {
    }
    
    private static void showAdminMenu() {
    }
    
    private static void registerUser() {
    }
    
    private static void loginUser() {
    }
}