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
        
        users.add(new Admin("admin", "admin"));
        
        loadData();
        
        showWelcomeScreen();
        
        scanner.close();
    }
    
    private static void showWelcomeScreen() {
        while (true) {
            System.out.println("\n=== WELCOME TO CINEMA TICKETING SYSTEM ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    if (currentUser != null) {
                        showMainMenu();
                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;
                case "3":
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
            }
        }
    }
    
    private static void loadData() {
    }
    
    private static void saveData() {
    }
    
    private static void showMainMenu() {
        if (currentUser instanceof Admin) {
            showAdminMenu();
        } else if (currentUser instanceof Customer) {
            showCustomerMenu();
        }
    }
    
    private static void showCustomerMenu() {
        Customer cust = (Customer) currentUser;
        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. View Movies & Schedules");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Reservations");
            System.out.println("4. Logout");
            System.out.print("Choose (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cust.viewMovies();
                    break;
                case "2":
                    cust.bookTicket();
                    break;
                case "3":
                    cust.viewReservations();
                    break;
                case "4":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }
    
    private static void showAdminMenu() {
        Admin admin = (Admin) currentUser;
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add Movie");
            System.out.println("2. Add Schedule to Movie");
            System.out.println("3. View All Reservations");
            System.out.println("4. Logout");
            System.out.print("Choose (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Duration (minutes): ");
                    int duration = Integer.parseInt(scanner.nextLine());
                    Movie movie = new Movie(title, genre, duration);
                    movies.add(movie);
                    admin.addMovie(movie);
                    System.out.println("Movie added successfully.");
                    break;
                case "2":
                    if (movies.isEmpty()) {
                        System.out.println("No movies available. Add a movie first.");
                        break;
                    }
                    System.out.println("Select a movie to add schedule:");
                    for (int i = 0; i < movies.size(); i++) {
                        System.out.printf("%d. %s\n", i+1, movies.get(i).getTitle());
                    }
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx < 0 || idx >= movies.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }
                    Movie selMovie = movies.get(idx);
                    System.out.print("Date & Time (yyyy-MM-dd HH:mm): ");
                    String dt = scanner.nextLine();
                    System.out.print("Available Seats: ");
                    int seats = Integer.parseInt(scanner.nextLine());
                    System.out.print("Price per Ticket: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    Schedule schedule = new Schedule(selMovie, dt, seats, price);
                    selMovie.addSchedule(schedule);
                    admin.updateSchedule(selMovie, schedule);
                    System.out.println("Schedule added successfully.");
                    break;
                case "3":
                    System.out.println("\n--- ALL RESERVATIONS ---");
                    if (reservations.isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        for (Reservation r : reservations) {
                            r.printInfo();
                        }
                    }
                    break;
                case "4":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }
    
    private static void registerUser() {
        System.out.println("\n--- REGISTER NEW CUSTOMER ---");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(user)) {
                System.out.println("Username already exists!");
                return;
            }
        }
        Customer cust = new Customer(user, pass);
        users.add(cust);
        System.out.println("Registration successful. You can now login.");
    }
    
    private static void loginUser() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        for (User u : users) {
            if (u.login(user, pass)) {
                currentUser = u;
                System.out.println("Login successful. Welcome, " + u.getUsername() + "!");
                return;
            }
        }
        System.out.println("Invalid credentials.");
    }
}
