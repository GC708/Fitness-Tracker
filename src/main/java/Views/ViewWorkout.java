package Views;

import Models.FitnessData;
import Models.WorkoutEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewWorkout extends JFrame implements ActionListener {
    private DefaultTableModel model;
    private JTable workoutTable;
    JButton returnButton = new JButton("Return");

    public ViewWorkout() {
        setTitle("Fitness Tracker - Workout");
        setExtendedState(ViewMain.MAXIMIZED_BOTH);
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        buttonPanel.add(returnButton);

        addButton.setFocusable(false);
        deleteButton.setFocusable(false);
        returnButton.setFocusable(false);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        //  beginner section for muscle groups
        JPanel muscleGroupPanel = new JPanel();
        String[] muscleGroups = {"Select Muscle Group", "Chest", "Back", "Shoulders", "Arms", "Legs", "Glutes", "Abs"};
        JComboBox<String> muscleGroupComboBox = new JComboBox<>(muscleGroups);
        muscleGroupPanel.add(muscleGroupComboBox);

        //  default workouts
        JTextArea defaultWorkoutArea = new JTextArea(5, 30);
        defaultWorkoutArea.setEditable(false);
        muscleGroupPanel.add(defaultWorkoutArea);

        muscleGroupComboBox.addActionListener(e -> {
            String selectedGroup = (String) muscleGroupComboBox.getSelectedItem();
            String workoutPlan = getBeginnerWorkout(selectedGroup);
            defaultWorkoutArea.setText(workoutPlan);
        });

        add(muscleGroupPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> openAddWorkoutDialog());
        deleteButton.addActionListener(e -> deleteSelectedWorkout());
        returnButton.addActionListener(this);

        //  current workout to table
        for (WorkoutEntry entry : FitnessData.workoutLog) {
            model.addRow(new Object[]{entry.getWorkoutType(), entry.getSets() + "x" + entry.getReps(),
                    entry.getWeight(), entry.getDate()});
        }

        setVisible(true);
    }

    /**
     * Returns the user to homepage
     * Disposes of the current window before opening a new one.
     * @param e the event to be processed, used to determine which button was clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            dispose();
            ViewMain viewMain = new ViewMain();
        }
    }

    private String getBeginnerWorkout(String muscleGroup) {
        switch (muscleGroup) {
            case "Chest":
                return "1. Chest Press: 4 x 10\n" +
                        "2. Push-ups: 3 x 12\n" +
                        "3. Dumbbell Fly: 3 x 10\n" +
                        "4. Incline Chest Press: 4 x 10\n" +
                        "5. Cable Chest Fly: 3 x 12";
            case "Back":
                return "1. Lat Pulldown: 4 x 10\n" +
                        "2. Seated Row: 3 x 10\n" +
                        "3. Deadlift: 3 x 8\n" +
                        "4. Bent-over Row: 4 x 10\n" +
                        "5. T-Bar Row: 3 x 12";
            case "Shoulders":
                return "1. Shoulder Press: 4 x 10\n" +
                        "2. Lateral Raise: 3 x 12\n" +
                        "3. Front Raise: 3 x 10\n" +
                        "4. Arnold Press: 3 x 10\n" +
                        "5. Rear Delt Fly: 3 x 12";
            case "Arms":
                return "1. Bicep Curl: 4 x 12\n" +
                        "2. Tricep Dips: 3 x 10\n" +
                        "3. Hammer Curl: 3 x 12\n" +
                        "4. Tricep Pushdown: 4 x 10\n" +
                        "5. Concentration Curl: 3 x 10";
            case "Legs":
                return "1. Squats: 4 x 10\n" +
                        "2. Lunges: 3 x 12\n" +
                        "3. Leg Press: 3 x 10\n" +
                        "4. Leg Curl: 3 x 12\n" +
                        "5. Bulgarian Split Squat: 3 x 10";
            case "Glutes":
                return "1. Hip Thrust: 4 x 10\n" +
                        "2. Glute Bridges: 3 x 12\n" +
                        "3. Bulgarian Split Squat: 3 x 10\n" +
                        "4. Cable Kickbacks: 3 x 12\n" +
                        "5. Deadlift: 3 x 8";
            case "Abs":
                return "1. Plank: 3 x 30 seconds\n" +
                        "2. Leg Raises: 3 x 12\n" +
                        "3. Russian Twists: 3 x 15\n" +
                        "4. Bicycle Crunches: 3 x 15\n" +
                        "5. Mountain Climbers: 3 x 30 seconds";
            default:
                return "Please select a muscle group to view a beginner workout.";
        }
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
