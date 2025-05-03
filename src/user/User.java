package user;

/**
 * Abstract class representing a User in the Cinema Ticketing System
 */
public abstract class User {
    // Fields
    protected String username;
    protected String password;
    
    /**
     * Constructor for User
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * Login method to authenticate user
     * @param inputUser input username
     * @param inputPass input password
     * @return true if login successful, false otherwise
     */
    public boolean login(String inputUser, String inputPass) {
        // TODO: Implement login logic
        return false;
    }
    
    /**
     * Method to print user information
     */
    public abstract void printInfo();
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    // We don't provide a getter for password for security reasons
    public void setPassword(String password) {
        this.password = password;
    }
}
