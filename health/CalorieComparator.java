package health;

import java.util.Comparator;

// Comparator interface - custom sorting alternative to Comparable
public class CalorieComparator {
    
    // Static nested class - Comparator for sorting meals by name
    public static class MealNameComparator implements Comparator<Meal> {
        // Comparator interface - comparing meals alphabetically by name
        @Override
        public int compare(Meal m1, Meal m2) {
            return m1.getName().compareToIgnoreCase(m2.getName());
        }
    }
    
    // Static nested class - Comparator for sorting exercises by name
    public static class ExerciseNameComparator implements Comparator<Exercise> {
        // Comparator interface - comparing exercises alphabetically by name
        @Override
        public int compare(Exercise e1, Exercise e2) {
            return e1.getName().compareToIgnoreCase(e2.getName());
        }
    }
}
