package gui.frames;

import gui.themes.ModernColors;
import gui.components.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;

public class DialogFrame extends JFrame {
    private JPanel contentPanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JButton closeButton;

    private DialogListener dialogListener;

    public interface DialogListener {
        void onDialogClosed(DialogFrame dialog);
    }

    public DialogFrame(String title) {
        this(title, 600, 400);
    }

    public DialogFrame(String title, int width, int height) {
        setupFrame(title, width, height);
        initComponents();
    }

    private void setupFrame(String title, int width, int height) {
        setTitle(title);
        setUndecorated(true);
        setSize(width, height);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));

        setMinimumSize(new Dimension(300, 200));

        contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(ModernColors.BACKGROUND_DARK);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));

                g2d.setColor(ModernColors.BACKGROUND_LIGHT);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));

                g2d.dispose();
            }
        };

        contentPanel.setOpaque(false);
        setContentPane(contentPanel);

        MouseDragListener dragListener = new MouseDragListener(this);
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
    }

    private void initComponents() {

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        titleLabel = new JLabel(getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        closeButton = new JButton("×");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        closeButton.setForeground(ModernColors.TEXT_PRIMARY);
        closeButton.setBackground(null);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeDialog();
            }
        });

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(closeButton, BorderLayout.EAST);

        contentPanel.add(titlePanel, BorderLayout.NORTH);
    }

    public void setMainContent(JPanel panel) {

        Component centerComponent = ((BorderLayout) contentPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) {
            contentPanel.remove(centerComponent);
        }

        if (panel.getPreferredSize().height > 300) {
            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setBorder(null);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            contentPanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            contentPanel.add(panel, BorderLayout.CENTER);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void setDialogButtons(String primaryText, String secondaryText,
            ActionListener primaryAction, ActionListener secondaryAction) {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setOpaque(false);

        if (primaryText != null && !primaryText.isEmpty()) {
            RoundedButton primaryButton = new RoundedButton(primaryText);
            primaryButton.setPreferredSize(new Dimension(120, 40));

            if (primaryAction != null) {
                primaryButton.addActionListener(primaryAction);
            } else {
                primaryButton.addActionListener(e -> closeDialog());
            }

            buttonPanel.add(primaryButton);
        }

        if (secondaryText != null && !secondaryText.isEmpty()) {
            RoundedButton secondaryButton = new RoundedButton(secondaryText,
                    ModernColors.BACKGROUND_LIGHT,
                    ModernColors.BACKGROUND_MEDIUM,
                    ModernColors.TEXT_PRIMARY);
            secondaryButton.setPreferredSize(new Dimension(120, 40));

            if (secondaryAction != null) {
                secondaryButton.addActionListener(secondaryAction);
            } else {
                secondaryButton.addActionListener(e -> closeDialog());
            }

            buttonPanel.add(secondaryButton);
        }

        Component southComponent = ((BorderLayout) contentPanel.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        if (southComponent != null) {
            contentPanel.remove(southComponent);
        }

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static DialogFrame showInfoDialog(Component parent, String title, String message) {
        DialogFrame dialog = new DialogFrame(title, 400, 250);

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(ModernColors.TEXT_PRIMARY);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(messageLabel, BorderLayout.CENTER);

        dialog.setMainContent(panel);
        dialog.setDialogButtons("OK", null, null, null);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return dialog;
    }

    public static DialogFrame showConfirmDialog(Component parent, String title, String message,
            String confirmText, String cancelText,
            ActionListener confirmAction) {
        DialogFrame dialog = new DialogFrame(title, 400, 250);

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(ModernColors.TEXT_PRIMARY);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(messageLabel, BorderLayout.CENTER);

        dialog.setMainContent(panel);
        dialog.setDialogButtons(confirmText, cancelText, confirmAction, e -> dialog.closeDialog());
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return dialog;
    }

    public void closeDialog() {

        if (dialogListener != null) {
            dialogListener.onDialogClosed(this);
        }

        setVisible(false);
        dispose();
    }

    public void setDialogListener(DialogListener listener) {
        this.dialogListener = listener;
    }

    private static class MouseDragListener extends MouseAdapter {
        private final JFrame frame;
        private Point dragStart;

        public MouseDragListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            dragStart = e.getPoint();
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            dragStart = null;
        }

        @Override
        public void mouseDragged(java.awt.event.MouseEvent e) {
            if (dragStart != null) {
                Point currentLocation = frame.getLocation();
                frame.setLocation(
                        currentLocation.x + e.getX() - dragStart.x,
                        currentLocation.y + e.getY() - dragStart.y);
            }
        }
    }
}