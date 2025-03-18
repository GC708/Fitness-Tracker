package Views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewStatistics extends JFrame {

    public ViewStatistics() {
        setTitle("Statistics - Calories Overview");
        setSize(500, 400);
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
            g2d.drawLine(50, 250, 450, 250); // X-axis
            g2d.drawLine(50, 250, 50, 50);   // Y-axis

            // Draw horizontal grid lines and Y-axis labels (numbers)
            for (int i = 50; i <= 250; i += 30) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(50, i, 450, i);   // Grid lines
                g2d.setColor(Color.BLACK);
                if (i != 250) {
                    g2d.drawString(String.valueOf(250 - i), 20, i + 5);  // Y-axis labels (numbers)
                }
            }

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Calories Consumed vs. Calories Burned", 120, 30);

            int xPos = 60;
            int barWidth = 20;

            // Iterate through the dates and draw bars
            for (String date : caloriesConsumedByDate.keySet()) {
                int yConsumed = (int) (250 - caloriesConsumedByDate.get(date) * scale);
                int yBurned = (int) (250 - caloriesBurnedByDate.getOrDefault(date, 0.0) * scale);

                g2d.setColor(Color.RED);
                g2d.fillRect(xPos, yConsumed, barWidth, 250 - yConsumed);

                g2d.setColor(Color.BLUE);
                g2d.fillRect(xPos + barWidth + 5, yBurned, barWidth, 250 - yBurned);

                g2d.setColor(Color.BLACK);
                String shortDate = date.length() > 10 ? date.substring(0, 10) : date;  // Limit to 10 characters
                g2d.drawString(shortDate, xPos, 270);

                xPos += 60;  // Space between bars
            }

            g2d.setColor(Color.RED);
            g2d.drawString("Calories Consumed", 300, 320);
            g2d.setColor(Color.BLUE);
            g2d.drawString("Calories Burned", 300, 300);
        }
    }
}



