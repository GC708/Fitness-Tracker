package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewFood extends JFrame {
    private DefaultTableModel model;
    private JTable foodTable;

    public ViewFood() {
        setTitle("Food Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        model.addColumn("Food Item");
        model.addColumn("Date");
        model.addColumn("Time");
        model.addColumn("Protein Intake (g)");
        model.addColumn("Calories (kcal)");


        foodTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(foodTable);
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
                openAddFoodDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedFood();
            }
        });

        setVisible(true);
    }

    private void openAddFoodDialog() {
        JTextField foodInput = new JTextField(20);
        JTextField proteinInput = new JTextField(5);
        JTextField caloriesInput = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Food Item:"));
        panel.add(foodInput);
        panel.add(new JLabel("Protein Intake (g):"));
        panel.add(proteinInput);
        panel.add(new JLabel("Calories (kcal):"));
        panel.add(caloriesInput);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Food", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String foodItem = foodInput.getText();
            String protein = proteinInput.getText();
            String calories = caloriesInput.getText();

            if (!foodItem.trim().isEmpty() && !protein.trim().isEmpty() && !calories.trim().isEmpty()) {
                try {
                    double proteinValue = Double.parseDouble(protein);
                    double calorieValue = Double.parseDouble(calories);
                    addFoodItem(foodItem, proteinValue, calorieValue);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for protein and calories.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        }
    }

    private void addFoodItem(String foodItem, double protein, double calories) {
        String currentDate = new SimpleDateFormat("MMMM d, yyyy").format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        model.addRow(new Object[]{foodItem, currentDate, currentTime, protein, calories});
    }
    private void deleteSelectedFood() {
        int selectedRow = foodTable.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a food item to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewFood();
            }
        });
    }
}


