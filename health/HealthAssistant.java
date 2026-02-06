package health;
import java.io.IOException;
import java.util.Scanner;

public class HealthAssistant {
    private final Scanner scanner = new Scanner(System.in);
    private User user;
    private final Tracker tracker = new Tracker();
    private final UserFileManager fileManager = new UserFileManager();
    private AutoSaveThread autoSaveThread;

    public void start() {
        System.out.println("Welcome to your Personal Health Assistant!");
        
        try {
            loadOrCreateUser();
            
            // Set up file manager and load existing data
            DataFileManager dataFileManager = new DataFileManager(user.getName());
            tracker.setFileManager(dataFileManager);
            tracker.loadData();
            
            // Start auto-save thread
            autoSaveThread = new AutoSaveThread(tracker);
            autoSaveThread.start();
            
            System.out.println("\nHi " + user.getName() + "! Let's track your health.");
            // System.out.println("(Auto-save running in background every 30 seconds...)");
            
            // Show current summary if user has data
            if (tracker.getMealCount() > 0 || tracker.getExerciseCount() > 0) {
                System.out.println("\n===== Your Current Progress =====");
                tracker.showSummary();
            }
            
            boolean running = true;
            while (running) {
                System.out.println("\n===== Menu =====");
                System.out.println("1. Log Meal");
                System.out.println("2. Log Exercise");
                System.out.println("3. View Summary");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    switch (choice) {
                        case 1 -> {
                            logMeal();
                            pauseForUser();
                        }
                        case 2 -> {
                            logExercise();
                            pauseForUser();
                        }
                        case 3 -> {
                            tracker.showSummary();
                            pauseForUser();
                        }
                        case 4 -> running = false;
                        default -> {
                            System.out.println("Invalid choice, try again.");
                            pauseForUser();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    scanner.nextLine();
                    pauseForUser();
                }
            }
            
            autoSaveThread.stopSaving();
            
            // Final save before exit
            try {
                tracker.saveData();
                System.out.println("Data saved successfully!");
            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
            
            System.out.println("Goodbye, stay healthy!");
            
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    // User authentication - checking if user exists or creating new account
    private void loadOrCreateUser() throws IOException {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        try {
            user = fileManager.findUser(name);
            System.out.println("✓ Logged in successfully! Welcome back, " + user.getName() + "!");
        } catch (HealthExceptions.UserNotFoundException e) {
            System.out.println("User not found. Creating new account...");
            createNewUser(name);
            System.out.println("✓ Account created successfully! Welcome, " + user.getName() + "!");
        }
    }
    
    // Creating new user account with name, age, and email validation using regex
    private void createNewUser(String name) throws IOException {
        user = new User();
        user.setName(name);
        
        System.out.print("Enter your age: ");
        user.setAge(scanner.nextInt());
        scanner.nextLine();
        
        // Regex validation - loop until valid email is entered
        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (User.isValidEmail(email)) {
                user.setEmail(email);
                break;
            } else {
                System.out.println("✗ Invalid email format! Please use format: example@domain.com");
            }
        }
        
        fileManager.saveUser(user);
    }
    
    // User interaction - pausing to let user read output before returning to menu
    private void pauseForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void logMeal() {
        try {
            System.out.print("Enter meal name: ");
            String mealName = scanner.nextLine();
            System.out.print("Enter calories: ");
            int calories = scanner.nextInt();
            scanner.nextLine();
            Meal meal = new Meal(mealName, calories);
            tracker.addMeal(meal);
            System.out.println("\n✓ Meal logged for " + user.getName() + ":");
            System.out.println("  Type: " + meal.getType());
            System.out.println("  Name: " + mealName);
            System.out.println("  Calories: " + calories + " kcal");
        } catch (HealthExceptions.InvalidHealthDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void logExercise() {
        try {
            System.out.print("Enter exercise name: ");
            String exerciseName = scanner.nextLine();
            System.out.print("Enter calories burned: ");
            int calories = scanner.nextInt();
            scanner.nextLine();
            Exercise exercise = new Exercise(exerciseName, calories);
            tracker.addExercise(exercise);
            System.out.println("\n✓ Exercise logged for " + user.getName() + ":");
            System.out.println("  Type: " + exercise.getType());
            System.out.println("  Name: " + exerciseName);
            System.out.println("  Calories Burned: " + calories + " kcal");
        } catch (HealthExceptions.InvalidHealthDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Multithreading - background thread for auto-saving data periodically
    private static class AutoSaveThread extends Thread {
        private final Tracker tracker;
        private volatile boolean running = true;
        
        public AutoSaveThread(Tracker tracker) {
            this.tracker = tracker;
            setDaemon(true);
        }
        
        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(30000); // Save every 30 seconds
                    tracker.saveData();
                    // Silent save
                } catch (InterruptedException e) {
                    break;
                } catch (IOException e) {
                    // Silent error - logged but doesn't interrupt user
                    e.printStackTrace();
                }
            }
        }
        
        public void stopSaving() {
            running = false;
            interrupt();
        }
    }
}
