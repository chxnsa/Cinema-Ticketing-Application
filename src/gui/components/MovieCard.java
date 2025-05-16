package gui.components;

import gui.themes.ModernColors;
import gui.utils.ImageUtil;
import movie.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MovieCard extends JPanel {
    private Movie movie;
    private ImageIcon posterImage;
    private boolean isHovered = false;
    private boolean isSelected = false;
    private final int CARD_RADIUS = 15;
    private MovieCardListener listener;

    public interface MovieCardListener {
        void onMovieCardClicked(Movie movie);
    }

    public MovieCard(Movie movie) {
        this.movie = movie;

        if (movie.getImagePath() != null && !movie.getImagePath().isEmpty()) {
            this.posterImage = ImageUtil.loadMovieImage(movie.getImagePath(), 180, 270);
        } else {
            this.posterImage = ImageUtil.getDefaultMovieImage(180, 270);
        }

        setPreferredSize(new Dimension(200, 320));
        setBackground(ModernColors.BACKGROUND_MEDIUM);

        setupMouseInteraction();
    }

    private void setupMouseInteraction() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.onMovieCardClicked(movie);
                }
            }
        });
    }

    public void setMovieCardListener(MovieCardListener listener) {
        this.listener = listener;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (isSelected) {
            g2.setColor(ModernColors.ACCENT_PRIMARY);
        } else if (isHovered) {
            g2.setColor(ModernColors.BACKGROUND_LIGHT);
        } else {
            g2.setColor(ModernColors.BACKGROUND_MEDIUM);
        }

        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), CARD_RADIUS, CARD_RADIUS));

        if (posterImage != null) {
            int imgWidth = getWidth() - 20;
            int imgHeight = (int) (getHeight() * 0.70);
            g2.drawImage(posterImage.getImage(), 10, 10, imgWidth, imgHeight, null);

            g2.setColor(ModernColors.TEXT_PRIMARY);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            String title = movie.getTitle();

            if (g2.getFontMetrics().stringWidth(title) > imgWidth) {
                while (g2.getFontMetrics().stringWidth(title + "...") > imgWidth && title.length() > 0) {
                    title = title.substring(0, title.length() - 1);
                }
                title += "...";
            }

            g2.drawString(title, 15, imgHeight + 30);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2.setColor(ModernColors.TEXT_SECONDARY);
            g2.drawString(movie.getGenre() + " | " + movie.getDuration() + " min", 15, imgHeight + 50);

            if (!movie.getSchedules().isEmpty()) {
                g2.setColor(ModernColors.SUCCESS);
                g2.fillOval(getWidth() - 25, imgHeight + 25, 10, 10);

                g2.setColor(ModernColors.TEXT_SECONDARY);
                g2.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                g2.drawString(movie.getSchedules().size() + " showtimes", 15, imgHeight + 70);
            } else {

                g2.setColor(ModernColors.TEXT_TERTIARY);
                g2.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                g2.drawString("No showtimes available", 15, imgHeight + 70);
            }
        } else {

            g2.setColor(ModernColors.TEXT_PRIMARY);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            FontMetrics fm = g2.getFontMetrics();
            String title = movie.getTitle();

            int titleWidth = fm.stringWidth(title);
            if (titleWidth > getWidth() - 20) {
                while (fm.stringWidth(title + "...") > getWidth() - 20 && title.length() > 0) {
                    title = title.substring(0, title.length() - 1);
                }
                title += "...";
                titleWidth = fm.stringWidth(title);
            }

            g2.drawString(title, (getWidth() - titleWidth) / 2, getHeight() / 2);
        }

        if (isSelected) {
            g2.setColor(ModernColors.ACCENT_PRIMARY);
            g2.setStroke(new BasicStroke(2f));
            g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, CARD_RADIUS, CARD_RADIUS));
        }

        g2.dispose();
    }

    public Movie getMovie() {
        return movie;
    }
}
