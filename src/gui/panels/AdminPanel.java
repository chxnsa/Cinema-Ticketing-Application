package gui.panels;

import gui.themes.ModernColors;
import gui.components.RoundedButton;
import gui.frames.MainFrame;
import movie.Movie;
import reservation.Reservation;
import user.Admin;

import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminPanel extends JPanel {
    private MainFrame parentFrame;
    private Admin admin;
    private List<Movie> movies;
    private List<Reservation> reservations;

    private JTabbedPane tabbedPane;
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JLabel welcomeLabel;
    private JButton logoutButton;

    private AddMoviePanel addMoviePanel;
    private AddSchedulePanel addSchedulePanel;
    private ReservationListPanel reservationListPanel;
    private JPanel movieManagementPanel;
    private JPanel dashboardPanel;

    public AdminPanel(MainFrame parentFrame, Admin admin, List<Movie> movies, List<Reservation> reservations) {
        this.parentFrame = parentFrame;
        this.admin = admin;
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

        JPanel leftHeaderPanel = new JPanel(new GridLayout(2, 1));
        leftHeaderPanel.setOpaque(false);

        titleLabel = new JLabel("SineTix ADMIN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        welcomeLabel = new JLabel("Welcome, " + admin.getUsername());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(ModernColors.TEXT_SECONDARY);

        leftHeaderPanel.add(titleLabel);
        leftHeaderPanel.add(welcomeLabel);

        JPanel rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightHeaderPanel.setOpaque(false);

        logoutButton = new RoundedButton("Logout",
                ModernColors.BACKGROUND_LIGHT,
                ModernColors.BACKGROUND_MEDIUM,
                ModernColors.TEXT_PRIMARY);
        logoutButton.setPreferredSize(new Dimension(100, 40));

        rightHeaderPanel.add(logoutButton);

        headerPanel.add(leftHeaderPanel, BorderLayout.WEST);
        headerPanel.add(rightHeaderPanel, BorderLayout.EAST);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(ModernColors.BACKGROUND_DARK);
        tabbedPane.setForeground(ModernColors.TEXT_PRIMARY);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        dashboardPanel = createDashboardPanel();

        movieManagementPanel = new JPanel(new BorderLayout());
        movieManagementPanel.setBackground(ModernColors.BACKGROUND_DARK);

        addMoviePanel = new AddMoviePanel();
        addSchedulePanel = new AddSchedulePanel(movies);
        reservationListPanel = new ReservationListPanel(reservations);

        tabbedPane.addTab("Dashboard", createTabIcon("dashboard"), dashboardPanel);
        tabbedPane.addTab("Add Movie", createTabIcon("movie"), addMoviePanel);
        tabbedPane.addTab("Add Schedule", createTabIcon("schedule"), addSchedulePanel);
        tabbedPane.addTab("Reservations", createTabIcon("ticket"), reservationListPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void setupListeners() {

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.logout();
            }
        });

        addMoviePanel.setAddMovieListener(new AddMoviePanel.AddMovieListener() {
            @Override
            public void onMovieAdded(Movie movie) {
                admin.addMovie(movie, movies);
                JOptionPane.showMessageDialog(parentFrame,
                        "Movie \"" + movie.getTitle() + "\" added successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                addSchedulePanel.refreshMovies(movies);
            }

            @Override
            public void onCancel() {
                tabbedPane.setSelectedIndex(0);
            }
        });

        addSchedulePanel.setAddScheduleListener(new AddSchedulePanel.AddScheduleListener() {
            @Override
            public void onScheduleAdded(Movie movie, String dateTime, int totalSeats, double price, String studioName) {

                movie.addSchedule(new movie.Schedule(movie, dateTime, totalSeats, price, studioName));

                JOptionPane.showMessageDialog(parentFrame,
                        "Schedule for \"" + movie.getTitle() + "\" added successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                refreshDashboard();
            }
        });
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(ModernColors.BACKGROUND_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel dashboardTitle = new JLabel("Admin Dashboard");
        dashboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        dashboardTitle.setForeground(ModernColors.TEXT_PRIMARY);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatPanel("Total Movies", String.valueOf(movies.size()), ModernColors.ACCENT_PRIMARY));

        int scheduleCount = 0;
        for (Movie movie : movies) {
            scheduleCount += movie.getSchedules().size();
        }
        statsPanel
                .add(createStatPanel("Total Schedules", String.valueOf(scheduleCount), ModernColors.ACCENT_SECONDARY));

        statsPanel
                .add(createStatPanel("Total Reservations", String.valueOf(reservations.size()), ModernColors.SUCCESS));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionsPanel.setOpaque(false);
        actionsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM),
                "Quick Actions",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14),
                ModernColors.TEXT_PRIMARY));

        RoundedButton addMovieButton = new RoundedButton("Add New Movie");
        addMovieButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        RoundedButton addScheduleButton = new RoundedButton("Add New Schedule");
        addScheduleButton.addActionListener(e -> tabbedPane.setSelectedIndex(2));

        RoundedButton viewReservationsButton = new RoundedButton("View Reservations");
        viewReservationsButton.addActionListener(e -> tabbedPane.setSelectedIndex(3));

        actionsPanel.add(addMovieButton);
        actionsPanel.add(addScheduleButton);
        actionsPanel.add(viewReservationsButton);

        JPanel recentMoviesPanel = new JPanel(new BorderLayout(0, 10));
        recentMoviesPanel.setOpaque(false);
        recentMoviesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM),
                "Recent Movies",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14),
                ModernColors.TEXT_PRIMARY));

        JPanel movieListPanel = new JPanel();
        movieListPanel.setLayout(new BoxLayout(movieListPanel, BoxLayout.Y_AXIS));
        movieListPanel.setOpaque(false);

        if (movies.isEmpty()) {
            JLabel noMoviesLabel = new JLabel("No movies available. Add some movies!");
            noMoviesLabel.setForeground(ModernColors.TEXT_SECONDARY);
            noMoviesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            movieListPanel.add(noMoviesLabel);
        } else {
            int count = Math.min(movies.size(), 5);
            for (int i = 0; i < count; i++) {
                Movie movie = movies.get(movies.size() - 1 - i);
                JPanel movieItem = createRecentMovieItem(movie);
                movieItem.setAlignmentX(Component.LEFT_ALIGNMENT);
                movieListPanel.add(movieItem);
                movieListPanel.add(Box.createVerticalStrut(5));
            }
        }

        JScrollPane scrollPane = new JScrollPane(movieListPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        recentMoviesPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout(0, 20));
        topPanel.setOpaque(false);
        topPanel.add(dashboardTitle, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(actionsPanel, BorderLayout.CENTER);
        panel.add(recentMoviesPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStatPanel(String title, String value, Color accentColor) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(ModernColors.BACKGROUND_MEDIUM);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(ModernColors.TEXT_SECONDARY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(accentColor);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRecentMovieItem(Movie movie) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        JLabel detailsLabel = new JLabel(movie.getGenre() + " | " + movie.getDuration() + " min");
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailsLabel.setForeground(ModernColors.TEXT_SECONDARY);

        JLabel schedulesLabel = new JLabel(movie.getSchedules().size() + " schedule(s)");
        schedulesLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        schedulesLabel.setForeground(ModernColors.TEXT_TERTIARY);

        JPanel textPanel = new JPanel(new GridLayout(3, 1));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(detailsLabel);
        textPanel.add(schedulesLabel);

        RoundedButton deleteButton = new RoundedButton("Delete",
                ModernColors.ERROR,
                new Color(ModernColors.ERROR.getRed(),
                        ModernColors.ERROR.getGreen(),
                        ModernColors.ERROR.getBlue(),
                        200),
                ModernColors.TEXT_PRIMARY);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Are you sure you want to delete the movie \"" + movie.getTitle() + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    admin.deleteMovie(movie.getTitle(), movies);
                    refreshDashboard();

                    addSchedulePanel.refreshMovies(movies);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteButton);

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private ImageIcon createTabIcon(String iconType) {
        int size = 16;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        Color color;
        switch (iconType.toLowerCase()) {
            case "dashboard":
                color = ModernColors.ACCENT_PRIMARY;
                break;
            case "movie":
                color = ModernColors.ACCENT_SECONDARY;
                break;
            case "schedule":
                color = ModernColors.SUCCESS;
                break;
            case "ticket":
                color = ModernColors.WARNING;
                break;
            default:
                color = ModernColors.TEXT_PRIMARY;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fillRoundRect(0, 0, size, size, 4, 4);
        g2d.dispose();

        return new ImageIcon(image);
    }

    private void refreshDashboard() {

        tabbedPane.removeTabAt(0);

        dashboardPanel = createDashboardPanel();
        tabbedPane.insertTab("Dashboard", createTabIcon("dashboard"), dashboardPanel, null, 0);

        reservationListPanel.refreshReservations(reservations);
    }

    public void refreshData() {
        refreshDashboard();
        addSchedulePanel.refreshMovies(movies);
        reservationListPanel.refreshReservations(reservations);
    }
}