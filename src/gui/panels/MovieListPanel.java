package gui.panels;

import gui.themes.ModernColors;
import gui.components.MovieCard;
import movie.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieListPanel extends JPanel {
    private List<Movie> movies;
    private JPanel gridPanel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private JPanel emptyPanel;
    private MovieCard.MovieCardListener movieCardListener;

    public MovieListPanel(List<Movie> movies) {
        this.movies = movies;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(ModernColors.BACKGROUND_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {

        titleLabel = new JLabel("Now Showing");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));

        gridPanel = new JPanel(new GridLayout(0, 4, 20, 20));
        gridPanel.setOpaque(false);

        emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setOpaque(false);

        JLabel emptyLabel = new JLabel("No movies available at the moment.");
        emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        emptyLabel.setForeground(ModernColors.TEXT_SECONDARY);
        emptyLabel.setHorizontalAlignment(JLabel.CENTER);

        emptyPanel.add(emptyLabel, BorderLayout.CENTER);

        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(titleLabel, BorderLayout.NORTH);

        updateMovieDisplay();

        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateMovieDisplay() {
        gridPanel.removeAll();

        if (movies == null || movies.isEmpty()) {
            scrollPane.setViewportView(emptyPanel);
            return;
        }

        for (Movie movie : movies) {
            MovieCard movieCard = new MovieCard(movie);

            if (movieCardListener != null) {
                movieCard.setMovieCardListener(movieCardListener);
            }

            gridPanel.add(movieCard);
        }

        int rowCount = (int) Math.ceil((double) movies.size() / 4);
        gridPanel.setPreferredSize(new Dimension(0, rowCount * 340));

        scrollPane.setViewportView(gridPanel);
    }

    public void setMovieCardListener(MovieCard.MovieCardListener listener) {
        this.movieCardListener = listener;
        updateMovieDisplay();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        updateMovieDisplay();
    }
}