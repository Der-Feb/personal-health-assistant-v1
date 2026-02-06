package health;

import java.io.Serializable;

// Serializable - allows saving object to file, Comparable - enables sorting
public class Exercise implements Serializable, Comparable<Exercise> {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int caloriesBurned;
    private final HealthDataType type = HealthDataType.EXERCISE;

    // Constructor - initializing exercise with name and calories burned
    public Exercise(String name, int caloriesBurned) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    // Getter method - getting exercise name
    public String getName() { return name; }
    
    // Getter method - getting calories burned
    public int getCaloriesBurned() { return caloriesBurned; }
    
    // Getter method - getting data type (enum)
    public HealthDataType getType() { return type; }
    
    // Comparable interface - sorting exercises by calories burned (highest first)
    @Override
    public int compareTo(Exercise other) {
        return Integer.compare(other.caloriesBurned, this.caloriesBurned);
    }
    
    // Polymorphism - overriding toString for readable output
    @Override
    public String toString() {
        return "Exercise{name='" + name + "', caloriesBurned=" + caloriesBurned + "}";
    }
    
    // Polymorphism - overriding equals for object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Exercise exercise = (Exercise) obj;
        return caloriesBurned == exercise.caloriesBurned && name.equals(exercise.name);
    }
    
    // Polymorphism - overriding hashCode for hash-based collections
    @Override
    public int hashCode() {
        return name.hashCode() + caloriesBurned;
    }
}
