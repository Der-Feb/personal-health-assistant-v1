package health;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserFileManager {
    private static final String DATA_DIR = "user_data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.dat";
    
    // Constructor - initializing and creating data directory
    public UserFileManager() {
        createDataDirectory();
    }
    
    // File I/O - creating directory if it doesn't exist
    private void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
    
    // Deserialization - loading users from binary file and sorting them
    @SuppressWarnings("unchecked")
    public List<User> loadUsers() throws IOException {
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<User> users = (List<User>) ois.readObject();
            Collections.sort(users);
            return users;
        } catch (ClassNotFoundException e) {
            throw new IOException("Error loading users: " + e.getMessage());
        }
    }
    
    // Serialization - saving user to binary file, avoiding duplicates
    public void saveUser(User user) throws IOException {
        List<User> users = loadUsers();
        
        boolean exists = false;
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(user.getName())) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            users.add(user);
            Collections.sort(users);
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        }
    }
    
    // Exception handling - finding user or throwing custom exception
    public User findUser(String name) throws HealthExceptions.UserNotFoundException, IOException {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        throw new HealthExceptions.UserNotFoundException("User '" + name + "' not found in database.");
    }
}
