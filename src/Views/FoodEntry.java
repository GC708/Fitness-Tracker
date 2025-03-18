package Views;

class FoodEntry {
    String foodItem;
    String date;
    String time;
    double protein;
    double calories;

    public FoodEntry(String foodItem, String date, String time, double protein, double calories) {
        this.foodItem = foodItem;
        this.date = date;
        this.time = time;
        this.protein = protein;
        this.calories = calories;
    }

    public String getFoodItem() { return foodItem; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public double getProtein() { return protein; }
    public double getCalories() { return calories; }
}

