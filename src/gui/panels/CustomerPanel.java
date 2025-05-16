package gui.panels;

import gui.frames.MainFrame;
import gui.themes.ModernColors;
import gui.components.MovieCard;
import movie.Movie;
import movie.Schedule;
import user.Customer;
import reservation.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {
    private MainFrame parentFrame;
    private Customer customer;
    private List<Movie> movies;
    private List<Reservation> reservations;

    private CardLayout contentCardLayout;
    private JPanel contentPanel;

    private MovieListPanel movieListPanel;
    private MovieDetailPanel movieDetailPanel;
    private SeatSelectionPanel seatSelectionPanel;
    private ReservationHistoryPanel reservationHistoryPanel;

    private JPanel headerPanel;
    private JLabel titleLabel;
    private JPanel navPanel;
    private JButton moviesButton;
    private JButton reservationsButton;
    private JButton logoutButton;

    private Movie selectedMovie;
    private Schedule selectedSchedule;

    public CustomerPanel(MainFrame parentFrame, Customer customer, List<Movie> movies, List<Reservation> reservations) {
        this.parentFrame = parentFrame;
        this.customer = customer;
        this.movies = movies;
        this.reservations = reservations;

        setupPanel();
        initComponents();
        setupListeners();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(ModernColors.BACKGROUND_DARK);
    }

    private void initComponents() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ModernColors.BACKGROUND_MEDIUM);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        titleLabel = new JLabel("SineTix");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setOpaque(false);

        moviesButton = createNavButton("Movies");
        reservationsButton = createNavButton("My Reservations");
        logoutButton = createNavButton("Logout");

        navPanel.add(moviesButton);
        navPanel.add(reservationsButton);
        navPanel.add(logoutButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);

        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        contentPanel.setOpaque(false);

        movieListPanel = new MovieListPanel(movies);
        movieDetailPanel = new MovieDetailPanel(movies.isEmpty() ? null : movies.get(0));
        reservationHistoryPanel = new ReservationHistoryPanel(customer, reservations);

        contentPanel.add(movieListPanel, "movieList");
        contentPanel.add(movieDetailPanel, "movieDetail");
        contentPanel.add(reservationHistoryPanel, "reservations");

        contentCardLayout.show(contentPanel, "movieList");

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(ModernColors.TEXT_PRIMARY);
        button.setBackground(ModernColors.BACKGROUND_MEDIUM);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(ModernColors.ACCENT_PRIMARY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(ModernColors.TEXT_PRIMARY);
            }
        });

        return button;
    }

    private void setupListeners() {

        moviesButton.addActionListener(e -> contentCardLayout.show(contentPanel, "movieList"));

        reservationsButton.addActionListener(e -> {
            reservationHistoryPanel.refreshReservations();
            contentCardLayout.show(contentPanel, "reservations");
        });

        logoutButton.addActionListener(e -> parentFrame.logout());

        movieListPanel.setMovieCardListener(new MovieCard.MovieCardListener() {
            @Override
            public void onMovieCardClicked(Movie movie) {
                selectedMovie = movie;
                movieDetailPanel.setMovie(movie);
                contentCardLayout.show(contentPanel, "movieDetail");
            }
        });

        movieDetailPanel.setMovieDetailListener(new MovieDetailPanel.MovieDetailListener() {
            @Override
            public void onScheduleSelected(Movie movie, Schedule schedule) {
                selectedMovie = movie;
                selectedSchedule = schedule;
                openSeatSelection(schedule);
            }

            @Override
            public void onBackToMovieList() {
                contentCardLayout.show(contentPanel, "movieList");
            }
        });
    }

    private void openSeatSelection(Schedule schedule) {

        if (seatSelectionPanel != null) {
            contentPanel.remove(seatSelectionPanel);
        }

        seatSelectionPanel = new SeatSelectionPanel(schedule);
        seatSelectionPanel.setSeatSelectionListener(new SeatSelectionPanel.SeatSelectionListener() {
            @Override
            public void onSeatSelectionConfirmed(List<Integer> seatNumbers) {
                bookTickets(selectedMovie, selectedSchedule, seatNumbers);
            }

            @Override
            public void onSeatSelectionCancelled() {
                contentCardLayout.show(contentPanel, "movieDetail");
            }
        });

        contentPanel.add(seatSelectionPanel, "seatSelection");
        contentCardLayout.show(contentPanel, "seatSelection");
    }

    private void bookTickets(Movie movie, Schedule schedule, List<Integer> seatNumbers) {
        customer.bookTickets(movie, schedule, seatNumbers, reservations);

        JOptionPane.showMessageDialog(this,
                "Your tickets have been booked successfully!",
                "Booking Confirmed",
                JOptionPane.INFORMATION_MESSAGE);

        reservationHistoryPanel.refreshReservations();
        contentCardLayout.show(contentPanel, "reservations");
    }

    public void refreshData() {
        movieListPanel.setMovies(movies);
        reservationHistoryPanel.refreshReservations();
    }
}