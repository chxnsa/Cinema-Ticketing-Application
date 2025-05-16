package gui.themes;

import java.awt.Color;

/**
 * Modern color palette for the Cinema Reservation System
 */
public class ModernColors {
    // Main theme colors
    public static final Color BACKGROUND_DARK = new Color(18, 18, 18);
    public static final Color BACKGROUND_MEDIUM = new Color(30, 30, 30);
    public static final Color BACKGROUND_LIGHT = new Color(40, 40, 40);

    // Accent colors
    public static final Color ACCENT_PRIMARY = new Color(103, 58, 183); // Purple
    public static final Color ACCENT_SECONDARY = new Color(233, 30, 99); // Pink

    // Text colors
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_SECONDARY = new Color(170, 170, 170);
    public static final Color TEXT_TERTIARY = new Color(120, 120, 120);

    // Status colors
    public static final Color SUCCESS = new Color(76, 175, 80);
    public static final Color WARNING = new Color(255, 152, 0);
    public static final Color ERROR = new Color(244, 67, 54);

    // Gradient colors
    public static final Color GRADIENT_START = new Color(103, 58, 183);
    public static final Color GRADIENT_END = new Color(233, 30, 99);

    // Button colors
    public static final Color BUTTON_PRIMARY = ACCENT_PRIMARY;
    public static final Color BUTTON_SECONDARY = BACKGROUND_LIGHT;
    public static final Color BUTTON_HOVER = new Color(123, 78, 203);

    // Seat selection colors
    public static final Color SEAT_AVAILABLE = new Color(76, 175, 80);
    public static final Color SEAT_SELECTED = ACCENT_PRIMARY;
    public static final Color SEAT_BOOKED = new Color(120, 120, 120);
}
