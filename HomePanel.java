import java.awt.*;
import javax.swing.*;

// Home Panel
class HomePanel extends JPanel {
    public HomePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(20, 20, 20, 20);
        
        JLabel welcome = new JLabel("Welcome to Serenity Suites");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcome.setForeground(SerenitySuitesHMS.PRIMARY);
        g.gridx = 0; g.gridy = 0;
        content.add(welcome, g);
        
        JLabel subtitle = new JLabel("Experience Luxury and Comfort");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(Color.GRAY);
        g.gridy++;
        content.add(subtitle, g);
        
        g.gridy++;
        content.add(Box.createVerticalStrut(30), g);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setPreferredSize(new Dimension(900, 150));
        
        statsPanel.add(createStatCard("150+", "Luxury Rooms", "🛏️"));
        statsPanel.add(createStatCard("500+", "Happy Guests", "😊"));
        statsPanel.add(createStatCard("24/7", "Room Service", "🔔"));
        
        g.gridy++;
        content.add(statsPanel, g);
        
        add(content, BorderLayout.CENTER);
    }
    
    private JPanel createStatCard(String value, String label, String icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(SerenitySuitesHMS.PRIMARY);
        
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textLabel.setForeground(Color.GRAY);
        
        JPanel center = new JPanel(new GridLayout(3, 1));
        center.setBackground(new Color(248, 249, 250));
        center.add(iconLabel);
        center.add(valueLabel);
        center.add(textLabel);
        
        card.add(center, BorderLayout.CENTER);
        return card;
    }
}

// Rooms Panel
class RoomsPanel extends JPanel {
    private JPanel container;
    
    public RoomsPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel title = new JLabel("Our Rooms");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        add(title, BorderLayout.NORTH);
        
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        refreshRooms();
        
        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
    }
    
    public void refreshRooms() {
        container.removeAll();
        
        // Group rooms by type
        String[] types = {"Standard", "Deluxe", "Executive", "Suite", "Family"};
        for (String type : types) {
            java.util.List<Room> roomsByType = SerenitySuitesHMS.rooms.stream()
                .filter(r -> r.getType().equals(type))
                .sorted((r1, r2) -> Integer.compare(r1.getRoomNumber(), r2.getRoomNumber()))  // Sort by room number
                .collect(java.util.stream.Collectors.toList());
            
            if (!roomsByType.isEmpty()) {
                container.add(createCollapsibleRoomBox(type, roomsByType));
                container.add(Box.createVerticalStrut(20));
            }
        }
        
        container.revalidate();
        container.repaint();
    }
    
    private JPanel createCollapsibleRoomBox(String type, java.util.List<Room> rooms) {
        JPanel boxContainer = new JPanel(new BorderLayout());
        boxContainer.setBackground(Color.WHITE);
        boxContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        boxContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        
        JPanel header = new JPanel(new BorderLayout(10, 0));
        header.setBackground(SerenitySuitesHMS.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(Integer.MAX_VALUE, 55));
        
        JLabel typeLabel = new JLabel(type + " Rooms (" + rooms.size() + ")");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        typeLabel.setForeground(Color.WHITE);
        
        header.add(typeLabel, BorderLayout.WEST);
        
        boxContainer.add(header, BorderLayout.NORTH);
        
        JPanel roomsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        roomsPanel.setBackground(Color.WHITE);
        roomsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        for (Room r : rooms) {
            roomsPanel.add(createRoomCard(r));
        }
        
        boxContainer.add(roomsPanel, BorderLayout.CENTER);
        
        return boxContainer;
    }
    
    private JPanel createRoomCard(Room r) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JPanel info = new JPanel(new GridLayout(6, 1, 5, 5));
        info.setBackground(new Color(248, 249, 250));
        
        JLabel room = new JLabel("Room " + r.getRoomNumber());
        room.setFont(new Font("Segoe UI", Font.BOLD, 22));
        room.setForeground(SerenitySuitesHMS.PRIMARY);
        info.add(room);
        
        info.add(new JLabel("Type: " + r.getName()));
        info.add(new JLabel("Category: " + r.getType()));
        info.add(new JLabel("Capacity: " + r.getCapacity() + " guests"));
        info.add(new JLabel("Price: ₱" + r.getPrice() + " per night"));
        
        JLabel status = new JLabel("Status: " + (r.isBooked() ? "Occupied" : "Available"));
        status.setFont(new Font("Segoe UI", Font.BOLD, 14));
        status.setForeground(r.isBooked() ? SerenitySuitesHMS.DANGER : SerenitySuitesHMS.ACCENT);
        info.add(status);
        
        card.add(info, BorderLayout.CENTER);
        return card;
    }
}

