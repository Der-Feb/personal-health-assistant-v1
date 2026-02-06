package health;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataFileManager {
    private static final String DATA_DIR = "user_data";
    private final String username;
    
    // Constructor - initializing file manager with username
    public DataFileManager(String username) {
        this.username = username;
        createDataDirectory();
    }
    
    // File I/O - creating directory if it doesn't exist
    private void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
    
    // Helper method - getting meals file path for specific user
    private String getMealsFile() {
        return DATA_DIR + File.separator + username + "_meals.dat";
    }
    
    // Helper method - getting exercises file path for specific user
    private String getExercisesFile() {
        return DATA_DIR + File.separator + username + "_exercises.dat";
    }
    
    // Serialization - saving meals list to binary file
    public void saveMeals(List<Meal> meals) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getMealsFile()))) {
            oos.writeObject(meals);
        }
    }
    
    // Serialization - saving exercises list to binary file
    public void saveExercises(List<Exercise> exercises) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getExercisesFile()))) {
            oos.writeObject(exercises);
        }
    }
    
    // Deserialization - loading meals list from binary file
    @SuppressWarnings("unchecked")
    public List<Meal> loadMeals() throws IOException {
        File file = new File(getMealsFile());
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Meal>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error loading meals: " + e.getMessage());
        }
    }
    
    // Deserialization - loading exercises list from binary file
    @SuppressWarnings("unchecked")
    public List<Exercise> loadExercises() throws IOException {
        File file = new File(getExercisesFile());
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Exercise>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error loading exercises: " + e.getMessage());
        }
    }
}
