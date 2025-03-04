package Views;

import javax.swing.*;

public class ViewStatistics extends JFrame {
    public ViewStatistics() {
        setTitle("Statistics");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Statistics View", SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewStatistics();
            }
        });
    }
}

