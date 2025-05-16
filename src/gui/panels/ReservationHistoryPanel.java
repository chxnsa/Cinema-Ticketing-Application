package gui.panels;

import gui.themes.ModernColors;
import gui.components.RoundedButton;
import reservation.Reservation;
import user.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ReservationHistoryPanel extends JPanel {
    private Customer customer;
    private List<Reservation> allReservations;
    private List<Reservation> customerReservations;

    private JLabel titleLabel;
    private JPanel reservationsPanel;
    private JScrollPane scrollPane;
    private RoundedButton backButton;

    public ReservationHistoryPanel(Customer customer, List<Reservation> allReservations) {
        this.customer = customer;
        this.allReservations = allReservations;
        this.customerReservations = new ArrayList<>();

        setupPanel();
        initComponents();
        refreshReservations();
    }

    private void setupPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(ModernColors.BACKGROUND_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        titleLabel = new JLabel("My Reservations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        headerPanel.add(titleLabel, BorderLayout.WEST);

        reservationsPanel = new JPanel();
        reservationsPanel.setLayout(new BoxLayout(reservationsPanel, BoxLayout.Y_AXIS));
        reservationsPanel.setOpaque(false);

        scrollPane = new JScrollPane(reservationsPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setOpaque(false);

        JLabel emptyLabel = new JLabel("You don't have any reservations yet.");
        emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        emptyLabel.setForeground(ModernColors.TEXT_SECONDARY);
        emptyLabel.setHorizontalAlignment(JLabel.CENTER);

        emptyPanel.add(emptyLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createReservationCard(Reservation reservation) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(ModernColors.BACKGROUND_MEDIUM);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setOpaque(false);

        JLabel movieLabel = new JLabel(reservation.getSchedule().getMovie().getTitle());
        movieLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        movieLabel.setForeground(ModernColors.TEXT_PRIMARY);

        JLabel dateTimeLabel = new JLabel("Date & Time: " + reservation.getSchedule().getDateTime());
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateTimeLabel.setForeground(ModernColors.TEXT_SECONDARY);

        JLabel studioLabel = new JLabel("Studio: " + reservation.getSchedule().getStudioName());
        studioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studioLabel.setForeground(ModernColors.TEXT_SECONDARY);

        JLabel seatLabel = new JLabel("Seat Number: " + reservation.getSeatNumber());
        seatLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seatLabel.setForeground(ModernColors.TEXT_SECONDARY);

        infoPanel.add(movieLabel);
        infoPanel.add(dateTimeLabel);
        infoPanel.add(studioLabel);
        infoPanel.add(seatLabel);

        JPanel pricePanel = new JPanel(new GridBagLayout());
        pricePanel.setOpaque(false);

        JLabel priceLabel = new JLabel(String.format("Rp %,.0f", reservation.getTotalPrice()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(ModernColors.SUCCESS);

        pricePanel.add(priceLabel);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(pricePanel, BorderLayout.EAST);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernColors.BACKGROUND_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        return card;
    }

    private void updateReservationsPanel() {
        reservationsPanel.removeAll();

        if (customerReservations.isEmpty()) {
            JLabel emptyLabel = new JLabel("You don't have any reservations yet.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            emptyLabel.setForeground(ModernColors.TEXT_SECONDARY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setOpaque(false);
            emptyPanel.add(emptyLabel);

            reservationsPanel.add(emptyPanel);
        } else {
            java.util.Map<String, List<Reservation>> groupedReservations = new java.util.HashMap<>();

            for (Reservation reservation : customerReservations) {
                String key = reservation.getSchedule().getMovie().getTitle() + " - " +
                        reservation.getSchedule().getDateTime();

                if (!groupedReservations.containsKey(key)) {
                    groupedReservations.put(key, new ArrayList<>());
                }

                groupedReservations.get(key).add(reservation);
            }

            for (List<Reservation> group : groupedReservations.values()) {

                for (Reservation reservation : group) {
                    JPanel card = createReservationCard(reservation);
                    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, card.getPreferredSize().height));
                    card.setAlignmentX(Component.LEFT_ALIGNMENT);

                    reservationsPanel.add(card);
                    reservationsPanel.add(Box.createVerticalStrut(10));
                }
            }
        }

        reservationsPanel.revalidate();
        reservationsPanel.repaint();
    }

    public void refreshReservations() {

        customerReservations.clear();

        for (Reservation reservation : allReservations) {
            if (reservation.getCustomer().getUsername().equals(customer.getUsername())) {
                customerReservations.add(reservation);
            }
        }

        updateReservationsPanel();
    }

}