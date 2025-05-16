package gui.panels;

import gui.themes.ModernColors;
import gui.components.RoundedButton;
import gui.components.CustomTextField;
import gui.utils.ImageUtil;
import movie.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMoviePanel extends JPanel {
    private JLabel titleLabel;
    private CustomTextField titleField;
    private CustomTextField genreField;
    private CustomTextField durationField;
    private JTextArea descriptionArea;
    private CustomTextField directorField;
    private JLabel imageLabel;
    private JPanel imagePanel;
    private RoundedButton selectImageButton;
    private RoundedButton addButton;
    private RoundedButton cancelButton;

    private String selectedImagePath = "";
    private ImageIcon previewImage;

    private AddMovieListener listener;

    public interface AddMovieListener {
        void onMovieAdded(Movie movie);

        void onCancel();
    }

    public AddMoviePanel() {
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(ModernColors.BACKGROUND_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        titleLabel = new JLabel("Add New Movie");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        titleField = new CustomTextField("Movie Title");
        genreField = new CustomTextField("Genre");
        durationField = new CustomTextField("Duration (minutes)");

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setBackground(ModernColors.BACKGROUND_LIGHT);
        descriptionArea.setForeground(ModernColors.TEXT_PRIMARY);
        descriptionArea.setCaretColor(ModernColors.TEXT_PRIMARY);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setBorder(BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM));

        directorField = new CustomTextField("Director");

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setBorder(BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM));
        imagePanel.setPreferredSize(new Dimension(180, 270));

        previewImage = ImageUtil.getDefaultMovieImage(160, 240);

        imageLabel = new JLabel(previewImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        imagePanel.add(imageLabel, BorderLayout.CENTER);

        selectImageButton = new RoundedButton("Select Image",
                ModernColors.ACCENT_SECONDARY,
                new Color(ModernColors.ACCENT_SECONDARY.getRed(),
                        ModernColors.ACCENT_SECONDARY.getGreen(),
                        ModernColors.ACCENT_SECONDARY.getBlue(), 200),
                ModernColors.TEXT_PRIMARY);

        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMovieImage();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        addButton = new RoundedButton("Add Movie");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });

        cancelButton = new RoundedButton("Cancel",
                ModernColors.BACKGROUND_LIGHT,
                ModernColors.BACKGROUND_MEDIUM,
                ModernColors.TEXT_PRIMARY);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel titleInputLabel = new JLabel("Title:");
        titleInputLabel.setForeground(ModernColors.TEXT_PRIMARY);
        formPanel.add(titleInputLabel, gbc);

        gbc.gridy = 2;
        formPanel.add(titleField, gbc);

        gbc.gridy = 3;
        JLabel genreInputLabel = new JLabel("Genre:");
        genreInputLabel.setForeground(ModernColors.TEXT_PRIMARY);
        formPanel.add(genreInputLabel, gbc);

        gbc.gridy = 4;
        formPanel.add(genreField, gbc);

        gbc.gridy = 5;
        JLabel durationInputLabel = new JLabel("Duration (minutes):");
        durationInputLabel.setForeground(ModernColors.TEXT_PRIMARY);
        formPanel.add(durationInputLabel, gbc);

        gbc.gridy = 6;
        formPanel.add(durationField, gbc);

        gbc.gridy = 7;
        JLabel directorInputLabel = new JLabel("Director:");
        directorInputLabel.setForeground(ModernColors.TEXT_PRIMARY);
        formPanel.add(directorInputLabel, gbc);

        gbc.gridy = 8;
        formPanel.add(directorField, gbc);

        gbc.gridy = 9;
        JLabel descriptionInputLabel = new JLabel("Description:");
        descriptionInputLabel.setForeground(ModernColors.TEXT_PRIMARY);
        formPanel.add(descriptionInputLabel, gbc);

        gbc.gridy = 10;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(descriptionScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 10;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel imageSelectionPanel = new JPanel(new BorderLayout(0, 10));
        imageSelectionPanel.setOpaque(false);
        imageSelectionPanel.add(imagePanel, BorderLayout.CENTER);
        imageSelectionPanel.add(selectImageButton, BorderLayout.SOUTH);

        formPanel.add(imageSelectionPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void selectMovieImage() {
        String imagePath = ImageUtil.selectMovieImage(this);
        if (imagePath != null && !imagePath.isEmpty()) {
            selectedImagePath = imagePath;
            previewImage = ImageUtil.loadMovieImage(imagePath, 160, 240);
            imageLabel.setIcon(previewImage);
        }
    }

    private void addMovie() {
        String title = titleField.getText().trim();
        String genre = genreField.getText().trim();
        String durationStr = durationField.getText().trim();
        String description = descriptionArea.getText().trim();
        String director = directorField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Movie title cannot be empty",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (genre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Genre cannot be empty",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationStr);
            if (duration <= 0) {
                JOptionPane.showMessageDialog(this, "Duration must be a positive number",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid number",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description cannot be empty",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (director.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Director cannot be empty",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Movie movie = new Movie(title, genre, duration, description, director, selectedImagePath);

        if (listener != null) {
            listener.onMovieAdded(movie);
        }

        clearFields();
    }

    private void clearFields() {
        titleField.setText("");
        genreField.setText("");
        durationField.setText("");
        descriptionArea.setText("");
        directorField.setText("");
        selectedImagePath = "";
        previewImage = ImageUtil.getDefaultMovieImage(160, 240);
        imageLabel.setIcon(previewImage);
    }

    public void setAddMovieListener(AddMovieListener listener) {
        this.listener = listener;
    }
}