// Book Panel
class BookPanel extends JPanel {
    private SerenitySuitesHMS parent;
    private MainPanel mainPanel;
    private JTextField checkinField;
    private JTextField checkoutField;
    private JComboBox<String> roomTypeCombo;
    private JComboBox<String> checkinTimeCombo;
    private JComboBox<String> checkoutTimeCombo;
    private JSpinner guestsSpinner;
    private JPanel resultsPanel;
    
    public BookPanel(SerenitySuitesHMS parent, MainPanel mainPanel) {
        this.parent = parent;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel title = new JLabel("Book a Room");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        add(title, BorderLayout.NORTH);
        
        add(createBookingForm(), BorderLayout.CENTER);
    }
    
    public void resetForm() {
        checkinField.setText("");
        checkoutField.setText("");
        roomTypeCombo.setSelectedIndex(0);
        checkinTimeCombo.setSelectedIndex(0);
        checkoutTimeCombo.setSelectedIndex(checkoutTimeCombo.getItemCount() - 1);
        guestsSpinner.setValue(1);
        resultsPanel.removeAll();
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private JPanel createBookingForm() {
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(Color.WHITE);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        
        // Create results panel early so we can reference it
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        
        g.gridx = 0; g.gridy = 0;
        form.add(new JLabel("Room Type:"), g);
        g.gridx = 1;
        roomTypeCombo = new JComboBox<>(new String[]{"Standard", "Deluxe", "Executive", "Suite", "Family"});
        roomTypeCombo.setPreferredSize(new Dimension(300, 35));
        form.add(roomTypeCombo, g);
        
        g.gridx = 0; g.gridy++;
        form.add(new JLabel("Check-in Date:"), g);
        g.gridx = 1;
        JPanel checkinPanel = new JPanel(new BorderLayout(5, 0));
        checkinPanel.setBackground(Color.WHITE);
        checkinField = UIComponents.createStyledTextField();
        checkinField.setPreferredSize(new Dimension(250, 35));
        checkinField.setEditable(false);
        JButton checkinBtn = new JButton("📅");
        checkinBtn.setPreferredSize(new Dimension(45, 35));
        checkinBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        checkinBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkinBtn.addActionListener(e -> {
            String date = DatePickerDialog.showDatePicker(parent, "Select Check-in Date");
            if (date != null) {
                checkinField.setText(date);
            }
        });
        checkinPanel.add(checkinField, BorderLayout.CENTER);
        checkinPanel.add(checkinBtn, BorderLayout.EAST);
        form.add(checkinPanel, g);
        
        g.gridx = 0; g.gridy++;
        form.add(new JLabel("Check-out Date:"), g);
        g.gridx = 1;
        JPanel checkoutPanel = new JPanel(new BorderLayout(5, 0));
        checkoutPanel.setBackground(Color.WHITE);
        checkoutField = UIComponents.createStyledTextField();
        checkoutField.setPreferredSize(new Dimension(250, 35));
        checkoutField.setEditable(false);
        JButton checkoutBtn = new JButton("📅");
        checkoutBtn.setPreferredSize(new Dimension(45, 35));
        checkoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        checkoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkoutBtn.addActionListener(e -> {
            String date = DatePickerDialog.showDatePicker(parent, "Select Check-out Date");
            if (date != null) {
                checkoutField.setText(date);
            }
        });
        checkoutPanel.add(checkoutField, BorderLayout.CENTER);
        checkoutPanel.add(checkoutBtn, BorderLayout.EAST);
        form.add(checkoutPanel, g);
        
        g.gridx = 0; g.gridy++;
        form.add(new JLabel("Number of Guests:"), g);
        g.gridx = 1;
        guestsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
        guestsSpinner.setPreferredSize(new Dimension(300, 35));
        form.add(guestsSpinner, g);
        
        // Update guest limit based on room type selection
        roomTypeCombo.addActionListener(e -> {
            String selectedType = (String)roomTypeCombo.getSelectedItem();
            int maxGuests = 2; // Default for Standard, Deluxe, Executive
            if ("Suite".equals(selectedType)) {
                maxGuests = 6;
            } else if ("Family".equals(selectedType)) {
                maxGuests = 10;
            }
            int currentValue = (int)guestsSpinner.getValue();
            guestsSpinner.setModel(new SpinnerNumberModel(Math.min(currentValue, maxGuests), 1, maxGuests, 1));
        });
        
        g.gridx = 0; g.gridy++;
        form.add(new JLabel("Check-in Time:"), g);
        g.gridx = 1;
        checkinTimeCombo = new JComboBox<>(generateTimeSlots());
        checkinTimeCombo.setPreferredSize(new Dimension(300, 35));
        form.add(checkinTimeCombo, g);
        
        g.gridx = 0; g.gridy++;
        form.add(new JLabel("Check-out Time:"), g);
        g.gridx = 1;
        checkoutTimeCombo = new JComboBox<>(generateTimeSlots());
        checkoutTimeCombo.setSelectedIndex(checkoutTimeCombo.getItemCount() - 1);
        checkoutTimeCombo.setPreferredSize(new Dimension(300, 35));
        form.add(checkoutTimeCombo, g);
        
        g.gridx = 0; g.gridy++; g.gridwidth = 2;
        JButton search = UIComponents.createStyledButton("Search Available Rooms", SerenitySuitesHMS.PRIMARY);
        search.setPreferredSize(new Dimension(400, 45));
        form.add(search, g);
        
        main.add(form, BorderLayout.NORTH);
        
        search.addActionListener(e -> {
            resultsPanel.removeAll();
            String type = (String)roomTypeCombo.getSelectedItem();
            
            java.util.List<Room> availableRooms = SerenitySuitesHMS.rooms.stream()
                .filter(r -> !r.isBooked() && r.getType().equals(type))
                .collect(java.util.stream.Collectors.toList());
            
            if (availableRooms.isEmpty()) {
                JLabel noRooms = new JLabel("No available rooms for " + type + " type.");
                noRooms.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                noRooms.setForeground(Color.GRAY);
                noRooms.setHorizontalAlignment(SwingConstants.CENTER);
                resultsPanel.add(noRooms);
            } else {
                availableRooms.forEach(r -> {
                    resultsPanel.add(createRoomBookingCard(r, checkinField.getText(), 
                        checkoutField.getText(), (int)guestsSpinner.getValue(), 
                        (String)checkinTimeCombo.getSelectedItem(), (String)checkoutTimeCombo.getSelectedItem(), BookPanel.this));
                    resultsPanel.add(Box.createVerticalStrut(15));
                });
            }
            
            resultsPanel.revalidate();
            resultsPanel.repaint();
        });
        
        JScrollPane scroll = new JScrollPane(resultsPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        main.add(scroll, BorderLayout.CENTER);
        
        return main;
    }
    
    private String[] generateTimeSlots() {
        String[] slots = new String[48];
        int index = 0;
        for (int h = 0; h < 24; h++) {
            String ampm = h < 12 ? "AM" : "PM";
            int hour12 = h == 0 ? 12 : h > 12 ? h - 12 : h;
            slots[index++] = String.format("%d:00 %s", hour12, ampm);
            slots[index++] = String.format("%d:30 %s", hour12, ampm);
        }
        return slots;
    }
    
    private JPanel createRoomBookingCard(Room r, String ci, String co, int g, String ciTime, String coTime, BookPanel bookPanel) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        
        // Calculate total price based on number of nights
        double totalPrice = r.getPrice();
        long nights = 1;
        String priceDisplay = "Price: ₱" + String.format("%.2f", r.getPrice()) + " per night";
        
        if (ci != null && !ci.isEmpty() && co != null && !co.isEmpty()) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date checkinDate = sdf.parse(ci);
                java.util.Date checkoutDate = sdf.parse(co);
                nights = (checkoutDate.getTime() - checkinDate.getTime()) / (1000 * 60 * 60 * 24);
                if (nights <= 0) {
                    nights = 1;
                }
                totalPrice = r.getPrice() * nights;
                priceDisplay = "Price: ₱" + String.format("%.2f", r.getPrice()) + " × " + nights + " night" + (nights > 1 ? "s" : "") + " = ₱" + String.format("%.2f", totalPrice);
            } catch (Exception e) {
                // Use default price display if date parsing fails
            }
        }
        
