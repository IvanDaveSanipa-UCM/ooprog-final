// SERENITY SUITES HOTEL MANAGEMENT SYSTEM
// Main Application Class
// This is the entry point of the Hotel Management System
// It handles the main frame setup, UI initialization, and panel switching
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class SerenitySuitesHMS extends JFrame {
    // Color constants used throughout the application for consistent styling
    static final Color PRIMARY = new Color(102, 126, 234);      // Blue - used for headers and main elements
    static final Color SECONDARY = new Color(118, 75, 162);    // Purple - used for gradients
    static final Color ACCENT = new Color(40, 167, 69);        // Green - used for positive actions (Book, Register)
    static final Color DANGER = new Color(220, 53, 69);        // Red - used for negative actions (Cancel, Logout)
    static final Color SIDEBAR = new Color(240, 242, 245);     // Light gray - sidebar background
    
    // Shared data structures accessible across all panels
    static java.util.List<User> users = new ArrayList<>();          // Stores all registered users
    static java.util.List<Room> rooms = new ArrayList<>();          // Stores all hotel rooms
    static java.util.List<Booking> bookings = new ArrayList<>();    // Stores all bookings/reservations
    static User currentUser;                                          // Currently logged-in user
    
    // UI Components
    private JPanel mainPanel;           // Container for all panels
    private CardLayout cardLayout;      // Layout manager for switching between panels (Login, Register, Main)
    
    // Constructor - initializes the application
    public SerenitySuitesHMS() {
        DataInitializer.initializeData(users, rooms);  // Load sample data (users, rooms)
        setupUI();                                       // Setup the main UI
    }
    
    // Setup the main frame and UI
    private void setupUI() {
        setTitle("Serenity Suites - Hotel Management System");
        setSize(1400, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Add login and register panels initially
        mainPanel.add(new LoginPanel(this), "LOGIN");
        mainPanel.add(new RegisterPanel(this), "REGISTER");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");  // Show login panel first
    }
    
    // Method to switch between panels (Login, Register, Main)
    public void showPanel(String panelName) {
        if (panelName.equals("MAIN")) {
            // Special handling for MAIN panel - rebuild it with all sub-panels
            mainPanel.removeAll();
            mainPanel.add(new LoginPanel(this), "LOGIN");
            mainPanel.add(new RegisterPanel(this), "REGISTER");
            mainPanel.add(new MainPanel(this), "MAIN");  // MainPanel contains sidebar + home/rooms/book/etc
            cardLayout.show(mainPanel, "MAIN");
            mainPanel.revalidate();
            mainPanel.repaint();
        } else {
            cardLayout.show(mainPanel, panelName);  // Switch to specified panel
        }
    }
    
    // Main method - entry point of the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new SerenitySuitesHMS().setVisible(true);
        });
    }
}

// DATA INITIALIZER
// Loads sample/initial data when the application starts
// This includes creating default users and populating room inventory
class DataInitializer {
    public static void initializeData(java.util.List<User> users, java.util.List<Room> rooms) {
        // Create default users for testing
        users.add(new User("admin", "admin123", "ADMIN", "admin@serenitysuites.com", "09123456789"));
        users.add(new User("guest1", "guest123", "GUEST", "guest@email.com", "09234567890"));
        users.add(new User("staff1", "staff123", "STAFF", "staff@serenitysuites.com", "09345678901"));
        
        // Create 5 types of rooms with different capacities and prices
        
        // Standard Rooms (1-10): 2-person capacity, ₱1500 per night
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i, "Standard", 1500, 2, "Standard"));
        }
        
        // Deluxe Rooms (101-110): 2-person capacity, ₱2500 per night
        for (int i = 101; i <= 110; i++) {
            rooms.add(new Room(i, "Deluxe Single", 2500, 2, "Deluxe"));
        }
        
        // Executive Rooms (201-210): 2-person capacity, ₱3500 per night
        for (int i = 201; i <= 210; i++) {
            rooms.add(new Room(i, "Executive Double", 3500, 2, "Executive"));
        }
        
        // Presidential Suite (301-310): 6-person capacity, ₱8500 per night
        for (int i = 301; i <= 310; i++) {
            rooms.add(new Room(i, "Presidential Suite", 8500, 6, "Suite"));
        }
        
        // Family Room (401-410): 10-person capacity, ₱5000 per night
        for (int i = 401; i <= 410; i++) {
            rooms.add(new Room(i, "Family Room", 5000, 10, "Family"));
        }
    }
}

