import java.awt.*;
import javax.swing.*;

// Separate ProfilePanel implementation (handles payment method + number)
class ProfilePanel extends JPanel {
    private JPanel paymentDisplayPanel;
    
    public ProfilePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(Color.WHITE);
        scrollContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Title
        JLabel title = new JLabel("Account Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollContent.add(title);
        scrollContent.add(Box.createVerticalStrut(20));
        
        // Profile Information Section
        scrollContent.add(createProfileInfoSection());
        scrollContent.add(Box.createVerticalStrut(30));
        
        // Change Password Section
        scrollContent.add(createChangePasswordSection());
        scrollContent.add(Box.createVerticalStrut(30));
        
        // Payment Methods Section
        paymentDisplayPanel = new JPanel();
        paymentDisplayPanel.setLayout(new BoxLayout(paymentDisplayPanel, BoxLayout.Y_AXIS));
        paymentDisplayPanel.setBackground(Color.WHITE);
        refreshPaymentDisplay(paymentDisplayPanel);
        scrollContent.add(paymentDisplayPanel);
        scrollContent.add(Box.createVerticalGlue());
        
        JScrollPane scroll = new JScrollPane(scrollContent);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
    }
    
    private void refreshPaymentDisplay(JPanel displayPanel) {
        displayPanel.removeAll();
        displayPanel.add(createPaymentMethodsSection());
        displayPanel.revalidate();
        displayPanel.repaint();
    }
    
    private JPanel createProfileInfoSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
        section.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel sectionTitle = new JLabel("Profile Information");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(sectionTitle);
        section.add(Box.createVerticalStrut(10));
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 0, 10, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.weightx = 1;
        
        // Username
        g.gridx = 0; g.gridy = 0;
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(userLabel, g);
        g.gridy++;
        JTextField user = UIComponents.createStyledTextField();
        user.setText(SerenitySuitesHMS.currentUser.getUsername());
        user.setEnabled(false);
        form.add(user, g);
        
        // Email
        g.gridy++;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(emailLabel, g);
        g.gridy++;
        JTextField email = UIComponents.createStyledTextField();
        email.setText(SerenitySuitesHMS.currentUser.getEmail());
        form.add(email, g);
        
        // Phone
        g.gridy++;
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(phoneLabel, g);
        g.gridy++;
        JTextField phone = UIComponents.createStyledTextField();
        phone.setText(SerenitySuitesHMS.currentUser.getPhone());
        form.add(phone, g);
        
