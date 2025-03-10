package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewWorkout extends JFrame {
    private DefaultTableModel model;
    private JTable workoutTable;

    public ViewWorkout() {
        setTitle("Workout Tracker");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        model.addColumn("Workout Type");
        model.addColumn("Sets x Reps");
        model.addColumn("Weight (lbs)");
        model.addColumn("Date");

        workoutTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(workoutTable);
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> openAddWorkoutDialog());
        deleteButton.addActionListener(e -> deleteSelectedWorkout());

        // Populate table with existing data from FitnessData
        for (WorkoutEntry entry : FitnessData.workoutLog) {
            model.addRow(new Object[]{entry.getWorkoutType(), entry.getSets() + "x" + entry.getReps(),
                    entry.getWeight(), entry.getDate()});
        }

        setVisible(true);
    }

    private void openAddWorkoutDialog() {
        JTextField workoutInput = new JTextField(20);
        JTextField setsRepsInput = new JTextField(10);
        JTextField weightInput = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Workout Type:"));
        panel.add(workoutInput);
        panel.add(new JLabel("Sets x Reps:"));
        panel.add(setsRepsInput);
        panel.add(new JLabel("Weight (lbs):"));
        panel.add(weightInput);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Workout", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String workoutType = workoutInput.getText().trim();
            String setsReps = setsRepsInput.getText().trim();
            String weight = weightInput.getText().trim();

            if (!setsReps.matches("\\d+x\\d+")) {
                JOptionPane.showMessageDialog(this, "Please enter 'Sets x Reps' in the format: 3x10");
                return;
            }

            try {
                int sets = Integer.parseInt(setsReps.split("x")[0].trim());
                int reps = Integer.parseInt(setsReps.split("x")[1].trim());
                double weightValue = Double.parseDouble(weight);
                addWorkout(workoutType, sets, reps, weightValue);
                saveWorkoutData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Sets, Reps, and Weight.");
            }
        }
    }

    private void addWorkout(String workoutType, int sets, int reps, double weight) {
        String currentDate = new SimpleDateFormat("MMMM d, yyyy").format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        FitnessData.workoutLog.add(new WorkoutEntry(workoutType, currentDate, currentTime, sets, reps, weight));
        model.addRow(new Object[]{workoutType, sets + "x" + reps, weight, currentDate});
    }

    private void deleteSelectedWorkout() {
        int selectedRow = workoutTable.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
            FitnessData.workoutLog.remove(selectedRow);
            saveWorkoutData();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a workout to delete.");
        }
    }

    public void saveWorkoutData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("workout_log.txt"))) {
            System.out.println("Saving workout data...");
            for (WorkoutEntry entry : FitnessData.workoutLog) {
                String cleanedDate = entry.getDate().replace(",", "");
                writer.write(entry.getWorkoutType() + ", " + cleanedDate + ", " + entry.getTime() + ", "
                        + entry.getSets() + "x" + entry.getReps() + ", " + entry.getWeight());
                writer.newLine();
            }
            System.out.println("Workout data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving workout data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewWorkout::new);
    }
}