        JPanel info = new JPanel(new GridLayout(4, 1, 5, 5));
        info.setBackground(new Color(248, 249, 250));
        
        JLabel room = new JLabel("Room " + r.getRoomNumber() + " - " + r.getName());
        room.setFont(new Font("Segoe UI", Font.BOLD, 18));
        room.setForeground(SerenitySuitesHMS.PRIMARY);
        info.add(room);
        info.add(new JLabel("Type: " + r.getType() + " | Capacity: " + r.getCapacity() + " guests"));
        
        JLabel priceLabel = new JLabel(priceDisplay);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceLabel.setForeground(SerenitySuitesHMS.PRIMARY);
        info.add(priceLabel);
        
        info.add(new JLabel("Status: Available"));
        
        card.add(info, BorderLayout.CENTER);
        
        JButton book = UIComponents.createStyledButton("Book Now", SerenitySuitesHMS.ACCENT);
        book.setPreferredSize(new Dimension(130, 45));
        final double finalTotalPrice = totalPrice;
        book.addActionListener(e -> BookingHandler.handleBooking(parent, r, ci, co, g, ciTime, coTime, finalTotalPrice, bookPanel, mainPanel));
        card.add(book, BorderLayout.EAST);
        
        return card;
    }
}

// Booking Handler
class BookingHandler {
    // Create and initialize all available offers
    private static java.util.List<Offer> initializeOffers() {
        java.util.List<Offer> offers = new java.util.ArrayList<>();
        offers.add(new Offer("Weekend Getaway", 20, "Book 2 nights, get 20% discount", "2+ nights"));
        offers.add(new Offer("Early Bird Special", 15, "Book 30 days in advance", "Book 30+ days ahead"));
        offers.add(new Offer("Extended Stay", 25, "Stay 7+ nights, save 25%", "7+ nights"));
        offers.add(new Offer("Honeymoon Package", 30, "Romantic suite with extras", "Suite only"));
        return offers;
    }
    
