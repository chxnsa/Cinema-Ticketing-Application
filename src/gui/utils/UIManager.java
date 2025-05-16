package gui.utils;

import gui.themes.ModernColors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * Utility class for managing UI settings and theme customization
 */
public class UIManager {

    /**
     * Applies dark theme overrides to the Swing UI components
     */
    public static void applyDarkThemeOverrides() {
        // Set global UI properties for dark theme
        UIDefaults defaults = javax.swing.UIManager.getLookAndFeelDefaults();

        // Panel backgrounds
        defaults.put("Panel.background", ModernColors.BACKGROUND_DARK);
        defaults.put("OptionPane.background", ModernColors.BACKGROUND_DARK);

        // Text components
        defaults.put("TextField.background", ModernColors.BACKGROUND_LIGHT);
        defaults.put("TextField.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("TextField.caretForeground", ModernColors.TEXT_PRIMARY);
        defaults.put("TextArea.background", ModernColors.BACKGROUND_LIGHT);
        defaults.put("TextArea.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("TextArea.caretForeground", ModernColors.TEXT_PRIMARY);
        defaults.put("PasswordField.background", ModernColors.BACKGROUND_LIGHT);
        defaults.put("PasswordField.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("PasswordField.caretForeground", ModernColors.TEXT_PRIMARY);

        // Buttons
        defaults.put("Button.background", ModernColors.BUTTON_SECONDARY);
        defaults.put("Button.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("Button.select", ModernColors.BUTTON_HOVER);

        // Labels
        defaults.put("Label.foreground", ModernColors.TEXT_PRIMARY);

        // Lists and tables
        defaults.put("List.background", ModernColors.BACKGROUND_MEDIUM);
        defaults.put("List.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("List.selectionBackground", ModernColors.ACCENT_PRIMARY);
        defaults.put("List.selectionForeground", ModernColors.TEXT_PRIMARY);

        defaults.put("Table.background", ModernColors.BACKGROUND_MEDIUM);
        defaults.put("Table.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("Table.selectionBackground", ModernColors.ACCENT_PRIMARY);
        defaults.put("Table.selectionForeground", ModernColors.TEXT_PRIMARY);
        defaults.put("Table.gridColor", ModernColors.BACKGROUND_LIGHT);

        // Combo box
        defaults.put("ComboBox.background", ModernColors.BACKGROUND_LIGHT);
        defaults.put("ComboBox.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("ComboBox.selectionBackground", ModernColors.ACCENT_PRIMARY);
        defaults.put("ComboBox.selectionForeground", ModernColors.TEXT_PRIMARY);

        // Scroll bars
        defaults.put("ScrollBar.track", ModernColors.BACKGROUND_DARK);
        defaults.put("ScrollBar.thumb", ModernColors.BACKGROUND_LIGHT);
        defaults.put("ScrollBar.thumbDarkShadow", ModernColors.BACKGROUND_LIGHT);
        defaults.put("ScrollBar.thumbHighlight", ModernColors.BACKGROUND_LIGHT);
        defaults.put("ScrollBar.thumbShadow", ModernColors.BACKGROUND_LIGHT);

        // Tabbed pane
        defaults.put("TabbedPane.background", ModernColors.BACKGROUND_DARK);
        defaults.put("TabbedPane.foreground", ModernColors.TEXT_PRIMARY);
        defaults.put("TabbedPane.selected", ModernColors.ACCENT_PRIMARY);
        defaults.put("TabbedPane.selectedForeground", ModernColors.TEXT_PRIMARY);

        // Focus indication
        defaults.put("Component.focusColor", new ColorUIResource(ModernColors.ACCENT_PRIMARY));
    }

    /**
     * Creates a rounded border with the specified radius
     */
    public static Border createRoundedBorder(int radius, Color color) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 1, true),
                BorderFactory.createEmptyBorder(radius / 2, radius, radius / 2, radius));
    }

    /**
     * Creates a panel with gradient background
     */
    public static JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                GradientPaint gp = new GradientPaint(
                        0, 0, ModernColors.GRADIENT_START,
                        w, h, ModernColors.GRADIENT_END);

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
    }

    /**
     * Creates a transparent panel (useful for overlays)
     */
    public static JPanel createTransparentPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
}
