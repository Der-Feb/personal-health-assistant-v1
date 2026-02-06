package health;

import java.io.Serializable;

// Serializable - allows saving object to file, Comparable - enables sorting
public class Meal implements Serializable, Comparable<Meal> {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int calories;
    private final HealthDataType type = HealthDataType.MEAL;

    // Constructor - initializing meal with name and calories
    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    // Getter method - getting meal name
    public String getName() { return name; }
    
    // Getter method - getting meal calories
    public int getCalories() { return calories; }
    
    // Getter method - getting data type (enum)
    public HealthDataType getType() { return type; }
    
    // Comparable interface - sorting meals by calories (highest first)
    @Override
    public int compareTo(Meal other) {
        return Integer.compare(other.calories, this.calories);
    }
    
    // Polymorphism - overriding toString for readable output
    @Override
    public String toString() {
        return "Meal{name='" + name + "', calories=" + calories + "}";
    }
    
    // Polymorphism - overriding equals for object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Meal meal = (Meal) obj;
        return calories == meal.calories && name.equals(meal.name);
    }
    
    // Polymorphism - overriding hashCode for hash-based collections
    @Override
    public int hashCode() {
        return name.hashCode() + calories;
    }
}