    public static void handleBooking(SerenitySuitesHMS parent, Room r, String ci, String co, int g, String ciTime, String coTime, double totalPrice, BookPanel bookPanel, MainPanel mainPanel) {
        if (ci == null || ci.isEmpty() || co == null || co.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Please select check-in and check-out dates.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate that checkout date is after check-in date
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date checkinDate = sdf.parse(ci);
            java.util.Date checkoutDate = sdf.parse(co);
            
            if (checkoutDate.before(checkinDate) || checkoutDate.equals(checkinDate)) {
                JOptionPane.showMessageDialog(parent, 
                    "Check-out date must be after check-in date.\n\n" +
                    "Check-in: " + ci + "\n" +
                    "Check-out: " + co + "\n\n" +
                    "Please select valid dates.", 
                    "Invalid Date Range", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(parent, "Invalid date format. Please use YYYY-MM-DD format.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (g > r.getCapacity()) {
            JOptionPane.showMessageDialog(parent, "Number of guests exceeds room capacity.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get payment methods from user's profile
        java.util.ArrayList<PaymentEntry> savedPayments = SerenitySuitesHMS.currentUser.getPaymentMethods();
        java.util.List<String> paymentMethods = new java.util.ArrayList<>();
        paymentMethods.add("Cash");
        
        // Add all saved payment methods to the list
        for (PaymentEntry payment : savedPayments) {
            paymentMethods.add(payment.getType() + " (" + payment.getMaskedNumber() + ")");
        }
        
        // Show payment method selection dialog
        String selectedPayment = (String) JOptionPane.showInputDialog(parent,
            "Select Payment Method:",
            "Payment Method",
            JOptionPane.QUESTION_MESSAGE,
            null,
            paymentMethods.toArray(),
            paymentMethods.get(0));
        
        if (selectedPayment == null) {
            return; // User cancelled
        }
        
        // Store just the payment type (without masked number for display)
        String paymentForBooking = selectedPayment;
        
        int id = SerenitySuitesHMS.bookings.size() + 1;
        Booking b = new Booking(id, SerenitySuitesHMS.currentUser.getUsername(), r.getRoomNumber(), ci, co, g, r.getPrice(), paymentForBooking, ciTime, coTime);
        
        // Check and apply any applicable offers
        java.util.List<Offer> offers = initializeOffers();
        java.util.List<String> applicableOffers = new java.util.ArrayList<>();
        
        for (Offer offer : offers) {
            if (offer.qualifies(b, r)) {
                applicableOffers.add(offer.getName() + " (" + offer.getDiscountPercent() + "% OFF)");
                // Apply the best offer (highest discount)
                if (b.getDiscountPercent() == 0 || offer.getDiscountPercent() > b.getDiscountPercent()) {
                    b.applyOffer(offer, r);
                }
            }
        }
        
        SerenitySuitesHMS.bookings.add(b);
        r.setBooked(true);
        
        // Build success message with offer details
        String message = "Booking successful! Booking ID: " + id + "\n";
        if (applicableOffers.size() > 0) {
            message += "Applicable Offers:\n";
            for (String offer : applicableOffers) {
                message += "  ✓ " + offer + "\n";
            }
            message += "\nBase Price: ₱" + String.format("%.2f", b.getPricePerNight() * b.getNumberOfNights());
            message += "\nDiscount: -₱" + String.format("%.2f", b.getDiscountAmount());
            message += "\nFinal Total: ₱" + String.format("%.2f", b.getTotalPrice());
        } else {
            message += "Total: ₱" + String.format("%.2f", b.getTotalPrice());
        }
        
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Refresh the RoomsPanel to show updated room status
        if (mainPanel != null && mainPanel.getRoomsPanel() != null) {
            mainPanel.getRoomsPanel().refreshRooms();
        }
        
        // Reset the booking form for a fresh booking
        bookPanel.resetForm();
        
        // If admin made the booking, refresh admin dashboard
        if (SerenitySuitesHMS.currentUser.getRole().equals("ADMIN")) {
            refreshAdminDashboard();
        }
    }
    
    private static void refreshAdminDashboard() {
        // Find the main frame and refresh admin panel
        for (java.awt.Window w : java.awt.Window.getWindows()) {
            if (w instanceof SerenitySuitesHMS) {
                SerenitySuitesHMS frame = (SerenitySuitesHMS) w;
                // The MainPanel will be in contentPanel, we need to access it
                javax.swing.JPanel mainPanel = (javax.swing.JPanel) frame.getContentPane().getComponent(0);
                for (java.awt.Component c : mainPanel.getComponents()) {
                    if (c.getClass().getName().equals("MainPanel")) {
                        MainPanel mp = (MainPanel) c;
                        AdminPanel ap = mp.getAdminPanel();
                        if (ap != null) {
                            ap.refreshDashboard();
                        }
                        break;
                    }
                }
            }
        }
    }
}

// Admin Panel
class AdminPanel extends JPanel {
    private JPanel content;
    private JTable table;
    private RoomsPanel roomsPanel;  // Reference to rooms panel to refresh when booking cancelled
    private JTabbedPane tabbedPane;
    private JPanel roomManagementPanel;
    
    public AdminPanel(RoomsPanel roomsPanel) {
        this.roomsPanel = roomsPanel;  // Store reference to rooms panel
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        add(title, BorderLayout.NORTH);
        
        // Create tabbed pane for different admin functions
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Bookings Tab
        JPanel bookingsPanel = new JPanel(new BorderLayout(20, 20));
        bookingsPanel.setBackground(Color.WHITE);
        bookingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(Color.WHITE);
        refreshDashboard();
        bookingsPanel.add(content, BorderLayout.CENTER);
        tabbedPane.addTab("Bookings", bookingsPanel);
        
        // Room Management Tab
        roomManagementPanel = new JPanel(new BorderLayout());
        roomManagementPanel.setBackground(Color.WHITE);
        refreshRoomManagement();
        tabbedPane.addTab("Manage Rooms", roomManagementPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void refreshRoomManagement() {
        roomManagementPanel.removeAll();
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel("Add New Room");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        mainPanel.add(title, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(500, 400));
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(12, 10, 12, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.weightx = 1;
        
        // Room Number
        g.gridx = 0; g.gridy = 0;
        JLabel roomNumLabel = new JLabel("Room Number:");
        roomNumLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(roomNumLabel, g);
        g.gridy++;
        JTextField roomNumField = UIComponents.createStyledTextField();
        formPanel.add(roomNumField, g);
        
        // Room Type
        g.gridy++;
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(roomTypeLabel, g);
        g.gridy++;
        JComboBox<String> roomTypeCombo = new JComboBox<>(new String[]{
            "Standard", "Deluxe", "Executive", "Suite", "Family"
        });
        roomTypeCombo.setPreferredSize(new Dimension(350, 40));
        formPanel.add(roomTypeCombo, g);
        
        // Room Name
        g.gridy++;
        JLabel roomNameLabel = new JLabel("Room Name:");
        roomNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(roomNameLabel, g);
        g.gridy++;
        JTextField roomNameField = UIComponents.createStyledTextField();
        formPanel.add(roomNameField, g);
        
        // Capacity
        g.gridy++;
        JLabel capacityLabel = new JLabel("Capacity:");
        capacityLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(capacityLabel, g);
        g.gridy++;
        JComboBox<Integer> capacityCombo = new JComboBox<>(new Integer[]{2, 4, 6, 8, 10});
        capacityCombo.setPreferredSize(new Dimension(350, 40));
        formPanel.add(capacityCombo, g);
        
        // Price per Night
        g.gridy++;
        JLabel priceLabel = new JLabel("Price per Night (₱):");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(priceLabel, g);
        g.gridy++;
        JTextField priceField = UIComponents.createStyledTextField();
        formPanel.add(priceField, g);
        
        // Add Room Button
        g.gridy++;
        g.insets = new Insets(25, 10, 12, 10);
        JButton addRoomBtn = UIComponents.createStyledButton("Add Room", SerenitySuitesHMS.ACCENT);
        addRoomBtn.setPreferredSize(new Dimension(150, 45));
        addRoomBtn.addActionListener(e -> {
            try {
                String roomNumStr = roomNumField.getText().trim();
                String roomType = (String) roomTypeCombo.getSelectedItem();
                String roomName = roomNameField.getText().trim();
                int capacity = (Integer) capacityCombo.getSelectedItem();
                String priceStr = priceField.getText().trim();
                
                if (roomNumStr.isEmpty() || roomName.isEmpty() || priceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int roomNum = Integer.parseInt(roomNumStr);
                double price = Double.parseDouble(priceStr);
                
                // Check if room number already exists
                boolean exists = SerenitySuitesHMS.rooms.stream()
                    .anyMatch(r -> r.getRoomNumber() == roomNum);
                
                if (exists) {
                    JOptionPane.showMessageDialog(mainPanel, "Room number " + roomNum + " already exists!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Add new room
                SerenitySuitesHMS.rooms.add(new Room(roomNum, roomName, price, capacity, roomType));
                
                JOptionPane.showMessageDialog(mainPanel, 
                    "✅ Room Added Successfully!\n\nRoom #" + roomNum + " (" + roomType + ")\nCapacity: " + capacity + " guests\nPrice: ₱" + price + "/night\n\nTotal Rooms: " + SerenitySuitesHMS.rooms.size(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                roomNumField.setText("");
                roomNameField.setText("");
                priceField.setText("");
                roomTypeCombo.setSelectedIndex(0);
                capacityCombo.setSelectedIndex(0);
                
                // Refresh rooms panel so new room appears
                roomsPanel.refreshRooms();
                
                // Refresh room management to update count and table
                refreshRoomManagement();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        formPanel.add(addRoomBtn, g);
        
        mainPanel.add(formPanel, BorderLayout.WEST);
        
        // Current Rooms Display (DYNAMIC)
        JPanel roomsDisplayPanel = new JPanel(new BorderLayout(10, 10));
        roomsDisplayPanel.setBackground(Color.WHITE);
        JLabel roomsTitle = new JLabel("Current Rooms (" + SerenitySuitesHMS.rooms.size() + ")");
        roomsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roomsTitle.setForeground(SerenitySuitesHMS.PRIMARY);
        roomsDisplayPanel.add(roomsTitle, BorderLayout.NORTH);
        
        // Rooms list (sorted by room number)
        String[] cols = {"Room #", "Name", "Type", "Capacity", "Price/Night", "Status"};
        Object[][] roomData = SerenitySuitesHMS.rooms.stream()
            .sorted((r1, r2) -> Integer.compare(r1.getRoomNumber(), r2.getRoomNumber()))  // Sort by room number
            .map(r -> new Object[]{
                r.getRoomNumber(),
                r.getName(),
                r.getType(),
                r.getCapacity() + " guests",
                "₱" + r.getPrice(),
                r.isBooked() ? "Occupied" : "Available"
            })
            .toArray(Object[][]::new);
        
        JTable roomsTable = new JTable(roomData, cols) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        roomsTable.setRowHeight(30);
        roomsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roomsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        roomsTable.getTableHeader().setBackground(new Color(248, 249, 250));
        
        // Status column color
        roomsTable.getColumn("Status").setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("Available".equals(value)) {
                    c.setForeground(new Color(40, 167, 69));
                } else if ("Occupied".equals(value)) {
                    c.setForeground(new Color(220, 53, 69));
                }
                return c;
            }
        });
        
        roomsDisplayPanel.add(new JScrollPane(roomsTable), BorderLayout.CENTER);
        mainPanel.add(roomsDisplayPanel, BorderLayout.CENTER);
        
        roomManagementPanel.add(mainPanel, BorderLayout.CENTER);
        roomManagementPanel.revalidate();
        roomManagementPanel.repaint();
    }
    
    public void refreshDashboard() {
        content.removeAll();
        
        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 0));
        stats.setBackground(Color.WHITE);
        
        int totalBookings = SerenitySuitesHMS.bookings.size();
        double totalRevenue = SerenitySuitesHMS.bookings.stream()
            .mapToDouble(Booking::getTotalPrice).sum();
        double occupancy = SerenitySuitesHMS.rooms.stream()
            .filter(Room::isBooked).count() * 100.0 / SerenitySuitesHMS.rooms.size();
        
        stats.add(createStatCard("Total Bookings", String.valueOf(totalBookings)));
        stats.add(createStatCard("Total Revenue", "₱" + String.format("%.2f", totalRevenue)));
        stats.add(createStatCard("Occupancy Rate", String.format("%.1f%%", occupancy)));
        
        content.add(stats, BorderLayout.NORTH);
        
        String[] cols = {"ID", "Guest", "Room", "Check-in", "Check-out", "Guests", "Total", "Payment", "Action"};
        Object[][] data = SerenitySuitesHMS.bookings.stream()
            .map(b -> new Object[]{
                b.getId(), b.getUsername(), b.getRoomNumber(), 
                b.getCheckin(), b.getCheckout(), b.getGuests(),
                "₱" + b.getTotalPrice(), b.getPayment(), "Cancel"
            })
            .toArray(Object[][]::new);
        
        table = new JTable(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only Action column is editable
            }
        };
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        
        // Add proper button renderer and editor for cancel action
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), this, roomsPanel));
        
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }
    
    private void cancelBooking(int bookingId) {
        Booking booking = SerenitySuitesHMS.bookings.stream()
            .filter(b -> b.getId() == bookingId)
            .findFirst().orElse(null);
        
        if (booking != null) {
            // Show confirmation dialog
            int response = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to cancel Reservation #" + bookingId + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (response == JOptionPane.YES_OPTION) {
                SerenitySuitesHMS.bookings.remove(booking);
                
                // Mark room as available
                SerenitySuitesHMS.rooms.stream()
                    .filter(r -> r.getRoomNumber() == booking.getRoomNumber())
                    .findFirst().ifPresent(r -> r.setBooked(false));
                
                JOptionPane.showMessageDialog(null, "Reservation #" + bookingId + " has been cancelled.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    
    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(SerenitySuitesHMS.PRIMARY);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 32));
        val.setForeground(Color.WHITE);
        val.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lbl.setForeground(Color.WHITE);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(val, BorderLayout.CENTER);
        card.add(lbl, BorderLayout.SOUTH);
        return card;
    }
}

// Date Picker Dialog
class DatePickerDialog extends JDialog {
    private String selectedDate = null;
    private int currentYear;
    private int currentMonth;
    private JLabel monthYearLabel;
    private JPanel calendarPanel;
    
    public DatePickerDialog(JFrame parent, String title) {
        super(parent, title, true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        currentYear = cal.get(java.util.Calendar.YEAR);
        currentMonth = cal.get(java.util.Calendar.MONTH);
        
        add(createHeader(), BorderLayout.NORTH);
        
        calendarPanel = new JPanel(new GridLayout(7, 7, 5, 5));
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        updateCalendar();
        add(calendarPanel, BorderLayout.CENTER);
        
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancel = UIComponents.createStyledButton("Cancel", new Color(108, 117, 125));
        cancel.addActionListener(e -> dispose());
        footer.add(cancel);
        add(footer, BorderLayout.SOUTH);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton prev = new JButton("◀");
        prev.setFont(new Font("Segoe UI", Font.BOLD, 16));
        prev.setFocusPainted(false);
        prev.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prev.addActionListener(e -> {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            updateCalendar();
        });
        
        JButton next = new JButton("▶");
        next.setFont(new Font("Segoe UI", Font.BOLD, 16));
        next.setFocusPainted(false);
        next.setCursor(new Cursor(Cursor.HAND_CURSOR));
        next.addActionListener(e -> {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            updateCalendar();
        });
        
        monthYearLabel = new JLabel();
        monthYearLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        monthYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateMonthYearLabel();
        
        header.add(prev, BorderLayout.WEST);
        header.add(monthYearLabel, BorderLayout.CENTER);
        header.add(next, BorderLayout.EAST);
        
        return header;
    }
    
    private void updateMonthYearLabel() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                          "July", "August", "September", "October", "November", "December"};
        monthYearLabel.setText(months[currentMonth] + " " + currentYear);
    }
    
    private void updateCalendar() {
        calendarPanel.removeAll();
        updateMonthYearLabel();
        
        // Add day headers (Sun, Mon, Tue, Wed, Thu, Fri, Sat)
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(SerenitySuitesHMS.PRIMARY);
            calendarPanel.add(label);
        }
        
        // Get calendar info for this month
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(currentYear, currentMonth, 1);
        int startDay = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;  // 0 = Sunday
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        
        // Add empty cells for days before month starts (to align correctly)
        for (int i = 0; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }
        
        // Add all days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            final int d = day;
            JButton dayBtn = new JButton(String.valueOf(day));
            dayBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            dayBtn.setFocusPainted(false);
            dayBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            dayBtn.setBackground(Color.WHITE);
            dayBtn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            
            dayBtn.addActionListener(e -> {
                selectedDate = String.format("%04d-%02d-%02d", currentYear, currentMonth + 1, d);
                dispose();
            });
            
            dayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    dayBtn.setBackground(SerenitySuitesHMS.PRIMARY);
                    dayBtn.setForeground(Color.WHITE);
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    dayBtn.setBackground(Color.WHITE);
                    dayBtn.setForeground(Color.BLACK);
                }
            });
            
            calendarPanel.add(dayBtn);
        }
        
        // Add empty cells for remaining space (after month ends)
        int totalCells = 7 + startDay + daysInMonth;  // 7 header + start empty + days
        int remainingCells = 49 - totalCells;  // 7x7 grid = 49 cells total
        for (int i = 0; i < remainingCells; i++) {
            calendarPanel.add(new JLabel(""));
        }
        
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
    
    public static String showDatePicker(JFrame parent, String title) {
        DatePickerDialog dialog = new DatePickerDialog(parent, title);
        dialog.setVisible(true);
        return dialog.selectedDate;
    }
}

// Reservation Panel
class ReservationPanel extends JPanel {
    private JScrollPane scrollPane;
    private JPanel contentPanel;
    
    public ReservationPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel title = new JLabel("My Reservations");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        add(title, BorderLayout.NORTH);
        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        updateReservations();
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void updateReservations() {
        contentPanel.removeAll();
        
        Object[][] data = getBookingsData();
        
        if (data.length == 0) {
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setBackground(Color.WHITE);
            
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
            messagePanel.setBackground(Color.WHITE);
            
            JLabel emptyIcon = new JLabel("📋");
            emptyIcon.setFont(new Font("Segoe UI", Font.PLAIN, 64));
            emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel emptyMsg = new JLabel("No reservations yet");
            emptyMsg.setFont(new Font("Segoe UI", Font.BOLD, 20));
            emptyMsg.setForeground(Color.GRAY);
            emptyMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel emptySubMsg = new JLabel("Book a room to see your reservations here");
            emptySubMsg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptySubMsg.setForeground(Color.GRAY);
            emptySubMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            messagePanel.add(emptyIcon);
            messagePanel.add(Box.createVerticalStrut(10));
            messagePanel.add(emptyMsg);
            messagePanel.add(Box.createVerticalStrut(5));
            messagePanel.add(emptySubMsg);
            
            emptyPanel.add(messagePanel);
            contentPanel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            String[] cols = {"Booking ID", "Room", "Check-in", "Check-out", "Guests", "Total", "Payment"};
            JTable table = new JTable(data, cols);
            table.setRowHeight(35);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
            table.getTableHeader().setBackground(new Color(248, 249, 250));
            table.setSelectionBackground(new Color(102, 126, 234, 50));
            
            scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));
            contentPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private Object[][] getBookingsData() {
        return SerenitySuitesHMS.bookings.stream()
            .filter(b -> b.getUsername().equals(SerenitySuitesHMS.currentUser.getUsername()))
            .map(b -> new Object[]{
                b.getId(), 
                "Room " + b.getRoomNumber(), 
                b.getCheckin() + " " + b.getCheckinTime(), 
                b.getCheckout() + " " + b.getCheckoutTime(), 
                b.getGuests(), 
                "₱" + String.format("%.2f", b.getTotalPrice()), 
                b.getPayment()
            })
            .toArray(Object[][]::new);
    }
    
    public void refreshReservations() {
        updateReservations();
    }
}

// Events Panel
class EventsPanel extends JPanel {
    public EventsPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel title = new JLabel("Upcoming Events");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        add(title, BorderLayout.NORTH);
        
        JPanel events = new JPanel(new GridLayout(0, 1, 0, 20));
        events.setBackground(Color.WHITE);
        
        events.add(createEventCard("Summer Gala", "June 15, 2025", "Join us for an elegant evening"));
        events.add(createEventCard("Wine Tasting", "June 22, 2025", "Exclusive wine collection showcase"));
        events.add(createEventCard("Chef's Table", "July 1, 2025", "Experience fine dining at its best"));
        
        add(new JScrollPane(events), BorderLayout.CENTER);
    }
    
    private JPanel createEventCard(String name, String date, String desc) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
        info.setBackground(new Color(248, 249, 250));
        
        JLabel title = new JLabel(name);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        info.add(title);
        info.add(new JLabel("📅 " + date));
        info.add(new JLabel(desc));
        
        card.add(info, BorderLayout.CENTER);
        return card;
    }
}

