package health;

import java.io.Serializable;

// Serializable - allows saving object to file, Comparable - enables sorting
public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private String email;

    // Getter method - getting user name
    public String getName() { return name; }
    
    // Setter method - setting user name
    public void setName(String name) { this.name = name; }

    // Getter method - getting user age
    public int getAge() { return age; }
    
    // Setter method - setting user age
    public void setAge(int age) { this.age = age; }
    
    // Getter method - getting user email
    public String getEmail() { return email; }
    
    // Setter method - setting user email
    public void setEmail(String email) { this.email = email; }
    
    // Comparable interface - sorting users alphabetically by name
    @Override
    public int compareTo(User other) {
        return this.name.compareToIgnoreCase(other.name);
    }
    
    // Regex - validating email format using regular expression pattern
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    // Polymorphism - overriding toString for readble output
    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
    
    // Polymorphism - overriding equals for object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return age == user.age && name.equals(user.name) && email.equals(user.email);
    }
    
    // Polymorphism - overriding hashCode for hash based collections
    @Override
    public int hashCode() {
        return name.hashCode() + age + email.hashCode();
    }
}
