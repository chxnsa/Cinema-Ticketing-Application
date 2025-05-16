
package user;

import java.util.List;

import movie.Movie;

public abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public boolean login(String inputUser, String inputPass) {
        return username.equals(inputUser) && password.equals(inputPass);
    }

    public abstract void printInfo();

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && !username.contains(" ");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void viewMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            System.out.println("Belum ada film yang tersedia.");
            return;
        }

        System.out.println("\n===== DAFTAR FILM DAN JADWAL =====");
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            System.out.println((i + 1) + ". ");
            movie.displayDetails();

            System.out.println();
        }
    }

}


