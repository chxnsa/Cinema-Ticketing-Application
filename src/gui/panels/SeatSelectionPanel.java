package gui.panels;

import gui.themes.ModernColors;
import gui.components.RoundedButton;
import movie.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionPanel extends JPanel {
    private Schedule schedule;
    private List<Integer> selectedSeats = new ArrayList<>();
    private JButton[][] seatButtons;
    private JPanel seatsPanel;
    private JLabel screenLabel;
    private JPanel selectedSeatsPanel;
    private JLabel selectedSeatsLabel;
    private JLabel totalPriceLabel;
    private RoundedButton confirmButton;
    private RoundedButton cancelButton;

    private SeatSelectionListener listener;

    public interface SeatSelectionListener {
        void onSeatSelectionConfirmed(List<Integer> seatNumbers);

        void onSeatSelectionCancelled();
    }

    public SeatSelectionPanel(Schedule schedule) {
        this.schedule = schedule;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(ModernColors.BACKGROUND_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(schedule.getMovie().getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        JLabel dateTimeLabel = new JLabel(schedule.getDateTime());
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateTimeLabel.setForeground(ModernColors.TEXT_SECONDARY);

        JLabel studioLabel = new JLabel("Studio: " + schedule.getStudioName());
        studioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        studioLabel.setForeground(ModernColors.TEXT_SECONDARY);

        infoPanel.add(titleLabel);
        infoPanel.add(new JLabel(" | "));
        infoPanel.add(dateTimeLabel);
        infoPanel.add(new JLabel(" | "));
        infoPanel.add(studioLabel);

        add(infoPanel, BorderLayout.NORTH);

        JPanel screenPanel = new JPanel(new BorderLayout());
        screenPanel.setOpaque(false);

        screenLabel = new JLabel("SCREEN");
        screenLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        screenLabel.setForeground(ModernColors.TEXT_SECONDARY);
        screenLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel screenVisualization = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(ModernColors.BACKGROUND_LIGHT);
                g2d.fillRect(0, 0, getWidth(), 5);
                g2d.dispose();
            }
        };
        screenVisualization.setPreferredSize(new Dimension(600, 30));
        screenVisualization.setOpaque(false);

        screenPanel.add(screenLabel, BorderLayout.NORTH);
        screenPanel.add(screenVisualization, BorderLayout.CENTER);

        seatsPanel = new JPanel();
        seatsPanel.setOpaque(false);

        int totalSeats = schedule.getTotalSeats();
        int rows = (int) Math.ceil(Math.sqrt(totalSeats));
        int cols = (int) Math.ceil((double) totalSeats / rows);

        seatsPanel.setLayout(new GridLayout(rows, cols, 8, 8));

        seatButtons = new JButton[rows][cols];
        int seatNumber = 1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (seatNumber <= totalSeats) {
                    JButton seatButton = createSeatButton(seatNumber);
                    seatButtons[i][j] = seatButton;
                    seatsPanel.add(seatButton);
                    seatNumber++;
                } else {
                    JPanel emptySpace = new JPanel();
                    emptySpace.setOpaque(false);
                    seatsPanel.add(emptySpace);
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(seatsPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        legendPanel.setOpaque(false);

        addLegendItem(legendPanel, ModernColors.SEAT_AVAILABLE, "Available");
        addLegendItem(legendPanel, ModernColors.SEAT_SELECTED, "Selected");
        addLegendItem(legendPanel, ModernColors.SEAT_BOOKED, "Booked");

        selectedSeatsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        selectedSeatsPanel.setOpaque(false);

        selectedSeatsLabel = new JLabel("Selected Seats: None");
        selectedSeatsLabel.setForeground(ModernColors.TEXT_PRIMARY);
        selectedSeatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        totalPriceLabel = new JLabel("Total Price: Rp 0");
        totalPriceLabel.setForeground(ModernColors.TEXT_PRIMARY);
        totalPriceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        confirmButton = new RoundedButton("Confirm Selection");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null && !selectedSeats.isEmpty()) {
                    listener.onSeatSelectionConfirmed(selectedSeats);
                }
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
                    listener.onSeatSelectionCancelled();
                }
            }
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        selectedSeatsPanel.add(selectedSeatsLabel);
        selectedSeatsPanel.add(totalPriceLabel);
        selectedSeatsPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(screenPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(legendPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        add(selectedSeatsPanel, BorderLayout.SOUTH);
    }

    private JButton createSeatButton(int seatNumber) {
        final int seatNum = seatNumber;
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (!schedule.isSeatAvailable(seatNum)) {
                    g2d.setColor(ModernColors.SEAT_BOOKED);
                } else if (selectedSeats.contains(seatNum)) {
                    g2d.setColor(ModernColors.SEAT_SELECTED);
                } else {
                    g2d.setColor(ModernColors.SEAT_AVAILABLE);
                }

                g2d.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 10, 10);

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));

                String seatText = String.valueOf(seatNum);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(seatText);
                int textHeight = fm.getHeight();

                g2d.drawString(seatText,
                        (getWidth() - textWidth) / 2,
                        (getHeight() + textHeight / 2) / 2);

                g2d.dispose();
            }
        };

        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(40, 40));
        button.setFocusPainted(false);

        if (schedule.isSeatAvailable(seatNumber)) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleSeatSelection(seatNumber);
                }
            });
        } else {
            button.setEnabled(false);
        }

        return button;
    }

    private void toggleSeatSelection(int seatNumber) {
        if (selectedSeats.contains(seatNumber)) {
            selectedSeats.remove(Integer.valueOf(seatNumber));
        } else {
            selectedSeats.add(seatNumber);
        }

        updateSelectionInfo();
        repaint();
    }

    private void updateSelectionInfo() {
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("Selected Seats: None");
            totalPriceLabel.setText("Total Price: Rp 0");
            confirmButton.setEnabled(false);
        } else {
            StringBuilder seatsList = new StringBuilder("Selected Seats: ");
            for (int i = 0; i < selectedSeats.size(); i++) {
                seatsList.append(selectedSeats.get(i));
                if (i < selectedSeats.size() - 1) {
                    seatsList.append(", ");
                }
            }

            double totalPrice = schedule.getPrice() * selectedSeats.size();
            selectedSeatsLabel.setText(seatsList.toString());
            totalPriceLabel.setText(String.format("Total Price: Rp %,.0f", totalPrice));
            confirmButton.setEnabled(true);
        }
    }

    private void addLegendItem(JPanel panel, Color color, String text) {
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        itemPanel.setOpaque(false);

        JPanel colorBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2d.dispose();
            }
        };
        colorBox.setOpaque(false);
        colorBox.setPreferredSize(new Dimension(20, 20));

        JLabel label = new JLabel(text);
        label.setForeground(ModernColors.TEXT_SECONDARY);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        itemPanel.add(colorBox);
        itemPanel.add(label);

        panel.add(itemPanel);
    }

    public void setSeatSelectionListener(SeatSelectionListener listener) {
        this.listener = listener;
    }
}