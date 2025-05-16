package gui.frames;

import gui.themes.ModernColors;
import gui.panels.MainPanel;
import gui.panels.LoginPanel;
import gui.panels.RegisterPanel;
import gui.panels.AdminPanel;
import gui.panels.CustomerPanel;
import gui.utils.UIManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import user.User;
import user.Admin;
import user.Customer;
import movie.Movie;
import reservation.Reservation;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private MainPanel mainPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private AdminPanel adminPanel;
    private CustomerPanel customerPanel;

    private List<User> users;
    private List<Movie> movies;
    private List<Reservation> reservations;
    private User currentUser;

    public MainFrame(List<User> users, List<Movie> movies, List<Reservation> reservations) {
        this.users = users;
        this.movies = movies;
        this.reservations = reservations;

        setupFrame();
        initComponents();
        setupListeners();
    }

    private void setupFrame() {
        setTitle("SineTix - Cinema Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus look and feel not available, using default");
        }

        UIManager.applyDarkThemeOverrides();

        java.net.URL iconURL = getClass().getResource("/images/app_icon.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());

        }

    }

    private void initComponents() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(ModernColors.BACKGROUND_DARK);

        mainPanel = new MainPanel(this);
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);

        contentPanel.add(mainPanel, "main");
        contentPanel.add(loginPanel, "login");
        contentPanel.add(registerPanel, "register");

        setContentPane(contentPanel);

        cardLayout.show(contentPanel, "main");
    }

    private void setupListeners() {

    }

    public void showMainPanel() {
        cardLayout.show(contentPanel, "main");
    }

    public void showLoginPanel() {
        cardLayout.show(contentPanel, "login");
    }

    public void showRegisterPanel() {
        cardLayout.show(contentPanel, "register");
    }

    public void showAdminPanel(Admin admin) {
        if (adminPanel == null) {
            adminPanel = new AdminPanel(this, admin, movies, reservations);
            contentPanel.add(adminPanel, "admin");
        } else {
            adminPanel.refreshData();
        }
        currentUser = admin;
        cardLayout.show(contentPanel, "admin");
    }

    public void showCustomerPanel(Customer customer) {
        if (customerPanel != null) {
            contentPanel.remove(customerPanel);
        }

        customerPanel = new CustomerPanel(this, customer, movies, reservations);
        contentPanel.add(customerPanel, "customer");
        currentUser = customer;
        cardLayout.show(contentPanel, "customer");
    }

    public boolean performLogin(String username, String password) {
        for (User user : users) {
            if (user.login(username, password)) {
                if (user instanceof Admin) {
                    showAdminPanel((Admin) user);
                } else if (user instanceof Customer) {
                    showCustomerPanel((Customer) user);
                }
                return true;
            }
        }
        return false;
    }

    public boolean performRegister(String username, String password) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        Customer newCustomer = new Customer(username, password);
        users.add(newCustomer);
        return true;
    }

    public void logout() {
        currentUser = null;
        showMainPanel();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
