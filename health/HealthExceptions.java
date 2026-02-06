package health;

// Custom exceptions - user-defined exception classes
public class HealthExceptions {
    
    // Inheritance - extends Exception, thrown for invalid health data
    public static class InvalidHealthDataException extends Exception {
        // Constructor - initializing exception with error message
        public InvalidHealthDataException(String message) {
            super(message);
        }
    }
    
    // Inheritance - extends Exception, thrown when user not found
    public static class UserNotFoundException extends Exception {
        // Constructor - initializing exception with error message
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
