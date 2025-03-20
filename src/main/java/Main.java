import Views.ViewMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Main {
    private static Image appIcon; // Made global so that every window gets the same image

    public static void main(String[] args) {
        System.out.println("Launching Fitness Tracker...");
        // Load image
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(
                Main.class.getResource("/images/generic-app-icon.png"),
                "Image not found!"
        ));

        appIcon = icon.getImage();  // Store image
        applyGlobalFrameIcon();     // Apply image to all new frames
        SwingUtilities.invokeLater(ViewMain::new); // Creates the homepage window safely on the EDT since Swing is not thread-safe.
    }

    // Toolkit is part of AWT and provides access to listeners and window settings
    private static void applyGlobalFrameIcon() {
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> { // AWT globally listens for window events
            if (event.getID() == WindowEvent.WINDOW_OPENED) {                // Checks if window opens
                Window window = ((WindowEvent)event).getWindow();            // Get the window that opened
                if (window instanceof JFrame) {                              // Checking if window is JFrame
                    ((JFrame) window).setIconImage(appIcon);                 // Applies icon to all JFrames
                }
            }
        }, AWTEvent.WINDOW_EVENT_MASK); // Only listens for window events
    }
}

