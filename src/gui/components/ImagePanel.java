package gui.components;

import gui.themes.ModernColors;
import gui.utils.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class ImagePanel extends JPanel {
    private ImageIcon image;
    private String caption;
    private boolean showCaption = false;
    private boolean isHovered = false;
    private boolean isRounded = true;
    private int cornerRadius = 12;
    private Color overlayColor = new Color(0, 0, 0, 100);

    private ImageClickListener clickListener;

    public interface ImageClickListener {
        void onImageClicked(ImagePanel source);
    }

    public ImagePanel() {
        this(null);
    }

    public ImagePanel(ImageIcon image) {
        this.image = image;
        setupPanel();
    }

    private void setupPanel() {
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(200, 300));

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
                if (clickListener != null) {
                    clickListener.onImageClicked(ImagePanel.this);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        Shape roundedRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                isRounded ? cornerRadius : 0,
                isRounded ? cornerRadius : 0);

        if (isRounded) {
            g2d.setClip(roundedRect);
        }

        if (image == null) {
            g2d.setColor(ModernColors.BACKGROUND_MEDIUM);
            g2d.fill(roundedRect);

            g2d.setColor(ModernColors.TEXT_SECONDARY);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String placeholder = "No Image";
            int textWidth = fm.stringWidth(placeholder);
            int textHeight = fm.getHeight();
            g2d.drawString(placeholder,
                    (getWidth() - textWidth) / 2,
                    (getHeight() - textHeight) / 2 + fm.getAscent());
        } else {

            g2d.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);

            if (isHovered) {
                g2d.setColor(overlayColor);
                g2d.fill(roundedRect);
            }

            if (showCaption && caption != null && !caption.isEmpty()) {

                int captionHeight = 40;
                Paint oldPaint = g2d.getPaint();
                GradientPaint gradient = new GradientPaint(
                        0, getHeight() - captionHeight,
                        new Color(0, 0, 0, 0),
                        0, getHeight(),
                        new Color(0, 0, 0, 180));
                g2d.setPaint(gradient);
                g2d.fillRect(0, getHeight() - captionHeight, getWidth(), captionHeight);
                g2d.setPaint(oldPaint);

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2d.drawString(caption, 10, getHeight() - 15);
            }
        }

        if (isRounded) {
            g2d.setColor(isHovered ? ModernColors.ACCENT_PRIMARY : ModernColors.BACKGROUND_MEDIUM);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.draw(roundedRect);
        }

        g2d.dispose();
    }

    public void setImage(ImageIcon image) {
        this.image = image;
        repaint();
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setCaption(String caption) {
        this.caption = caption;
        repaint();
    }

    public void setShowCaption(boolean showCaption) {
        this.showCaption = showCaption;
        repaint();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public void setRounded(boolean rounded) {
        this.isRounded = rounded;
        repaint();
    }

    public void setOverlayColor(Color overlayColor) {
        this.overlayColor = overlayColor;
        repaint();
    }

    public void setImageClickListener(ImageClickListener listener) {
        this.clickListener = listener;
    }
}