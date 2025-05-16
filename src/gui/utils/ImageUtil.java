package gui.utils;

import gui.themes.ModernColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for loading, scaling, and caching movie images
 */
public class ImageUtil {
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();
    private static final ImageIcon DEFAULT_MOVIE_IMAGE;
    private static final String IMAGES_DIR = "images/movies/";

    static {
        // Create images directory if it doesn't exist
        File imagesDir = new File(IMAGES_DIR);
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }

        // Initialize default movie image
        BufferedImage defaultImg = new BufferedImage(180, 270, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = defaultImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill with gradient background
        GradientPaint gp = new GradientPaint(
                0, 0, ModernColors.BACKGROUND_MEDIUM,
                0, 270, ModernColors.BACKGROUND_DARK);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, 180, 270);

        // Draw film icon or placeholder text
        g2d.setColor(ModernColors.TEXT_SECONDARY);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
        g2d.drawString("No Image", 50, 130);
        g2d.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        g2d.drawString("SINETIX", 55, 160);

        g2d.dispose();
        DEFAULT_MOVIE_IMAGE = new ImageIcon(defaultImg);
    }

    /**
     * Loads an image from file path with caching
     * 
     * @param path   File path to the image
     * @param width  Target width
     * @param height Target height
     * @return Scaled ImageIcon
     */
    public static ImageIcon loadMovieImage(String path, int width, int height) {
        // Check cache first
        String cacheKey = path + "_" + width + "x" + height;
        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        try {
            File imageFile = new File(path);
            if (!imageFile.exists()) {
                return getDefaultMovieImage(width, height);
            }

            BufferedImage originalImage = ImageIO.read(imageFile);
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);

            // Cache the scaled image
            imageCache.put(cacheKey, icon);
            return icon;

        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            return getDefaultMovieImage(width, height);
        }
    }

    /**
     * Returns the default movie image scaled to specified dimensions
     * 
     * @param width  Target width
     * @param height Target height
     * @return Scaled default ImageIcon
     */
    public static ImageIcon getDefaultMovieImage(int width, int height) {
        String cacheKey = "default_" + width + "x" + height;

        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        Image scaledImage = DEFAULT_MOVIE_IMAGE.getImage().getScaledInstance(
                width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Cache the result
        imageCache.put(cacheKey, scaledIcon);
        return scaledIcon;
    }

    /**
     * Opens a file chooser dialog to select an image
     * 
     * @param parent Parent component
     * @return Selected file path or null if canceled
     */
    public static String selectMovieImage(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Movie Poster");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Add image file filter
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                        name.endsWith(".png") || name.endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.png, *.gif)";
            }
        });

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Copy file to images directory with a unique name
            try {
                String fileName = "movie_" + System.currentTimeMillis() +
                        getFileExtension(selectedFile.getName());
                File destFile = new File(IMAGES_DIR + fileName);

                // Copy file
                BufferedImage img = ImageIO.read(selectedFile);
                ImageIO.write(img, getFileExtension(selectedFile.getName()).substring(1),
                        destFile);

                return destFile.getPath();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent,
                        "Error saving image: " + e.getMessage(),
                        "Image Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return null;
    }

    /**
     * Gets file extension with dot
     * 
     * @param fileName File name
     * @return File extension (e.g. ".jpg")
     */
    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0) {
            return fileName.substring(lastDot);
        }
        return ".jpg"; // Default extension
    }

    /**
     * Creates a rounded corner image
     * 
     * @param image        Source image
     * @param cornerRadius Corner radius
     * @return Rounded corner image
     */
    public static Image createRoundedImage(Image image, int cornerRadius) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();

        // Set rendering hints for better quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create rounded rectangle
        g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return outputImage;
    }
}