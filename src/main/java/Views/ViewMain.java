package Views;

import Models.FitnessData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMain extends JFrame implements ActionListener {
    JButton workoutButton = new JButton("Workout");
    JButton foodButton = new JButton("Food");
    JButton statsButton = new JButton("Statistics");

    public ViewMain() {
        setTitle("Fitness Tracker - Homepage");
        setExtendedState(ViewMain.MAXIMIZED_BOTH);      // Automatic full screen
        setSize(800,600);                   // If window is moved by user then shrink to fixed value
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        // Load all data at startup
        FitnessData.loadAllData();

        JPanel recordPanel = new JPanel();
        recordPanel.setBorder(BorderFactory.createTitledBorder("Record"));
        recordPanel.add(workoutButton);
        recordPanel.add(foodButton);

        JPanel morePanel = new JPanel();
        morePanel.setBorder(BorderFactory.createTitledBorder("More"));
        morePanel.add(statsButton);

        statsButton.setFocusable(false);
        foodButton.setFocusable(false);
        workoutButton.setFocusable(false);

        add(recordPanel);
        add(morePanel);

        workoutButton.addActionListener(this);
        foodButton.addActionListener(this);
        statsButton.addActionListener(this);

        setVisible(true);
    }

    /**
     * Handles button click events and opens the corresponding window.
     * Disposes of the current window before opening a new one.
     * @param e the event to be processed, used to determine which button was clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == foodButton) {
            dispose();
            ViewFood foodWindow = new ViewFood();
        }
        if (e.getSource() == statsButton) {
            dispose();
            ViewStatistics statWindow = new ViewStatistics();
        }
        if (e.getSource() == workoutButton) {
            dispose();
            ViewWorkout workoutWindow  = new ViewWorkout();
        }
    }
}