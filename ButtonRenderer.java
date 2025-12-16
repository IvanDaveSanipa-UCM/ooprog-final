// BUTTON RENDERER
// Custom renderer for displaying cancel buttons in JTable cells
// This component renders a styled red "Cancel" button in the Admin Dashboard table
// It displays consistently for all rows without needing to recreate buttons

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    // Constructor - set up the button appearance
    public ButtonRenderer() {
        setOpaque(true);
        setText("Cancel");
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setForeground(Color.WHITE);                          // White text
        setBackground(new Color(220, 53, 69));              // Red background (danger color)
        setFocusPainted(false);                             // Remove focus border when clicked
        setBorderPainted(false);                            // No border for clean look
        setCursor(new Cursor(Cursor.HAND_CURSOR));          // Hand cursor to indicate it's clickable
        setHorizontalAlignment(SwingConstants.CENTER);      // Center the text
    }

    // Render method - called each time a cell needs to be rendered
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText("Cancel");  // Always show "Cancel" text
        return this;
    }
}
