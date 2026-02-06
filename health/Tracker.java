package health;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Tracker {
    // Collections - storing meals and exercises in ArrayList
    private final List<Meal> meals = new ArrayList<>();
    private final List<Exercise> exercises = new ArrayList<>();
    // Multithreading - ReentrantLock for thread-safe operations
    private final ReentrantLock lock = new ReentrantLock();
    private DataFileManager fileManager;

    // Exception handling - adding meal with validation, Multithreading - using lock
    public void addMeal(Meal meal) throws HealthExceptions.InvalidHealthDataException {
        if (meal.getCalories() < 0) {
            throw new HealthExceptions.InvalidHealthDataException("Calories cannot be negative!");
        }
        lock.lock();
        try {
            meals.add(meal);
        } finally {
            lock.unlock();
        }
    }

    // Exception handling - adding exercise with validation, Multithreading - using lock
    public void addExercise(Exercise exercise) throws HealthExceptions.InvalidHealthDataException {
        if (exercise.getCaloriesBurned() < 0) {
            throw new HealthExceptions.InvalidHealthDataException("Calories burned cannot be negative!");
        }
        lock.lock();
        try {
            exercises.add(exercise);
        } finally {
            lock.unlock();
        }
    }

    // Collections - sorting and displaying summary, Multithreading - thread-safe access
    public void showSummary() {
        lock.lock();
        try {
            int totalCalories = 0;
            int burnedCalories = 0;

            System.out.println("\n Health Summary:");
            
            // Collections - sorting using Comparable interface
            List<Meal> sortedMeals = new ArrayList<>(meals);
            java.util.Collections.sort(sortedMeals);
            
            System.out.println("\nMeals (sorted by calories):");
            for (Meal m : sortedMeals) {
                System.out.println("- " + m.getName() + ": " + m.getCalories() + " kcal");
                totalCalories += m.getCalories();
            }

            // Collections - sorting using Comparable interface
            List<Exercise> sortedExercises = new ArrayList<>(exercises);
            java.util.Collections.sort(sortedExercises);
            
            System.out.println("\nExercises (sorted by calories burned):");
            for (Exercise e : sortedExercises) {
                System.out.println("- " + e.getName() + ": " + e.getCaloriesBurned() + " kcal burned");
                burnedCalories += e.getCaloriesBurned();
            }

            System.out.println("\nTotal Calories Consumed: " + totalCalories);
            System.out.println("Total Calories Burned: " + burnedCalories);
            System.out.println("Net Calories: " + (totalCalories - burnedCalories));
        } finally {
            lock.unlock();
        }
    }
    
    // Multithreading - thread-safe method to get meal count
    public int getMealCount() {
        lock.lock();
        try {
            return meals.size();
        } finally {
            lock.unlock();
        }
    }
    
    // Multithreading - thread-safe method to get exercise count
    public int getExerciseCount() {
        lock.lock();
        try {
            return exercises.size();
        } finally {
            lock.unlock();
        }
    }
    
    // Setter method - setting file manager for data persistence
    public void setFileManager(DataFileManager fileManager) {
        this.fileManager = fileManager;
    }
    
    // File I/O - loading data from file, Multithreading - thread-safe loading
    public void loadData() throws IOException {
        if (fileManager != null) {
            lock.lock();
            try {
                meals.clear();
                exercises.clear();
                meals.addAll(fileManager.loadMeals());
                exercises.addAll(fileManager.loadExercises());
                System.out.println("Loaded " + meals.size() + " meals and " + exercises.size() + " exercises.");
            } finally {
                lock.unlock();
            }
        }
    }
    
    // File I/O - saving data to file, Multithreading - thread-safe saving
    public void saveData() throws IOException {
        if (fileManager != null) {
            lock.lock();
            try {
                fileManager.saveMeals(meals);
                fileManager.saveExercises(exercises);
            } finally {
                lock.unlock();
            }
        }
    }
}
