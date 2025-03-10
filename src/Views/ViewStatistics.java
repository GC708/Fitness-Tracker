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

        // Use centralized data from FitnessData
        BarGraphPanel graphPanel = new BarGraphPanel(FitnessData.foodLog, FitnessData.workoutLog);
        add(graphPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewStatistics());
    }

    // Custom JPanel for drawing the bar graph
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

            // Data points: Mapping dates to calories consumed and burned
            Map<String, Double> caloriesConsumedByDate = new HashMap<>();
            Map<String, Double> caloriesBurnedByDate = new HashMap<>();

            // Calculate total calories consumed by date
            for (FoodEntry entry : foodLog) {
                caloriesConsumedByDate.put(entry.getDate(),
                        caloriesConsumedByDate.getOrDefault(entry.getDate(), 0.0) + entry.getCalories());
            }

            // Calculate total calories burned by date
            for (WorkoutEntry entry : workoutLog) {
                caloriesBurnedByDate.put(entry.getDate(),
                        caloriesBurnedByDate.getOrDefault(entry.getDate(), 0.0) + entry.getCaloriesBurned());
            }

            // Calculate max calories for dynamic scaling
            double maxConsumed = caloriesConsumedByDate.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
            double maxBurned = caloriesBurnedByDate.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
            double maxCalories = Math.max(maxConsumed, maxBurned);
            double scale = maxCalories > 0 ? 200.0 / maxCalories : 1; // Scale to 200px height

            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.drawLine(50, 250, 450, 250); // X-axis
            g2d.drawLine(50, 250, 50, 50);   // Y-axis

            // Draw bars for each date
            int xPos = 60;
            int barWidth = 20;
            for (String date : caloriesConsumedByDate.keySet()) {
                int yConsumed = (int) (250 - caloriesConsumedByDate.get(date) * scale);
                int yBurned = (int) (250 - caloriesBurnedByDate.getOrDefault(date, 0.0) * scale);

                // Draw consumed calories bar in RED
                g2d.setColor(Color.RED);
                g2d.fillRect(xPos, yConsumed, barWidth, 250 - yConsumed);

                // Draw burned calories bar in BLUE
                g2d.setColor(Color.BLUE);
                g2d.fillRect(xPos + barWidth + 5, yBurned, barWidth, 250 - yBurned);

                // Date labels (shortened to avoid overlap)
                g2d.setColor(Color.BLACK);
                String shortDate = date.substring(0, Math.min(6, date.length())); // e.g., "Mar 9"
                g2d.drawString(shortDate, xPos, 270);

                xPos += 60; // Adjusted spacing
            }

            // Legend
            g2d.setColor(Color.RED);
            g2d.drawString("Calories Consumed", 300, 60);
            g2d.setColor(Color.BLUE);
            g2d.drawString("Calories Burned", 300, 80);
        }
    }
}