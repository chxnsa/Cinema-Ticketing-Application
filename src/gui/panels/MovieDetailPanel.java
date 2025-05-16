package gui.panels;

import gui.themes.ModernColors;
import gui.components.RoundedButton;
import gui.utils.ImageUtil;
import movie.Movie;
import movie.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MovieDetailPanel extends JPanel {
    private Movie movie;
    private JLabel titleLabel;
    private JLabel imageLabel;
    private JLabel genreLabel;
    private JLabel durationLabel;
    private JLabel directorLabel;
    private JTextArea descriptionArea;
    private JPanel schedulesPanel;
    private RoundedButton backButton;

    private MovieDetailListener listener;

    public interface MovieDetailListener {
        void onScheduleSelected(Movie movie, Schedule schedule);

        void onBackToMovieList();
    }

    public MovieDetailPanel(Movie movie) {
        this.movie = movie;
        setupPanel();
        if (movie != null) {
            initComponents();
        } else {
            initEmptyComponents();
        }
    }

    private void initEmptyComponents() {
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setOpaque(false);

        JLabel emptyLabel = new JLabel("No movies available");
        emptyLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        emptyLabel.setForeground(ModernColors.TEXT_SECONDARY);
        emptyLabel.setHorizontalAlignment(JLabel.CENTER);

        backButton = new RoundedButton("Back",
                ModernColors.BACKGROUND_LIGHT,
                ModernColors.BACKGROUND_MEDIUM,
                ModernColors.TEXT_PRIMARY);
        backButton.setPreferredSize(new Dimension(100, 40));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.onBackToMovieList();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        emptyPanel.add(emptyLabel, BorderLayout.CENTER);
        emptyPanel.add(buttonPanel, BorderLayout.NORTH);

        add(emptyPanel, BorderLayout.CENTER);
    }

    private void setupPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(ModernColors.BACKGROUND_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        if (movie == null) {
            initEmptyComponents();
            return;
        }
        JPanel infoPanel = new JPanel(new BorderLayout(0, 15));
        infoPanel.setOpaque(false);
        infoPanel.setPreferredSize(new Dimension(300, 0));

        ImageIcon movieImage;
        if (movie.getImagePath() != null && !movie.getImagePath().isEmpty()) {
            movieImage = ImageUtil.loadMovieImage(movie.getImagePath(), 260, 380);
        } else {
            movieImage = ImageUtil.getDefaultMovieImage(260, 380);
        }

        imageLabel = new JLabel(movieImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel metaPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        metaPanel.setOpaque(false);

        genreLabel = new JLabel("Genre: " + movie.getGenre());
        genreLabel.setForeground(ModernColors.TEXT_SECONDARY);
        genreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        durationLabel = new JLabel("Duration: " + movie.getDuration() + " minutes");
        durationLabel.setForeground(ModernColors.TEXT_SECONDARY);
        durationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        directorLabel = new JLabel("Director: " + movie.getDirector());
        directorLabel.setForeground(ModernColors.TEXT_SECONDARY);
        directorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        metaPanel.add(genreLabel);
        metaPanel.add(durationLabel);
        metaPanel.add(directorLabel);

        infoPanel.add(imageLabel, BorderLayout.NORTH);
        infoPanel.add(metaPanel, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new BorderLayout(0, 20));
        detailsPanel.setOpaque(false);

        JPanel titlePanel = new JPanel(new BorderLayout(10, 0));
        titlePanel.setOpaque(false);

        titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        backButton = new RoundedButton("Back",
                ModernColors.BACKGROUND_LIGHT,
                ModernColors.BACKGROUND_MEDIUM,
                ModernColors.TEXT_PRIMARY);
        backButton.setPreferredSize(new Dimension(100, 40));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.onBackToMovieList();
                }
            }
        });

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.setOpaque(false);
        backPanel.add(backButton);

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(backPanel, BorderLayout.EAST);

        JPanel descriptionPanel = new JPanel(new BorderLayout(0, 10));
        descriptionPanel.setOpaque(false);

        JLabel descriptionTitle = new JLabel("Description");
        descriptionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        descriptionTitle.setForeground(ModernColors.TEXT_PRIMARY);

        descriptionArea = new JTextArea(movie.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setForeground(ModernColors.TEXT_SECONDARY);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        descriptionPanel.add(descriptionTitle, BorderLayout.NORTH);
        descriptionPanel.add(descriptionArea, BorderLayout.CENTER);

        JPanel schedulesContainer = new JPanel(new BorderLayout(0, 10));
        schedulesContainer.setOpaque(false);

        JLabel schedulesTitle = new JLabel("Schedules");
        schedulesTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        schedulesTitle.setForeground(ModernColors.TEXT_PRIMARY);

        schedulesPanel = new JPanel();
        schedulesPanel.setLayout(new BoxLayout(schedulesPanel, BoxLayout.Y_AXIS));
        schedulesPanel.setOpaque(false);

        createSchedulesList();

        schedulesContainer.add(schedulesTitle, BorderLayout.NORTH);
        schedulesContainer.add(schedulesPanel, BorderLayout.CENTER);

        detailsPanel.add(titlePanel, BorderLayout.NORTH);
        detailsPanel.add(descriptionPanel, BorderLayout.CENTER);
        detailsPanel.add(schedulesContainer, BorderLayout.SOUTH);

        add(infoPanel, BorderLayout.WEST);
        add(detailsPanel, BorderLayout.CENTER);
    }

    private void createSchedulesList() {
        schedulesPanel.removeAll();

        List<Schedule> schedules = movie.getSchedules();

        if (schedules.isEmpty()) {
            JLabel noSchedulesLabel = new JLabel("No schedules available for this movie.");
            noSchedulesLabel.setForeground(ModernColors.TEXT_TERTIARY);
            noSchedulesLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noSchedulesLabel.setAlignmentX(LEFT_ALIGNMENT);

            schedulesPanel.add(noSchedulesLabel);
        } else {
            for (final Schedule schedule : schedules) {
                JPanel scheduleItem = createScheduleItem(schedule);
                scheduleItem.setAlignmentX(LEFT_ALIGNMENT);
                schedulesPanel.add(scheduleItem);
                schedulesPanel.add(Box.createVerticalStrut(10));
            }
        }

        schedulesPanel.revalidate();
        schedulesPanel.repaint();
    }

    private JPanel createScheduleItem(final Schedule schedule) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setOpaque(false);

        JLabel dateTimeLabel = new JLabel(schedule.getDateTime());
        dateTimeLabel.setForeground(ModernColors.TEXT_PRIMARY);
        dateTimeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel studioLabel = new JLabel("Studio: " + schedule.getStudioName());
        studioLabel.setForeground(ModernColors.TEXT_SECONDARY);
        studioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel priceLabel = new JLabel(String.format("Price: Rp %,.0f", schedule.getPrice()));
        priceLabel.setForeground(ModernColors.TEXT_SECONDARY);
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        infoPanel.add(dateTimeLabel);
        infoPanel.add(studioLabel);
        infoPanel.add(priceLabel);

        JPanel availabilityPanel = new JPanel(new BorderLayout());
        availabilityPanel.setOpaque(false);

        String availabilityText = schedule.getAvailableSeats() + " seats available";
        JLabel availabilityLabel = new JLabel(availabilityText);
        availabilityLabel.setForeground(ModernColors.TEXT_SECONDARY);
        availabilityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        RoundedButton bookButton = new RoundedButton("Book");
        bookButton.setEnabled(schedule.isAvailable() && !schedule.isPast());

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.onScheduleSelected(movie, schedule);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(bookButton);

        availabilityPanel.add(availabilityLabel, BorderLayout.NORTH);
        availabilityPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(availabilityPanel, BorderLayout.EAST);

        if (schedule.isPast()) {
            panel.setEnabled(false);
            JLabel pastLabel = new JLabel("This schedule has passed");
            pastLabel.setForeground(ModernColors.ERROR);
            pastLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

            JPanel pastPanel = new JPanel(new BorderLayout());
            pastPanel.setOpaque(false);
            pastPanel.add(panel, BorderLayout.CENTER);
            pastPanel.add(pastLabel, BorderLayout.SOUTH);

            return pastPanel;
        }

        return panel;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        updateDisplay();
    }

    private void updateDisplay() {

        ImageIcon movieImage;
        if (movie.getImagePath() != null && !movie.getImagePath().isEmpty()) {
            movieImage = ImageUtil.loadMovieImage(movie.getImagePath(), 260, 380);
        } else {
            movieImage = ImageUtil.getDefaultMovieImage(260, 380);
        }

        imageLabel.setIcon(movieImage);

        titleLabel.setText(movie.getTitle());
        genreLabel.setText("Genre: " + movie.getGenre());
        durationLabel.setText("Duration: " + movie.getDuration() + " minutes");
        directorLabel.setText("Director: " + movie.getDirector());
        descriptionArea.setText(movie.getDescription());

        createSchedulesList();

        revalidate();
        repaint();
    }

    public void setMovieDetailListener(MovieDetailListener listener) {
        this.listener = listener;
    }
}