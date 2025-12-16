// BUTTON EDITOR
// Custom editor for handling cancel button clicks in JTable cells
// When a user clicks the Cancel button, this editor:
// 1. Shows a confirmation dialog ("Are you sure?")
// 2. If confirmed, removes the booking and marks the room as available
// 3. Refreshes both admin dashboard AND rooms panel to show updated data immediately

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private AdminPanel adminPanel;   // Reference to admin panel to refresh after cancellation
    private RoomsPanel roomsPanel;   // Reference to rooms panel to update room availability

    public ButtonEditor(JCheckBox checkBox, AdminPanel adminPanel, RoomsPanel roomsPanel) {
        super(checkBox);
        this.adminPanel = adminPanel;
        this.roomsPanel = roomsPanel;  // Store reference to rooms panel
        
        // Create and style the button
        button = new JButton("Cancel");
        button.setOpaque(true);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(220, 53, 69));  // Red danger color
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add action listener to handle button click
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();  // Stop editing this cell
            }
        });
    }

    // This method is called when the user clicks the button in a table cell
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "Cancel" : value.toString();
        button.setText(label);
        isPushed = true;

        // Get the booking ID from the first column (column 0 = ID)
        int bookingId = (Integer) table.getValueAt(row, 0);
        
        // Show confirmation dialog before canceling
        int response = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(table),
            "Are you sure you want to cancel Reservation #" + bookingId + "?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
 
        // If user clicked YES, proceed with cancellation
        if (response == JOptionPane.YES_OPTION) {
            // Find the booking with matching ID and remove it
            for (Booking b : SerenitySuitesHMS.bookings) {
                if (b.getId() == bookingId) {
                    SerenitySuitesHMS.bookings.remove(b);  // Remove from bookings list
                    
                    // Mark the room as available (not booked)
                    for (Room r : SerenitySuitesHMS.rooms) {
                        if (r.getRoomNumber() == b.getRoomNumber()) {
                            r.setBooked(false);  // Room is now AVAILABLE
                            break;
                        }
                    }
                    
                    // Show success message
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(table),
                        "Reservation #" + bookingId + " has been cancelled.\nRoom " + b.getRoomNumber() + " is now AVAILABLE.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the admin dashboard to show updated bookings and occupancy rate
                    if (adminPanel != null) {
                        adminPanel.refreshDashboard();
                    }
                    
                    // Refresh the rooms panel to show room as available immediately
                    if (roomsPanel != null) {
                        roomsPanel.refreshRooms();
                    }
                    break;
                }
            }
        }

        return button;
    }

    // Return the value of this cell
    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            isPushed = false;
        }
        return label;
    }

    // Stop editing when cell editing is done
    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