        section.add(form);
        return section;
    }
    
    private JPanel createChangePasswordSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
        section.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel sectionTitle = new JLabel("Change Password");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(sectionTitle);
        section.add(Box.createVerticalStrut(10));
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 0, 10, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.weightx = 1;
        
        // Current Password
        g.gridx = 0; g.gridy = 0;
        JLabel currentLabel = new JLabel("Current Password");
        currentLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(currentLabel, g);
        g.gridy++;
        g.gridwidth = 2;
        JPasswordField currentPass = UIComponents.createStyledPasswordField();
        JPanel currentPassPanel = createPasswordFieldWithToggle(currentPass);
        form.add(currentPassPanel, g);
        g.gridwidth = 1;
        
        // New Password
        g.gridy++;
        JLabel newLabel = new JLabel("New Password");
        newLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(newLabel, g);
        g.gridy++;
        g.gridwidth = 2;
        JPasswordField newPass = UIComponents.createStyledPasswordField();
        JPanel newPassPanel = createPasswordFieldWithToggle(newPass);
        form.add(newPassPanel, g);
        g.gridwidth = 1;
        
        // Confirm Password
        g.gridy++;
        JLabel confirmLabel = new JLabel("Confirm New Password");
        confirmLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(confirmLabel, g);
        g.gridy++;
        g.gridwidth = 2;
        JPasswordField confirmPass = UIComponents.createStyledPasswordField();
        JPanel confirmPassPanel = createPasswordFieldWithToggle(confirmPass);
        form.add(confirmPassPanel, g);
        g.gridwidth = 1;
        
        section.add(form);
        section.add(Box.createVerticalStrut(15));
        
        // Save Changes Button
        JButton saveBtn = UIComponents.createStyledButton("Save Changes", SerenitySuitesHMS.PRIMARY);
        saveBtn.setMaximumSize(new Dimension(150, 40));
        saveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveBtn.addActionListener(e -> {
            JTextField emailField = (JTextField) form.getComponent(5);
            JTextField phoneField = (JTextField) form.getComponent(9);
            String newEmail = emailField.getText();
            String newPhone = phoneField.getText();
            String np = new String(newPass.getPassword());
            String np2 = new String(confirmPass.getPassword());
            
            if (!np.isEmpty()) {
                if (!np.equals(np2)) {
                    JOptionPane.showMessageDialog(ProfilePanel.this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SerenitySuitesHMS.currentUser.setPassword(np);
            }
            
            SerenitySuitesHMS.currentUser.setEmail(newEmail);
            SerenitySuitesHMS.currentUser.setPhone(newPhone);
            
            JOptionPane.showMessageDialog(ProfilePanel.this, "Profile updated successfully!");
            currentPass.setText("");
            newPass.setText("");
            confirmPass.setText("");
        });
        section.add(saveBtn);
        
        return section;
    }
    
    private JPanel createPaymentMethodsSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
        section.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel sectionTitle = new JLabel("Payment Methods");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(sectionTitle);
        section.add(Box.createVerticalStrut(10));
        
        // Display all saved payment methods
        java.util.ArrayList<PaymentEntry> payments = SerenitySuitesHMS.currentUser.getPaymentMethods();
        
        if (payments.size() > 0) {
            JLabel savedTitle = new JLabel("Saved Payment Methods (" + payments.size() + "):");
            savedTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
            savedTitle.setForeground(SerenitySuitesHMS.PRIMARY);
            section.add(savedTitle);
            section.add(Box.createVerticalStrut(10));
            
            for (int i = 0; i < payments.size(); i++) {
                PaymentEntry entry = payments.get(i);
                JPanel paymentCard = new JPanel(new BorderLayout(10, 10));
                paymentCard.setBackground(new Color(248, 249, 250));
                paymentCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
                paymentCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
                
                JLabel methodLabel = new JLabel(entry.getType());
                methodLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                
                JLabel numberLabel = new JLabel(entry.getMaskedNumber());
                numberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                numberLabel.setForeground(Color.GRAY);
                
                JLabel nameLabel = new JLabel("Account: " + entry.getName());
                nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                nameLabel.setForeground(Color.GRAY);
                
                JPanel info = new JPanel(new GridLayout(3, 1));
                info.setBackground(new Color(248, 249, 250));
                info.add(methodLabel);
                info.add(numberLabel);
                info.add(nameLabel);
                paymentCard.add(info, BorderLayout.CENTER);
                
                // Remove button
                JButton removeBtn = new JButton("✕");
                removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
                removeBtn.setPreferredSize(new Dimension(45, 45));
                removeBtn.setForeground(SerenitySuitesHMS.DANGER);
                removeBtn.setFocusPainted(false);
                removeBtn.setBorderPainted(false);
                removeBtn.setBackground(Color.WHITE);
                removeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                final int index = i;
                removeBtn.addActionListener(e -> {
                    SerenitySuitesHMS.currentUser.removePaymentMethod(index);
                    refreshPaymentDisplay(paymentDisplayPanel);
                });
                paymentCard.add(removeBtn, BorderLayout.EAST);
                
                section.add(paymentCard);
                section.add(Box.createVerticalStrut(10));
            }
        } else {
            JLabel noPaymentLabel = new JLabel("No payment methods saved.");
            noPaymentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            noPaymentLabel.setForeground(Color.GRAY);
            section.add(noPaymentLabel);
            section.add(Box.createVerticalStrut(20));
        }
        
        section.add(Box.createVerticalStrut(20));
        
        // Add New Payment Method Section
        JLabel addTitle = new JLabel("Add New Payment Method");
        addTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        section.add(addTitle);
        section.add(Box.createVerticalStrut(10));
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 0, 10, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.weightx = 1;
        
        // Payment Type
        g.gridx = 0; g.gridy = 0;
        JLabel typeLabel = new JLabel("Payment Type");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(typeLabel, g);
        g.gridy++;
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"GCash", "PayMaya", "Credit Card", "Debit Card"});
        typeCombo.setPreferredSize(new Dimension(300, 35));
        form.add(typeCombo, g);
        
        // Account/Card Number
        g.gridy++;
        JLabel numberLabel = new JLabel("Account/Card Number");
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(numberLabel, g);
        g.gridy++;
        JTextField numberField = UIComponents.createStyledTextField();
        form.add(numberField, g);
        
        // Account Name
        g.gridy++;
        JLabel nameLabel = new JLabel("Account Name");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(nameLabel, g);
        g.gridy++;
        JTextField nameField = UIComponents.createStyledTextField();
        form.add(nameField, g);
        
        section.add(form);
        section.add(Box.createVerticalStrut(15));
        
        // Add Payment Method Button
        JButton addPaymentBtn = UIComponents.createStyledButton("Add Payment Method", SerenitySuitesHMS.ACCENT);
        addPaymentBtn.setMaximumSize(new Dimension(300, 40));
        addPaymentBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        addPaymentBtn.addActionListener(e -> {
            String method = (String) typeCombo.getSelectedItem();
            String number = numberField.getText().trim();
            String name = nameField.getText().trim();
            
            if (number.isEmpty()) {
                JOptionPane.showMessageDialog(ProfilePanel.this, "Please enter account/card number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(ProfilePanel.this, "Please enter account name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Add payment method to user's list
            SerenitySuitesHMS.currentUser.addPaymentMethod(method, number, name);
            
            JOptionPane.showMessageDialog(ProfilePanel.this, "Payment method added successfully!");
            numberField.setText("");
            nameField.setText("");
            typeCombo.setSelectedIndex(0);
            
            // Refresh the payment methods display
            refreshPaymentDisplay(paymentDisplayPanel);
        });
        section.add(addPaymentBtn);
        
        return section;
    }
    
    // Helper method to create a password field with show/hide toggle
    private JPanel createPasswordFieldWithToggle(JPasswordField passwordField) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(Color.WHITE);
        
        JButton toggleBtn = new JButton("👁️");
        toggleBtn.setPreferredSize(new Dimension(40, 35));
        toggleBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        toggleBtn.setFocusPainted(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Track visibility state
        boolean[] isVisible = {false};
        
        toggleBtn.addActionListener(e -> {
            isVisible[0] = !isVisible[0];
            if (isVisible[0]) {
                // Show password
                passwordField.setEchoChar((char) 0);
                toggleBtn.setText("🙈");
            } else {
                // Hide password
                passwordField.setEchoChar('•');
                toggleBtn.setText("👁️");
            }
        });
        
        panel.add(passwordField, BorderLayout.CENTER);
        panel.add(toggleBtn, BorderLayout.EAST);
        
        return panel;
    }
}
