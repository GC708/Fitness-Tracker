package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddWorkoutDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedWorkout();
            }
        });

        setVisible(true);
    }

    private void openAddWorkoutDialog() {
        JTextField workoutInput = new JTextField(20);
        JTextField setsRepsInput = new JTextField(10);
        JTextField weightInput = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Workout Type:"));
        panel.add(workoutInput);
        panel.add(new JLabel("Sets x Reps:"));
        panel.add(setsRepsInput);
        panel.add(new JLabel("Weight (lbs):"));
        panel.add(weightInput);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Workout", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String workoutType = workoutInput.getText();
            String setsReps = setsRepsInput.getText();
            String weight = weightInput.getText();

            if (!workoutType.trim().isEmpty() && !setsReps.trim().isEmpty() && !weight.trim().isEmpty()) {
                addWorkout(workoutType, setsReps, weight);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        }
    }

    private void addWorkout(String workoutType, String setsReps, String weight) {
        String currentDate = new SimpleDateFormat("MMMM d, yyyy").format(new Date());

        model.addRow(new Object[]{workoutType, setsReps, weight, currentDate});
    }

    private void deleteSelectedWorkout() {
        int selectedRow = workoutTable.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a workout to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewWorkout();
            }
        });
    }
}