// Offers Panel
class OffersPanel extends JPanel {
    public OffersPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel title = new JLabel("Special Offers");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        add(title, BorderLayout.NORTH);
        
        JPanel offers = new JPanel(new GridLayout(0, 2, 20, 20));
        offers.setBackground(Color.WHITE);
        
        offers.add(createOfferCard("Weekend Getaway", "20% OFF", "Book 2 nights, get 20% discount"));
        offers.add(createOfferCard("Early Bird Special", "15% OFF", "Book 30 days in advance"));
        offers.add(createOfferCard("Extended Stay", "25% OFF", "Stay 7+ nights, save 25%"));
        offers.add(createOfferCard("Honeymoon Package", "30% OFF", "Romantic suite with extras"));
        
        add(new JScrollPane(offers), BorderLayout.CENTER);
    }
    
    private JPanel createOfferCard(String name, String discount, String desc) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SerenitySuitesHMS.ACCENT, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
        info.setBackground(new Color(248, 249, 250));
        
        JLabel title = new JLabel(name);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(SerenitySuitesHMS.PRIMARY);
        info.add(title);
        
        JLabel disc = new JLabel(discount);
        disc.setFont(new Font("Segoe UI", Font.BOLD, 24));
        disc.setForeground(SerenitySuitesHMS.ACCENT);
        info.add(disc);
        
        info.add(new JLabel(desc));
        
        card.add(info, BorderLayout.CENTER);
        return card;
    }
}
