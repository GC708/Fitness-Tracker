package Models;

// Updated WorkoutEntry class with necessary fields
public class WorkoutEntry {
    String workoutType;
    String date;
    String time;
    int sets;
    int reps;
    double weight;

    public WorkoutEntry(String workoutType, String date, String time, int sets, int reps, double weight) {
        this.workoutType = workoutType;
        this.date = date;
        this.time = time;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Add this method to the WorkoutEntry class
    public double getCaloriesBurned() {
        // Example formula: calories burned = sets * reps * weight * some constant factor (e.g., 0.1)
        return sets * reps * weight * 0.1;  // Adjust this formula based on your requirements
    }

    // Getters for all fields
    public String getWorkoutType() { return workoutType; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public double getWeight() { return weight; }
}
