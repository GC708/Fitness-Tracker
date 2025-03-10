package Views;

import java.util.ArrayList;
import java.io.*;

public class FitnessData {
    public static ArrayList<FoodEntry> foodLog = new ArrayList<>();
    public static ArrayList<WorkoutEntry> workoutLog = new ArrayList<>();

    public static void loadAllData() {
        loadFoodData();
        loadWorkoutData();
    }

    private static void loadFoodData() {
        foodLog.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("food_log.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(", ");
                if (parts.length == 5) {
                    String foodItem = parts[0];
                    String date = parts[1];
                    String time = parts[2];
                    double protein = Double.parseDouble(parts[3]);
                    double calories = Double.parseDouble(parts[4]);
                    foodLog.add(new FoodEntry(foodItem, date, time, protein, calories));
                }
            }
            System.out.println("Food data loaded successfully.");
        } catch (IOException e) {
            System.out.println("No previous food data found.");
        }
    }

    private static void loadWorkoutData() {
        workoutLog.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("workout_log.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(", ");
                if (parts.length == 5) {
                    String workoutType = parts[0];
                    String date = parts[1];
                    String time = parts[2];
                    String[] setsReps = parts[3].split("x");
                    int sets = Integer.parseInt(setsReps[0]);
                    int reps = Integer.parseInt(setsReps[1]);
                    double weight = Double.parseDouble(parts[4]);
                    workoutLog.add(new WorkoutEntry(workoutType, date, time, sets, reps, weight));
                }
            }
            System.out.println("Workout data loaded successfully.");
        } catch (IOException e) {
            System.out.println("No previous workout data found.");
        }
    }
}