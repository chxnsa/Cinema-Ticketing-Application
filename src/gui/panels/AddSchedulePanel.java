package gui.panels;

import gui.themes.ModernColors;
import gui.components.RoundedButton;
import gui.components.CustomTextField;
import movie.Movie;
import movie.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddSchedulePanel extends JPanel {
    private List<Movie> movies;
    private JComboBox<Movie> movieComboBox;
    private JLabel titleLabel;
    private CustomTextField dateTimeField;
    private CustomTextField seatsField;
    private CustomTextField priceField;
    private CustomTextField studioField;
    private RoundedButton addButton;
    private RoundedButton clearButton;
    private JLabel messageLabel;

    private AddScheduleListener listener;

    public interface AddScheduleListener {
        void onScheduleAdded(Movie movie, String dateTime, int totalSeats, double price, String studioName);
    }

    public AddSchedulePanel(List<Movie> movies) {
        this.movies = movies;
        setupPanel();
        initComponents();
        setupListeners();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(ModernColors.BACKGROUND_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBackground(ModernColors.BACKGROUND_DARK);

        titleLabel = new JLabel("Add New Schedule");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        JLabel movieLabel = new JLabel("Select Movie:");
        movieLabel.setForeground(ModernColors.TEXT_SECONDARY);
        movieLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        movieComboBox = new JComboBox<>();
        movieComboBox.setBackground(ModernColors.BACKGROUND_LIGHT);
        movieComboBox.setForeground(ModernColors.TEXT_PRIMARY);
        movieComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel dateTimeLabel = new JLabel("Date & Time (yyyy-MM-dd HH:mm):");
        dateTimeLabel.setForeground(ModernColors.TEXT_SECONDARY);
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        dateTimeField = new CustomTextField("yyyy-MM-dd HH:mm");

        JLabel seatsLabel = new JLabel("Number of Seats:");
        seatsLabel.setForeground(ModernColors.TEXT_SECONDARY);
        seatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        seatsField = new CustomTextField("e.g. 100");

        JLabel priceLabel = new JLabel("Ticket Price:");
        priceLabel.setForeground(ModernColors.TEXT_SECONDARY);
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        priceField = new CustomTextField("e.g. 50000");

        JLabel studioLabel = new JLabel("Studio Name:");
        studioLabel.setForeground(ModernColors.TEXT_SECONDARY);
        studioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        studioField = new CustomTextField("e.g. Studio A");

        addButton = new RoundedButton("Add Schedule");
        addButton.setPreferredSize(new Dimension(150, 40));

        clearButton = new RoundedButton("Clear",
                ModernColors.BACKGROUND_LIGHT,
                ModernColors.BACKGROUND_MEDIUM,
                ModernColors.TEXT_PRIMARY);
        clearButton.setPreferredSize(new Dimension(100, 40));

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(ModernColors.ERROR);
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(movieLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(movieComboBox, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(dateTimeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(dateTimeField, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(seatsLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(seatsField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(studioLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(studioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        updateMovieComboBox();
    }

    private void setupListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSchedule();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void addSchedule() {

        Movie selectedMovie = (Movie) movieComboBox.getSelectedItem();
        if (selectedMovie == null) {
            messageLabel.setText("Please select a movie");
            return;
        }

        String dateTime = dateTimeField.getText().trim();
        if (dateTime.isEmpty()) {
            messageLabel.setText("Date and time cannot be empty");
            return;
        }

        if (!Schedule.isValidDateTime(dateTime)) {
            messageLabel.setText("Invalid date and time format. Use yyyy-MM-dd HH:mm");
            return;
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date scheduleDate = format.parse(dateTime);
            Date now = new Date();
            if (scheduleDate.before(now)) {
                messageLabel.setText("Cannot add a schedule in the past");
                return;
            }
        } catch (ParseException e) {
            messageLabel.setText("Error parsing date");
            return;
        }

        String seatsText = seatsField.getText().trim();
        if (seatsText.isEmpty()) {
            messageLabel.setText("Number of seats cannot be empty");
            return;
        }

        int seats;
        try {
            seats = Integer.parseInt(seatsText);
            if (seats <= 0) {
                messageLabel.setText("Number of seats must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid number of seats");
            return;
        }

        String priceText = priceField.getText().trim();
        if (priceText.isEmpty()) {
            messageLabel.setText("Price cannot be empty");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                messageLabel.setText("Price must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid price");
            return;
        }

        String studio = studioField.getText().trim();
        if (studio.isEmpty()) {
            messageLabel.setText("Studio name cannot be empty");
            return;
        }

        for (Schedule schedule : selectedMovie.getSchedules()) {
            if (schedule.getDateTime().equals(dateTime) && schedule.getStudioName().equals(studio)) {
                messageLabel.setText("A schedule with the same date, time and studio already exists");
                return;
            }
        }

        if (listener != null) {
            listener.onScheduleAdded(selectedMovie, dateTime, seats, price, studio);
            clearFields();
            messageLabel.setText("Schedule added successfully");
            messageLabel.setForeground(ModernColors.SUCCESS);
        }
    }

    private void clearFields() {
        dateTimeField.setText("");
        seatsField.setText("");
        priceField.setText("");
        studioField.setText("");
        messageLabel.setText(" ");
        messageLabel.setForeground(ModernColors.ERROR);
    }

    private void updateMovieComboBox() {
        movieComboBox.removeAllItems();

        if (movies.isEmpty()) {
            movieComboBox.setEnabled(false);
            addButton.setEnabled(false);
            messageLabel.setText("No movies available. Add some movies first!");
            return;
        }

        movieComboBox.setEnabled(true);
        addButton.setEnabled(true);
        messageLabel.setText(" ");

        for (Movie movie : movies) {
            movieComboBox.addItem(movie);
        }
    }

    public void refreshMovies(List<Movie> movies) {
        this.movies = movies;
        updateMovieComboBox();
    }

    public void setAddScheduleListener(AddScheduleListener listener) {
        this.listener = listener;
    }
}