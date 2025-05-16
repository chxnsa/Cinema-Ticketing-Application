import gui.frames.MainFrame;
import gui.themes.DarkTheme;
import user.Admin;
import user.User;
import movie.Movie;
import reservation.Reservation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MainGUI {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {

                DarkTheme.apply();

                List<User> users = new ArrayList<>();
                List<Movie> movies = new ArrayList<>();
                List<Reservation> reservations = new ArrayList<>();

                users.add(new Admin("admin", "root123"));

                MainFrame mainFrame = new MainFrame(users, movies, reservations);
                mainFrame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

}
