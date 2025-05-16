package gui.panels;

import gui.frames.MainFrame;
import gui.themes.ModernColors;
import gui.components.RoundedButton;
import gui.utils.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class MainPanel extends JPanel {
    private MainFrame parentFrame;
    private JLabel logoLabel;
    private JLabel welcomeLabel;
    private RoundedButton loginButton;
    private RoundedButton registerButton;

    public MainPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(ModernColors.BACKGROUND_DARK);
    }

    private void initComponents() {

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(ModernColors.BACKGROUND_DARK);
        contentPanel.setLayout(new GridBagLayout());

        logoLabel = new JLabel("SineTix");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        logoLabel.setForeground(ModernColors.TEXT_PRIMARY);

        welcomeLabel = new JLabel("Welcome to the Cinema Reservation System");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeLabel.setForeground(ModernColors.TEXT_SECONDARY);

        loginButton = new RoundedButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(200, 50));

        registerButton = new RoundedButton("Register");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(200, 50));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showLoginPanel();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showRegisterPanel();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.insets = new Insets(0, 0, 20, 0);
        contentPanel.add(logoLabel, gbc);

        gbc.insets = new Insets(0, 0, 50, 0);
        contentPanel.add(welcomeLabel, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        contentPanel.add(loginButton, gbc);
        contentPanel.add(registerButton, gbc);

        add(contentPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(ModernColors.BACKGROUND_DARK);

        JLabel footerLabel = new JLabel("© 2023 SINETIX - Cinema Ticketing Application");
        footerLabel.setForeground(ModernColors.TEXT_TERTIARY);
        footerPanel.add(footerLabel);

        add(footerPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}
