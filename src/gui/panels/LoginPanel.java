package gui.panels;

import gui.frames.MainFrame;
import gui.themes.ModernColors;
import gui.components.RoundedButton;
import gui.components.CustomTextField;
import gui.utils.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private MainFrame parentFrame;

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel messageLabel;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private RoundedButton loginButton;
    private RoundedButton backButton;

    public LoginPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        setupPanel();
        initComponents();
        setupListeners();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(ModernColors.BACKGROUND_DARK);
    }

    private void initComponents() {

        JPanel loginFormPanel = new JPanel(new GridBagLayout());
        loginFormPanel.setBackground(ModernColors.BACKGROUND_DARK);

        titleLabel = new JLabel("Login to SineTix");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(ModernColors.TEXT_SECONDARY);
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        usernameField = new JTextField(20);
        usernameField.setBackground(ModernColors.BACKGROUND_LIGHT);
        usernameField.setForeground(ModernColors.TEXT_PRIMARY);
        usernameField.setCaretColor(ModernColors.TEXT_PRIMARY);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(ModernColors.TEXT_SECONDARY);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passwordField = new JPasswordField(20);
        passwordField.setBackground(ModernColors.BACKGROUND_LIGHT);
        passwordField.setForeground(ModernColors.TEXT_PRIMARY);
        passwordField.setCaretColor(ModernColors.TEXT_PRIMARY);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(ModernColors.ERROR);
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        loginButton = new RoundedButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 40));

        backButton = new RoundedButton("Back", ModernColors.BACKGROUND_LIGHT,
                ModernColors.BACKGROUND_MEDIUM, ModernColors.TEXT_PRIMARY);
        backButton.setPreferredSize(new Dimension(100, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 25, 0);

        loginFormPanel.add(titleLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        loginFormPanel.add(usernameLabel, gbc);
        loginFormPanel.add(usernameField, gbc);

        gbc.insets = new Insets(15, 0, 5, 0);
        loginFormPanel.add(passwordLabel, gbc);
        gbc.insets = new Insets(5, 0, 15, 0);
        loginFormPanel.add(passwordField, gbc);

        loginFormPanel.add(messageLabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 5, 0);

        loginFormPanel.add(loginButton, gbc);

        gbc.insets = new Insets(10, 0, 0, 0);
        loginFormPanel.add(backButton, gbc);

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(ModernColors.BACKGROUND_DARK);
        containerPanel.add(loginFormPanel);

        add(containerPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                parentFrame.showMainPanel();
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username and password cannot be empty");
            return;
        }

        boolean loginSuccess = parentFrame.performLogin(username, password);

        if (!loginSuccess) {
            messageLabel.setText("Invalid username or password");
            passwordField.setText("");
        } else {
            clearFields();
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText(" ");
    }
}
