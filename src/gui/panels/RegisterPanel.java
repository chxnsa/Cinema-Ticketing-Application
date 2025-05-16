package gui.panels;

import gui.frames.MainFrame;
import gui.themes.ModernColors;
import gui.components.RoundedButton;
import gui.utils.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {
    private MainFrame parentFrame;

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel messageLabel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private RoundedButton registerButton;
    private RoundedButton backButton;

    public RegisterPanel(MainFrame parentFrame) {
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

        JPanel registerFormPanel = new JPanel(new GridBagLayout());
        registerFormPanel.setBackground(ModernColors.BACKGROUND_DARK);

        titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(ModernColors.TEXT_PRIMARY);

        usernameLabel = new JLabel("Username (min. 5 characters)");
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

        passwordLabel = new JLabel("Password (min. 5 letters & 3 digits)");
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

        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setForeground(ModernColors.TEXT_SECONDARY);
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBackground(ModernColors.BACKGROUND_LIGHT);
        confirmPasswordField.setForeground(ModernColors.TEXT_PRIMARY);
        confirmPasswordField.setCaretColor(ModernColors.TEXT_PRIMARY);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernColors.BACKGROUND_MEDIUM),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(ModernColors.ERROR);
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        registerButton = new RoundedButton("Register");
        registerButton.setPreferredSize(new Dimension(200, 40));

        backButton = new RoundedButton("Back", ModernColors.BACKGROUND_LIGHT,
                ModernColors.BACKGROUND_MEDIUM, ModernColors.TEXT_PRIMARY);
        backButton.setPreferredSize(new Dimension(100, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 25, 0);

        registerFormPanel.add(titleLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        registerFormPanel.add(usernameLabel, gbc);
        registerFormPanel.add(usernameField, gbc);

        gbc.insets = new Insets(15, 0, 5, 0);
        registerFormPanel.add(passwordLabel, gbc);
        gbc.insets = new Insets(5, 0, 5, 0);
        registerFormPanel.add(passwordField, gbc);

        gbc.insets = new Insets(15, 0, 5, 0);
        registerFormPanel.add(confirmPasswordLabel, gbc);
        gbc.insets = new Insets(5, 0, 15, 0);
        registerFormPanel.add(confirmPasswordField, gbc);

        registerFormPanel.add(messageLabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 5, 0);

        registerFormPanel.add(registerButton, gbc);

        gbc.insets = new Insets(10, 0, 0, 0);
        registerFormPanel.add(backButton, gbc);

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(ModernColors.BACKGROUND_DARK);
        containerPanel.add(registerFormPanel);

        add(containerPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                parentFrame.showMainPanel();
            }
        });
    }

    private void performRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty()) {
            messageLabel.setText("Username cannot be empty");
            return;
        }

        if (username.length() < 5) {
            messageLabel.setText("Username must be at least 5 characters");
            return;
        }

        if (password.isEmpty()) {
            messageLabel.setText("Password cannot be empty");
            return;
        }

        int letters = 0, digits = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                letters++;
            } else if (Character.isDigit(c)) {
                digits++;
            }
        }

        if (letters < 5 || digits < 3) {
            messageLabel.setText("Password must have at least 5 letters and 3 digits");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match");
            return;
        }

        boolean registerSuccess = parentFrame.performRegister(username, password);

        if (!registerSuccess) {
            messageLabel.setText("Username already exists");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration successful. Please login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            parentFrame.showLoginPanel();
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        messageLabel.setText(" ");
    }
}
