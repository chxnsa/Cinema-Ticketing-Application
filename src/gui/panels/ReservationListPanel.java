package gui.panels;

import gui.themes.ModernColors;
import gui.components.RoundedButton;
import reservation.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReservationListPanel extends JPanel {
    private List<Reservation> reservations;
    private JLabel titleLabel;
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private RoundedButton searchButton;
    private JComboBox<String> filterComboBox;

    public ReservationListPanel(List<Reservation> reservations) {
        this.reservations = reservations;
        setupPanel();
        initComponents();
        setupListeners();
        refreshReservations(reservations);
    }

    private void setupPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(ModernColors.BACKGROUND_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        titleLabel = new JLabel("All Reservations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setForeground(ModernColors.TEXT_SECONDARY);
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        filterComboBox = new JComboBox<>(new String[] { "All", "Today", "This Week", "This Month" });
        filterComboBox.setBackground(ModernColors.BACKGROUND_LIGHT);
        filterComboBox.setForeground(ModernColors.TEXT_PRIMARY);
        filterComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        String[] columnNames = { "ID", "Customer", "Movie", "Date & Time", "Seat", "Studio", "Price" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reservationTable = new JTable(tableModel);
        reservationTable.setBackground(ModernColors.BACKGROUND_MEDIUM);
        reservationTable.setForeground(ModernColors.TEXT_PRIMARY);
        reservationTable.setSelectionBackground(ModernColors.ACCENT_PRIMARY);
        reservationTable.setSelectionForeground(ModernColors.TEXT_PRIMARY);
        reservationTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reservationTable.setRowHeight(35);
        reservationTable.setShowGrid(false);
        reservationTable.setIntercellSpacing(new Dimension(0, 0));

        reservationTable.getTableHeader().setBackground(ModernColors.BACKGROUND_LIGHT);
        reservationTable.getTableHeader().setForeground(ModernColors.TEXT_PRIMARY);
        reservationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < reservationTable.getColumnCount(); i++) {
            reservationTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer priceRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.RIGHT);

                try {
                    double price = Double.parseDouble(value.toString());
                    setText(String.format("Rp %,.0f", price));
                } catch (NumberFormatException e) {
                    setText(value.toString());
                }

                return c;
            }
        };

        reservationTable.getColumnModel().getColumn(6).setCellRenderer(priceRenderer);

        reservationTable.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        reservationTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Customer
        reservationTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Movie
        reservationTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Date & Time
        reservationTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Seat
        reservationTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Studio
        reservationTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Price

        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(ModernColors.BACKGROUND_DARK);

        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel statsLabel = new JLabel("Total Reservations: 0");
        statsLabel.setForeground(ModernColors.TEXT_SECONDARY);
        statsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        JLabel totalLabel = new JLabel("Total Revenue: Rp 0");
        totalLabel.setForeground(ModernColors.SUCCESS);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setHorizontalAlignment(JLabel.RIGHT);

        statsPanel.add(statsLabel, BorderLayout.WEST);
        statsPanel.add(totalLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);

        displayAllReservations();
    }

    private void setupListeners() {

    }

    private void updateStatistics(int rowCount, double totalRevenue) {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                Component[] panelComponents = panel.getComponents();

                for (Component c : panelComponents) {
                    if (c instanceof JLabel) {
                        JLabel label = (JLabel) c;
                        if (label.getText().startsWith("Total Reservations:")) {
                            label.setText("Total Reservations: " + rowCount);
                        } else if (label.getText().startsWith("Total Revenue:")) {
                            label.setText(String.format("Total Revenue: Rp %,.0f", totalRevenue));
                        }
                    }
                }
            }
        }
    }

    public void refreshReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        displayAllReservations();
    }

    private void displayAllReservations() {
        tableModel.setRowCount(0);

        double totalRevenue = 0;

        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);

            Object[] rowData = {
                    i + 1,
                    reservation.getCustomer().getUsername(),
                    reservation.getSchedule().getMovie().getTitle(),
                    reservation.getSchedule().getDateTime(),
                    reservation.getSeatNumber(),
                    reservation.getSchedule().getStudioName(),
                    reservation.getTotalPrice()
            };

            tableModel.addRow(rowData);
            totalRevenue += reservation.getTotalPrice();
        }

        updateStatistics(reservations.size(), totalRevenue);
    }

}