package gui.themes;

import javax.swing.*;
import java.awt.*;

public class DarkTheme {

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public static final int BORDER_RADIUS_SMALL = 8;
    public static final int BORDER_RADIUS_MEDIUM = 12;
    public static final int BORDER_RADIUS_LARGE = 15;

    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 15;
    public static final int PADDING_LARGE = 20;

    public static void apply() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus look and feel not available, using default");
        }

        gui.utils.UIManager.applyDarkThemeOverrides();

        UIManager.put("ToolTip.background", ModernColors.BACKGROUND_MEDIUM);
        UIManager.put("ToolTip.foreground", ModernColors.TEXT_PRIMARY);
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(ModernColors.BACKGROUND_LIGHT));

        UIManager.put("OptionPane.background", ModernColors.BACKGROUND_DARK);
        UIManager.put("OptionPane.messageForeground", ModernColors.TEXT_PRIMARY);
        UIManager.put("OptionPane.buttonBackground", ModernColors.BUTTON_PRIMARY);
        UIManager.put("OptionPane.buttonForeground", ModernColors.TEXT_PRIMARY);
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(ModernColors.TEXT_PRIMARY);
        return label;
    }

    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBTITLE_FONT);
        label.setForeground(ModernColors.TEXT_PRIMARY);
        return label;
    }

    public static JLabel createBodyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(ModernColors.TEXT_SECONDARY);
        return label;
    }

    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernColors.BACKGROUND_DARK);
        return panel;
    }
}