// Login Panel
class LoginPanel extends JPanel {
    private SerenitySuitesHMS parent;
    
    public LoginPanel(SerenitySuitesHMS parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        add(UIComponents.createGradientHeader("🏨 Serenity Suites", "Luxury & Comfort Await You"), BorderLayout.NORTH);
        add(createLoginFormWithImage(), BorderLayout.CENTER);
    }
    
    private JPanel createLoginFormWithImage() {
        JPanel container = new JPanel(new GridLayout(1, 2, 0, 0));
        container.setBackground(Color.WHITE);
        
        // Left side - Image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(240, 242, 245));
        
        JPanel imgWrapper = new JPanel(new GridBagLayout());
        imgWrapper.setBackground(new Color(240, 242, 245));
        
        JLabel imgText = new JLabel("Welcome to Serenity Suites");
        imgText.setFont(new Font("Segoe UI", Font.BOLD, 32));
        imgText.setForeground(SerenitySuitesHMS.PRIMARY);
        imgText.setHorizontalAlignment(SwingConstants.CENTER);
        
        imgWrapper.add(imgText);
        imagePanel.add(imgWrapper, BorderLayout.CENTER);
        container.add(imagePanel);
        
        // Right side - Form
        container.add(createLoginForm());
        
        return container;
    }
    
