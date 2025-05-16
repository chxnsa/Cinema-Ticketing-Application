package gui.components;

import gui.themes.ModernColors;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class CustomTextField extends JTextField {
    private String placeholder;
    private Color placeholderColor = ModernColors.TEXT_TERTIARY;
    private Color textColor = ModernColors.TEXT_PRIMARY;
    private Color backgroundColor = ModernColors.BACKGROUND_LIGHT;
    private Color borderColor = ModernColors.BACKGROUND_MEDIUM;
    private Color focusBorderColor = ModernColors.ACCENT_PRIMARY;
    private int cornerRadius = 8;
    private boolean hasFocus = false;

    public CustomTextField() {
        this("");
    }

    public CustomTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
        setupTextField();
    }

    private void setupTextField() {

        setOpaque(false);
        setBackground(backgroundColor);
        setForeground(textColor);
        setCaretColor(textColor);
        setSelectionColor(ModernColors.ACCENT_PRIMARY);
        setSelectedTextColor(ModernColors.TEXT_PRIMARY);

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 15, 10, 15);
        setBorder(emptyBorder);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hasFocus = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                hasFocus = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        g2.setColor(hasFocus ? focusBorderColor : borderColor);
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        super.paintComponent(g);

        if (getText().isEmpty() && placeholder != null && !placeholder.isEmpty() && !hasFocus) {
            g2.setColor(placeholderColor);
            g2.setFont(getFont());
            int x = getInsets().left;
            int y = (getHeight() - g2.getFontMetrics().getHeight()) / 2 + g2.getFontMetrics().getAscent();
            g2.drawString(placeholder, x, y);
        }

        g2.dispose();
    }

    @Override
    public void setBorder(Border border) {

        super.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public void setPlaceholderColor(Color placeholderColor) {
        this.placeholderColor = placeholderColor;
        repaint();
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
        repaint();
    }

    public void setFieldBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setFocusBorderColor(Color focusBorderColor) {
        this.focusBorderColor = focusBorderColor;
        repaint();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }
}
