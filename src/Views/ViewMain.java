package Views;

import javax.swing.*;
import java.awt.*;

public class ViewMain extends JFrame {

    public ViewMain() {
        setTitle("Fitness Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        // Load all data at startup
        FitnessData.loadAllData();

        JPanel recordPanel = new JPanel();
        recordPanel.setBorder(BorderFactory.createTitledBorder("Record"));
        JButton workoutButton = new JButton("Workout");
        JButton foodButton = new JButton("Food");
        recordPanel.add(workoutButton);
        recordPanel.add(foodButton);

        JPanel morePanel = new JPanel();
        morePanel.setBorder(BorderFactory.createTitledBorder("More"));
        JButton statsButton = new JButton("Statistics");
        morePanel.add(statsButton);

        add(recordPanel);
        add(morePanel);

        workoutButton.addActionListener(e -> openWorkoutView());
        foodButton.addActionListener(e -> openFoodView());
        statsButton.addActionListener(e -> openStatsView());

        setVisible(true);
    }

    private void openWorkoutView() {
        new ViewWorkout();
    }

    private void openFoodView() {
        new ViewFood();
    }

    private void openStatsView() {
        new ViewStatistics();
    }
}