    private JPanel createLoginForm() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)));
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("Welcome!");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        form.add(title, g);
        
        JLabel subtitle = new JLabel("Sign in to continue");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        g.gridy++;
        form.add(subtitle, g);
        
        g.gridy++; g.gridwidth = 1;
        form.add(Box.createVerticalStrut(20), g);
        
        g.gridy++;
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g.gridx = 0; g.gridwidth = 2;
        form.add(userLabel, g);
        
        g.gridy++;
        JTextField user = UIComponents.createStyledTextField();
        form.add(user, g);
        
        g.gridy++;
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        form.add(passLabel, g);
        
        g.gridy++;
        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setBackground(Color.WHITE);
        passPanel.setPreferredSize(new Dimension(350, 40));
        JPasswordField pass = UIComponents.createStyledPasswordField();
        pass.setPreferredSize(new Dimension(305, 40));
        JButton showPass = new JButton("👁");
        showPass.setPreferredSize(new Dimension(45, 40));
        showPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        showPass.setFocusPainted(false);
        showPass.setBorderPainted(false);
        showPass.setBackground(new Color(240, 240, 240));
        showPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showPass.setToolTipText("Show/Hide Password");
        showPass.addActionListener(e -> {
            if (pass.getEchoChar() == (char)0) {
                pass.setEchoChar('•');
                showPass.setText("👁");
                showPass.setToolTipText("Show Password");
            } else {
                pass.setEchoChar((char)0);
                showPass.setText("🔒");
                showPass.setToolTipText("Hide Password");
            }
        });
        passPanel.add(pass, BorderLayout.CENTER);
        passPanel.add(showPass, BorderLayout.EAST);
        form.add(passPanel, g);
        
        g.gridy++;
        JLabel typeLabel = new JLabel("Login As");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        form.add(typeLabel, g);
        
        g.gridy++;
        JComboBox<String> type = new JComboBox<>(new String[]{"GUEST", "ADMIN"});
        type.setPreferredSize(new Dimension(350, 40));
        type.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(type, g);
        
        g.gridy++;
        form.add(Box.createVerticalStrut(10), g);
        
        g.gridy++;
        JButton login = UIComponents.createStyledButton("Sign In", SerenitySuitesHMS.PRIMARY);
        login.setPreferredSize(new Dimension(350, 45));
        login.addActionListener(e -> LoginHandler.handleLogin(parent, user.getText(), 
            new String(pass.getPassword()), (String)type.getSelectedItem()));
        form.add(login, g);
        
        g.gridy++;
        JButton forgotPass = new JButton("Forgot Password?");
        forgotPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        forgotPass.setForeground(SerenitySuitesHMS.PRIMARY);
        forgotPass.setBorderPainted(false);
        forgotPass.setContentAreaFilled(false);
        forgotPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPass.addActionListener(e -> showForgotPasswordDialog(parent, user));
        form.add(forgotPass, g);
        
        g.gridy++;
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        registerPanel.setBackground(Color.WHITE);
        JLabel noAccount = new JLabel("Don't have an account?");
        noAccount.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JButton registerLink = new JButton("Register here");
        registerLink.setFont(new Font("Segoe UI", Font.BOLD, 13));
        registerLink.setForeground(SerenitySuitesHMS.PRIMARY);
        registerLink.setBorderPainted(false);
        registerLink.setContentAreaFilled(false);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.addActionListener(e -> parent.showPanel("REGISTER"));
        registerPanel.add(noAccount);
        registerPanel.add(registerLink);
        form.add(registerPanel, g);
        
        container.add(form);
        return container;
    }
    
    private static void showForgotPasswordDialog(SerenitySuitesHMS parent, JTextField userField) {
        JDialog dialog = new JDialog(parent, "Reset Password", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridwidth = 2;
        
        JLabel title = new JLabel("Forgot Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        g.gridy = 0;
        panel.add(title, g);
        
        JLabel desc = new JLabel("Enter a valid email address");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(Color.GRAY);
        g.gridy++;
        panel.add(desc, g);
        
        g.gridy++;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(emailLabel, g);
        
        g.gridy++;
        JTextField emailField = UIComponents.createStyledTextField();
        panel.add(emailField, g);
        
        g.gridy++;
        g.gridwidth = 1;
        JButton continueBtn = UIComponents.createStyledButton("Continue", SerenitySuitesHMS.PRIMARY);
        continueBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter your email address!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            User user = SerenitySuitesHMS.users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst().orElse(null);
            
            if (user != null) {
                userField.setText(user.getUsername());
                dialog.dispose();
                showResetPasswordDialog(parent, user);
            } else {
                JOptionPane.showMessageDialog(dialog, "Email not found in our system!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(continueBtn, g);
        
        g.gridx = 1;
        JButton cancel = UIComponents.createStyledButton("Cancel", new Color(108, 117, 125));
        cancel.addActionListener(e -> dialog.dispose());
        panel.add(cancel, g);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private static void showResetPasswordDialog(SerenitySuitesHMS parent, User user) {
        JDialog dialog = new JDialog(parent, "Set New Password", true);
        dialog.setSize(500, 380);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridwidth = 2;
        
        JLabel title = new JLabel("Set New Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        g.gridy = 0;
        panel.add(title, g);
        
        JLabel desc = new JLabel("for account: " + user.getUsername());
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(Color.GRAY);
        g.gridy++;
        panel.add(desc, g);
        
        g.gridy++;
        JLabel newPassLabel = new JLabel("New Password:");
        newPassLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(newPassLabel, g);
        
        g.gridy++;
        JPanel newPassPanel = new JPanel(new BorderLayout());
        newPassPanel.setBackground(Color.WHITE);
        JPasswordField newPass = UIComponents.createStyledPasswordField();
        newPass.setPreferredSize(new Dimension(305, 40));
        JButton showNewPass = new JButton("👁");
        showNewPass.setPreferredSize(new Dimension(45, 40));
        showNewPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        showNewPass.setFocusPainted(false);
        showNewPass.setBorderPainted(false);
        showNewPass.setBackground(new Color(240, 240, 240));
        showNewPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showNewPass.addActionListener(e -> {
            if (newPass.getEchoChar() == (char)0) {
                newPass.setEchoChar('•');
                showNewPass.setText("👁");
            } else {
                newPass.setEchoChar((char)0);
                showNewPass.setText("🔒");
            }
        });
        newPassPanel.add(newPass, BorderLayout.CENTER);
        newPassPanel.add(showNewPass, BorderLayout.EAST);
        g.gridwidth = 2;
        panel.add(newPassPanel, g);
        
        g.gridy++;
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(confirmLabel, g);
        
        g.gridy++;
        JPanel confirmPassPanel = new JPanel(new BorderLayout());
        confirmPassPanel.setBackground(Color.WHITE);
        JPasswordField confirmPass = UIComponents.createStyledPasswordField();
        confirmPass.setPreferredSize(new Dimension(305, 40));
        JButton showConfirmPass = new JButton("👁");
        showConfirmPass.setPreferredSize(new Dimension(45, 40));
        showConfirmPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        showConfirmPass.setFocusPainted(false);
        showConfirmPass.setBorderPainted(false);
        showConfirmPass.setBackground(new Color(240, 240, 240));
        showConfirmPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showConfirmPass.addActionListener(e -> {
            if (confirmPass.getEchoChar() == (char)0) {
                confirmPass.setEchoChar('•');
                showConfirmPass.setText("👁");
            } else {
                confirmPass.setEchoChar((char)0);
                showConfirmPass.setText("🔒");
            }
        });
        confirmPassPanel.add(confirmPass, BorderLayout.CENTER);
        confirmPassPanel.add(showConfirmPass, BorderLayout.EAST);
        panel.add(confirmPassPanel, g);
        
        g.gridy++;
        g.gridwidth = 1;
        JButton ok = UIComponents.createStyledButton("OK", SerenitySuitesHMS.PRIMARY);
        ok.addActionListener(e -> {
            String np = new String(newPass.getPassword());
            String cp = new String(confirmPass.getPassword());
            
            if (np.isEmpty() || cp.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!np.equals(cp)) {
                JOptionPane.showMessageDialog(dialog, "Passwords do not match!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            user.setPassword(np);
            JOptionPane.showMessageDialog(dialog, 
                "✅ Password reset successful!\n\nPlease log in with your new password.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        panel.add(ok, g);
        
        g.gridx = 1;
        JButton back = UIComponents.createStyledButton("Back", new Color(108, 117, 125));
        back.addActionListener(e -> dialog.dispose());
        panel.add(back, g);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}
class RegisterPanel extends JPanel {
    private SerenitySuitesHMS parent;
    
    public RegisterPanel(SerenitySuitesHMS parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        add(UIComponents.createGradientHeader("🏨 Serenity Suites", "Create Your Account"), BorderLayout.NORTH);
        add(createRegisterForm(), BorderLayout.CENTER);
    }
    
    private JPanel createRegisterForm() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)));
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        form.add(title, g);
        
        JLabel subtitle = new JLabel("Join Serenity Suites today");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        g.gridy++;
        form.add(subtitle, g);
        
        g.gridy++; g.gridwidth = 1;
        form.add(Box.createVerticalStrut(20), g);
        
        g.gridy++; g.gridwidth = 2;
        form.add(createFieldLabel("Username"), g);
        g.gridy++;
        JTextField user = UIComponents.createStyledTextField();
        form.add(user, g);
        
        g.gridy++;
        form.add(createFieldLabel("Email Address"), g);
        g.gridy++;
        JTextField email = UIComponents.createStyledTextField();
        form.add(email, g);
        g.gridy++;
        form.add(createFieldLabel("Phone Number"), g);
        g.gridy++;
        JTextField phone = UIComponents.createStyledTextField();
        // Phone number validation - only numbers, max 11 digits
        ((javax.swing.text.AbstractDocument)phone.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= 11 && string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
                if (fb.getDocument().getLength() - length + text.length() <= 11 && text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        form.add(phone, g);
        
        g.gridy++;
        form.add(createFieldLabel("Password"), g);
        g.gridy++;
        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setBackground(Color.WHITE);
        JPasswordField pass = UIComponents.createStyledPasswordField();
        JButton showPass = new JButton("👁");
        showPass.setPreferredSize(new Dimension(45, 40));
        showPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        showPass.setFocusPainted(false);
        showPass.setBorderPainted(false);
        showPass.setBackground(Color.WHITE);
        showPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showPass.addActionListener(e -> {
            if (pass.getEchoChar() == (char)0) {
                pass.setEchoChar('•');
                showPass.setText("👁");
            } else {
                pass.setEchoChar((char)0);
                showPass.setText("🔒");
            }
        });
        passPanel.add(pass, BorderLayout.CENTER);
        passPanel.add(showPass, BorderLayout.EAST);
        form.add(passPanel, g);
        
        g.gridy++;
        form.add(createFieldLabel("Confirm Password"), g);
        g.gridy++;
        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setBackground(Color.WHITE);
        JPasswordField confirmPass = UIComponents.createStyledPasswordField();
        JButton showConfirm = new JButton("👁");
        showConfirm.setPreferredSize(new Dimension(45, 40));
        showConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        showConfirm.setFocusPainted(false);
        showConfirm.setBorderPainted(false);
        showConfirm.setBackground(Color.WHITE);
        showConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showConfirm.addActionListener(e -> {
            if (confirmPass.getEchoChar() == (char)0) {
                confirmPass.setEchoChar('•');
                showConfirm.setText("👁");
            } else {
                confirmPass.setEchoChar((char)0);
                showConfirm.setText("🔒");
            }
        });
        confirmPanel.add(confirmPass, BorderLayout.CENTER);
        confirmPanel.add(showConfirm, BorderLayout.EAST);
        form.add(confirmPanel, g);
        
        g.gridy++;
        form.add(createFieldLabel("Account Type"), g);
        g.gridy++;
        JComboBox<String> type = new JComboBox<>(new String[]{"GUEST"});
        type.setPreferredSize(new Dimension(350, 40));
        type.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(type, g);
        
        g.gridy++;
        form.add(Box.createVerticalStrut(10), g);
        
        g.gridy++;
        JButton register = UIComponents.createStyledButton("Create Account", SerenitySuitesHMS.ACCENT);
        register.setPreferredSize(new Dimension(350, 45));
        register.addActionListener(e -> {
            RegisterHandler.handleRegister(parent, user.getText(), email.getText(), 
                phone.getText(), new String(pass.getPassword()), 
                new String(confirmPass.getPassword()), (String)type.getSelectedItem());
        });
        form.add(register, g);
        
        g.gridy++;
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        loginPanel.setBackground(Color.WHITE);
        JLabel hasAccount = new JLabel("Already have an account?");
        hasAccount.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JButton loginLink = new JButton("Sign in here");
        loginLink.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginLink.setForeground(SerenitySuitesHMS.PRIMARY);
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addActionListener(e -> parent.showPanel("LOGIN"));
        loginPanel.add(hasAccount);
        loginPanel.add(loginLink);
        form.add(loginPanel, g);
        
        container.add(form);
        return container;
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return label;
    }
}

// Main Panel with Sidebar
class MainPanel extends JPanel {
    private SerenitySuitesHMS parent;
    private CardLayout contentLayout;
    private JPanel contentPanel;
    private JButton activeButton;
    private ReservationPanel reservationPanel;
    private AdminPanel adminPanel;
    private RoomsPanel roomsPanel;
    
    public MainPanel(SerenitySuitesHMS parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        
        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(Color.WHITE);
        
        reservationPanel = new ReservationPanel();
        roomsPanel = new RoomsPanel();
        adminPanel = new AdminPanel(roomsPanel);  // Pass roomsPanel to AdminPanel so it can refresh when booking is cancelled
        
        contentPanel.add(new HomePanel(), "HOME");
        contentPanel.add(roomsPanel, "ROOMS");
        contentPanel.add(new BookPanel(parent, this), "BOOK");
        contentPanel.add(reservationPanel, "RESERVATION");
        contentPanel.add(new EventsPanel(), "EVENTS");
        contentPanel.add(new OffersPanel(), "OFFERS");
        contentPanel.add(new ProfilePanel(), "PROFILE");
        
        if (SerenitySuitesHMS.currentUser.getRole().equals("ADMIN") || 
            SerenitySuitesHMS.currentUser.getRole().equals("STAFF")) {
            contentPanel.add(adminPanel, "ADMIN");
        }
        
        add(contentPanel, BorderLayout.CENTER);
        contentLayout.show(contentPanel, "HOME");
    }
    
    public AdminPanel getAdminPanel() {
        return adminPanel;
    }
    
    public RoomsPanel getRoomsPanel() {
        return roomsPanel;
    }
    
    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(255, 255, 255));
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        bar.setPreferredSize(new Dimension(0, 70));
        
        JLabel logo = new JLabel("🏨 Serenity Suites");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(SerenitySuitesHMS.PRIMARY);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        bar.add(logo, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        rightPanel.setBackground(Color.WHITE);
        
        JButton profile = UIComponents.createStyledButton("Profile", new Color(108, 117, 125));
        profile.setPreferredSize(new Dimension(100, 40));
        profile.addActionListener(e -> switchPanel("PROFILE", null));
        
        JButton about = UIComponents.createStyledButton("About", new Color(108, 117, 125));
        about.setPreferredSize(new Dimension(100, 40));
        about.addActionListener(e -> JOptionPane.showMessageDialog(parent, 
            "Serenity Suites Hotel Management System\nVersion 1.0\n\nDeveloped for luxury hospitality management",
            "About", JOptionPane.INFORMATION_MESSAGE));
        
        JButton contact = UIComponents.createStyledButton("Contact", new Color(108, 117, 125));
        contact.setPreferredSize(new Dimension(100, 40));
        contact.addActionListener(e -> JOptionPane.showMessageDialog(parent,
            "Contact Us:\nPhone: 09123456789\nEmail: info@serenitysuites.com\nAddress: Cebu City, Philippines",
            "Contact", JOptionPane.INFORMATION_MESSAGE));
        
        rightPanel.add(profile);
        rightPanel.add(about);
        rightPanel.add(contact);
        bar.add(rightPanel, BorderLayout.EAST);
        
        return bar;
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SerenitySuitesHMS.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));
        
        sidebar.add(Box.createVerticalStrut(20));
        
        JButton home = createSidebarButton("Home", "HOME");
        JButton rooms = createSidebarButton("Rooms", "ROOMS");
        JButton book = createSidebarButton("Book", "BOOK");
        JButton reservation = createSidebarButton("Reservation", "RESERVATION");
        
        reservation.addActionListener(e -> {
            reservationPanel.refreshReservations();
            switchPanel("RESERVATION", reservation);
        });
        
        JButton events = createSidebarButton("Events", "EVENTS");
        JButton offers = createSidebarButton("Offers", "OFFERS");
        
        sidebar.add(home);
        sidebar.add(rooms);
        sidebar.add(book);
        sidebar.add(reservation);
        sidebar.add(events);
        sidebar.add(offers);
        
        if (SerenitySuitesHMS.currentUser.getRole().equals("ADMIN") || 
            SerenitySuitesHMS.currentUser.getRole().equals("STAFF")) {
            sidebar.add(Box.createVerticalStrut(20));
            JLabel adminLabel = new JLabel("  ADMIN");
            adminLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
            adminLabel.setForeground(Color.GRAY);
            sidebar.add(adminLabel);
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(createSidebarButton("Dashboard", "ADMIN"));
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logout.setForeground(Color.WHITE);
        logout.setBackground(SerenitySuitesHMS.DANGER);
        logout.setFocusPainted(false);
        logout.setBorderPainted(false);
        logout.setHorizontalAlignment(SwingConstants.LEFT);
        logout.setPreferredSize(new Dimension(220, 45));
        logout.setMaximumSize(new Dimension(220, 45));
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        logout.addActionListener(e -> {
            SerenitySuitesHMS.currentUser = null;
            parent.showPanel("LOGIN");
        });
        sidebar.add(logout);
        sidebar.add(Box.createVerticalStrut(20));
        
        activeButton = home;
        home.setBackground(Color.WHITE);
        
        return sidebar;
    }
    
    private JButton createSidebarButton(String text, String panel) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(new Color(60, 60, 60));
        btn.setBackground(SerenitySuitesHMS.SIDEBAR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setMaximumSize(new Dimension(220, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        btn.addActionListener(e -> switchPanel(panel, btn));
        
        return btn;
    }
    
    private void switchPanel(String panel, JButton btn) {
        contentLayout.show(contentPanel, panel);
        if (btn != null && activeButton != null) {
            activeButton.setBackground(SerenitySuitesHMS.SIDEBAR);
            btn.setBackground(Color.WHITE);
            activeButton = btn;
        }
    }
}

// Login Handler
class LoginHandler {
    public static void handleLogin(SerenitySuitesHMS parent, String u, String p, String t) {
        User usr = SerenitySuitesHMS.users.stream()
            .filter(x -> x.getUsername().equals(u) && x.getPassword().equals(p) && x.getRole().equals(t))
            .findFirst().orElse(null);
        
        if (usr != null) {
            SerenitySuitesHMS.currentUser = usr;
            parent.showPanel("MAIN");
        } else {
            JOptionPane.showMessageDialog(parent, "Invalid username, password, or user type!", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Register Handler
class RegisterHandler {
    public static void handleRegister(SerenitySuitesHMS parent, String u, String e, 
                                     String ph, String p, String cp, String t) {
        if (u.isEmpty() || e.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Please fill all required fields!", 
                "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate phone number - must be exactly 11 digits
        if (ph.isEmpty() || ph.length() != 11) {
            JOptionPane.showMessageDialog(parent, "Phone number must be exactly 11 digits!", 
                "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate email format
        if (!isValidEmail(e)) {
            JOptionPane.showMessageDialog(parent, "Please enter a valid email address!", 
                "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!p.equals(cp)) {
            JOptionPane.showMessageDialog(parent, "Passwords do not match!", 
                "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean exists = SerenitySuitesHMS.users.stream()
            .anyMatch(x -> x.getUsername().equals(u));
        
        if (exists) {
            JOptionPane.showMessageDialog(parent, "Username already exists!", 
                "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        SerenitySuitesHMS.users.add(new User(u, p, t, e, ph));
        JOptionPane.showMessageDialog(parent, 
            "✅ Registration Successful!\n\nYour account has been created.\nPlease sign in with your credentials.", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        parent.showPanel("LOGIN");
    }
    
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}

// UI Components Utility
class UIComponents {
    public static JPanel createGradientHeader(String main, String sub) {
        JPanel h = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, SerenitySuitesHMS.PRIMARY, 
                    getWidth(), getHeight(), SerenitySuitesHMS.SECONDARY);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        h.setPreferredSize(new Dimension(0, 120));
        
        JLabel m = new JLabel(main);
        m.setFont(new Font("Segoe UI", Font.BOLD, 42));
        m.setForeground(Color.WHITE);
        m.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel s = new JLabel(sub);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        s.setForeground(Color.WHITE);
        s.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel c = new JPanel(new GridLayout(2, 1));
        c.setOpaque(false);
        c.add(m);
        c.add(s);
        
        h.add(c, BorderLayout.CENTER);
        return h;
    }
    
    public static JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
    
    public static JTextField createStyledTextField() {
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(350, 40));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return f;
    }
    
    public static JPasswordField createStyledPasswordField() {
        JPasswordField f = new JPasswordField();
        f.setPreferredSize(new Dimension(350, 40));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return f;
    }
}

// USER CLASS
// Represents a user in the system (Guest, Admin, or Staff)
// Stores user credentials and payment information
class User {
    private String username, password, role, email, phone;
    // Multiple payment methods support - stores list of payment entries
    private java.util.ArrayList<PaymentEntry> paymentMethods;
    // Keep these for backward compatibility
    private String paymentMethod;
    private String paymentNumber;
    
    public User(String u, String p, String r, String e, String ph) {
        username = u; password = p; role = r; email = e; phone = ph;
        paymentMethods = new java.util.ArrayList<>();
        // default payment method is Cash with empty number
        paymentMethod = "Cash";
        paymentNumber = "";
    }
    
    // Getters for user information
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentNumber() { return paymentNumber; }
    public java.util.ArrayList<PaymentEntry> getPaymentMethods() { return paymentMethods; }
    
    // Setters for user information
    public void setPassword(String p) { password = p; }
    public void setEmail(String e) { email = e; }
    public void setPhone(String p) { phone = p; }
    public void setPaymentMethod(String m) { paymentMethod = m; }
    public void setPaymentNumber(String n) { paymentNumber = n; }
    
    // Add new payment method to the list
    public void addPaymentMethod(String type, String number, String name) {
        paymentMethods.add(new PaymentEntry(type, number, name));
    }
    
    // Remove payment method from the list
    public void removePaymentMethod(int index) {
        if (index >= 0 && index < paymentMethods.size()) {
            paymentMethods.remove(index);
        }
    }
}

// ROOM CLASS
// Represents a hotel room with its properties
// Tracks room details and availability status
class Room {
    private int roomNumber, capacity;
    private String name, type;
    private double price;
    private boolean booked;
    
    public Room(int num, String n, double p, int c, String t) {
        roomNumber = num; name = n; price = p; capacity = c; type = t; booked = false;
    }
    
    // Getters for room information
    public int getRoomNumber() { return roomNumber; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }
    public boolean isBooked() { return booked; }
    
    // Setter for room availability
    public void setBooked(boolean b) { booked = b; }
}

// BOOKING CLASS
// Represents a reservation/booking in the system
// Automatically calculates total price based on number of nights
// Supports promotional offers with automatic discount application
class Booking {
    private int id, roomNumber, guests;
    private String username, checkin, checkout, payment, checkinTime, checkoutTime;
    private double totalPrice;
    private double pricePerNight;
    private double discountAmount;      // Discount applied (in pesos)
    private int discountPercent;        // Discount percentage
    private String appliedOffer;        // Name of offer applied (if any)
    
    // Constructor - initializes booking and calculates total price
    public Booking(int i, String u, int r, String ci, String co, int g, double p, String pm, String cit, String cot) {
        id = i; username = u; roomNumber = r; checkin = ci; checkout = co; 
        guests = g; payment = pm; pricePerNight = p; checkinTime = cit; checkoutTime = cot;
        this.discountAmount = 0;
        this.discountPercent = 0;
        this.appliedOffer = "None";
        this.totalPrice = calculateTotalPrice();  // Calculate total based on dates
    }
    
    // Apply an offer/discount to this booking
    public void applyOffer(Offer offer, Room room) {
        if (offer.qualifies(this, room)) {
            this.discountPercent = offer.getDiscountPercent();
            this.appliedOffer = offer.getName();
            // Recalculate total with discount
            double basePrice = calculateBaseTotalPrice();
            this.discountAmount = basePrice * (discountPercent / 100.0);
            this.totalPrice = basePrice - discountAmount;
        }
    }
    
    // Calculate base total price WITHOUT discount
    private double calculateBaseTotalPrice() {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date checkinDate = sdf.parse(checkin);
            java.util.Date checkoutDate = sdf.parse(checkout);
            long nights = (checkoutDate.getTime() - checkinDate.getTime()) / (1000 * 60 * 60 * 24);
            if (nights <= 0) {
                nights = 1;
            }
            return pricePerNight * nights;
        } catch (Exception e) {
            return pricePerNight;
        }
    }
    
    // Calculate total price based on number of nights between check-in and check-out
    // This method parses the dates and calculates the difference in days
    private double calculateTotalPrice() {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date checkinDate = sdf.parse(checkin);
            java.util.Date checkoutDate = sdf.parse(checkout);
            long nights = (checkoutDate.getTime() - checkinDate.getTime()) / (1000 * 60 * 60 * 24);
            if (nights <= 0) {
                nights = 1; // Minimum 1 night
            }
            return pricePerNight * nights;  // Total = price per night × number of nights
        } catch (Exception e) {
            return pricePerNight; // Fallback to per-night price if parsing fails
        }
    }
    
    // Get number of nights from check-in and check-out dates
    public long getNumberOfNights() {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date checkinDate = sdf.parse(checkin);
            java.util.Date checkoutDate = sdf.parse(checkout);
            long nights = (checkoutDate.getTime() - checkinDate.getTime()) / (1000 * 60 * 60 * 24);
            return nights > 0 ? nights : 1;
        } catch (Exception e) {
            return 1; // Default to 1 night on error
        }
    }
    
    // Getters for booking information
    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getRoomNumber() { return roomNumber; }
    public String getCheckin() { return checkin; }
    public String getCheckout() { return checkout; }
    public String getCheckinTime() { return checkinTime; }
    public String getCheckoutTime() { return checkoutTime; }
    public int getGuests() { return guests; }
    public String getPayment() { return payment; }
    public double getTotalPrice() { return totalPrice; }
    public double getPricePerNight() { return pricePerNight; }
    public double getDiscountAmount() { return discountAmount; }
    public int getDiscountPercent() { return discountPercent; }
    public String getAppliedOffer() { return appliedOffer; }
}

// ==========================================
// OFFER CLASS
// ==========================================
// Represents promotional discounts available to guests
// Offers are automatically applied based on booking criteria
class Offer {
    private String name;
    private int discountPercent;
    private String description;
    private String criteria;  // Description of requirements to qualify
    
    public Offer(String name, int discountPercent, String description, String criteria) {
        this.name = name;
        this.discountPercent = discountPercent;
        this.description = description;
        this.criteria = criteria;
    }
    
    // Check if a booking qualifies for this offer
    public boolean qualifies(Booking booking, Room room) {
        long nights = booking.getNumberOfNights();
        
        // Weekend Getaway: 20% OFF for 2+ nights
        if (name.equals("Weekend Getaway")) {
            return nights >= 2;
        }
        
        // Early Bird Special: 15% OFF for bookings 30+ days in advance
        if (name.equals("Early Bird Special")) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date checkinDate = sdf.parse(booking.getCheckin());
                java.util.Date today = new java.util.Date();
                long daysInAdvance = (checkinDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);
                return daysInAdvance >= 30;
            } catch (Exception e) {
                return false;
            }
        }
        
        // Extended Stay: 25% OFF for 7+ nights
        if (name.equals("Extended Stay")) {
            return nights >= 7;
        }
        
        // Honeymoon Package: 30% OFF for Suite rooms only
        if (name.equals("Honeymoon Package")) {
            return room.getType().equals("Suite");
        }
        
        return false;
    }
    
    // Getters
    public String getName() { return name; }
    public int getDiscountPercent() { return discountPercent; }
    public String getDescription() { return description; }
    public String getCriteria() { return criteria; }
}

// PAYMENT ENTRY CLASS
// Stores a single payment method with type, number, and account name
class PaymentEntry {
    private String type;      // GCash, PayMaya, Credit Card, etc.
    private String number;    // Account/Card number
    private String name;      // Account name
    
    public PaymentEntry(String t, String n, String nm) {
        type = t;
        number = n;
        name = nm;
    }
    
    public String getType() { return type; }
    public String getNumber() { return number; }
    public String getName() { return name; }
    
    public String getMaskedNumber() {
        return number.length() > 4 ? 
            "**** " + number.substring(number.length() - 4) : 
            number;
    }
}