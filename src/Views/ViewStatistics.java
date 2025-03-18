package Views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewStatistics extends JFrame {

    public ViewStatistics() {
        setTitle("Fitness Tracker - Statistics");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        BarGraphPanel graphPanel = new BarGraphPanel(FitnessData.foodLog, FitnessData.workoutLog);
        add(graphPanel);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewStatistics());
    }

    class BarGraphPanel extends JPanel {
        private final ArrayList<FoodEntry> foodLog;
        private final ArrayList<WorkoutEntry> workoutLog;

        public BarGraphPanel(ArrayList<FoodEntry> foodLog, ArrayList<WorkoutEntry> workoutLog) {
            this.foodLog = foodLog;
            this.workoutLog = workoutLog;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            Map<String, Double> caloriesConsumedByDate = new HashMap<>();
            Map<String, Double> caloriesBurnedByDate = new HashMap<>();

            // Aggregate calories consumed by date
            for (FoodEntry entry : foodLog) {
                String date = entry.getDate();
                caloriesConsumedByDate.put(date,
                        caloriesConsumedByDate.getOrDefault(date, 0.0) + entry.getCalories());
            }

            // Aggregate calories burned by date
            for (WorkoutEntry entry : workoutLog) {
                String date = entry.getDate();
                caloriesBurnedByDate.put(date,
                        caloriesBurnedByDate.getOrDefault(date, 0.0) + entry.getCaloriesBurned());
            }

            double maxConsumed = caloriesConsumedByDate.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
            double maxBurned = caloriesBurnedByDate.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
            double maxCalories = Math.max(maxConsumed, maxBurned);
            double scale = maxCalories > 0 ? 200.0 / maxCalories : 1;

            g2d.setColor(Color.BLACK);
            g2d.drawLine(250, 600, 1250, 600); // X-axis
            g2d.drawLine(250, 600, 250, 150);  // Y-axis

            // Draw horizontal grid lines and Y-axis labels (numbers)
            for (int i = 0; i <= 3000; i += 500) {
                int y = 600 - (i * 450 / 3000);

                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(250, y, 1250, y);

                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(i), 210, y + 5);
            }

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("Calories Consumed vs. Calories Burned", 550, 120);

            int xPos = 300;
            int barWidth = 30;

            for (String date : caloriesConsumedByDate.keySet()) {
                int yConsumed = (int) (600 - caloriesConsumedByDate.get(date) * 450 / 3000);
                int yBurned = (int) (600 - caloriesBurnedByDate.getOrDefault(date, 0.0) * 450 / 3000);

                g2d.setColor(Color.RED);
                g2d.fillRect(xPos, yConsumed, barWidth, 600 - yConsumed);

                g2d.setColor(Color.BLUE);
                g2d.fillRect(xPos + barWidth + 10, yBurned, barWidth, 600 - yBurned);

                g2d.setColor(Color.BLACK);
                String shortDate = date.length() > 10 ? date.substring(0, 8) : date;
                g2d.drawString(shortDate, xPos, 630);

                xPos += 150;
            }
            // Legends
            g2d.setColor(Color.RED);
            g2d.drawString("Calories Consumed", 550, 670);
            g2d.setColor(Color.BLUE);
            g2d.drawString("Calories Burned", 750, 670);
        }
    }
}



