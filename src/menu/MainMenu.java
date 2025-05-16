package menu;

import java.util.Scanner;
import java.util.List;
import user.User;
import user.Admin;
import user.Customer;
import movie.Movie;
import reservation.Reservation;

public class MainMenu implements Menu {
    private Scanner scanner;
    private List<User> users;
    private User currentUser;
    private List<Movie> movies;
    private List<Reservation> reservations;

    public MainMenu(Scanner scanner, List<User> users, List<Movie> movies, List<Reservation> reservations) {
        this.scanner = scanner;
        this.users = users;
        this.movies = movies;
        this.reservations = reservations;
    }

    @Override
    public void display() {
        System.out.println("\n=== SELAMAT DATANG DI SISTEM RESERVASI BIOSKOP ===");
        System.out.println("1. Registrasi");
        System.out.println("2. Login");
        System.out.println("3. Keluar");
        System.out.print("Pilih (1-3): ");
    }

    @Override
    public void processChoice(String choice) {
        if (choice.isEmpty()) {
            System.out.println("Input tidak boleh kosong.");
            return;
        }

        switch (choice) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                if (currentUser != null) {
                    if (currentUser instanceof Admin) {
                        AdminMenu adminMenu = new AdminMenu(scanner, users, movies, reservations, currentUser);
                        adminMenu.run();
                    } else {
                        CustomerMenu custMenu = new CustomerMenu(scanner, movies, reservations, currentUser);
                        custMenu.run();
                    }
                    currentUser = null;
                }
                break;
            case "3":
                System.out.println("Terima kasih telah menggunakan sistem. Sampai jumpa!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid. Silakan pilih 1-3.");
        }
    }

    @Override
    public void run() {
        while (true) {
            display();
            String choice = scanner.nextLine().trim();
            processChoice(choice);
        }
    }

    private void registerUser() {
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

    private void loginUser() {
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
        System.out.println("Username atau password salah.");
    }
}