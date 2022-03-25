package dev.mayglo.model;

/**
 * Stores user account information
 */
public class User
{
    private Integer user_ID;
    private String username;
    private String password;
    private String email;
    private String given_name;
    private String surname;
    private Boolean is_Active;
    private Integer role_ID;

    /**
     * Creates a new User with no parameters
     */
    public User() {}

    // FOR DEBUGGING TO "testusers" DB
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Creates a user with minimum parameters for "ERS_USER" table, used for JUNIT testing ------
     * @param username
     * @param password
     * @param email
     * @param given_name
     * @param surname
     */
    public User(String username, String password, String email, String given_name, String surname)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.given_name = given_name;
        this.surname = surname;
    }

    /**
     * Creates a new User with all required parameters for "ERS_USER" table.
     * Used for new user creation.
     * @param username
     * @param password
     * @param email
     * @param given_name
     * @param surname
     * @param is_Active
     * @param role_ID
     */
    public User(String username, String password, String email, String given_name, String surname,
                Boolean is_Active, Integer role_ID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.given_name = given_name;
        this.surname = surname;
        this.is_Active = is_Active;
        this.role_ID = role_ID;
    }

    /**
     * Creates a new User with all parameters for "ERS_USER" table.
     * Used for querying the database based on user_ID.
     * @param username
     * @param password
     * @param email
     * @param given_name
     * @param surname
     * @param is_Active
     * @param role_ID
     */
    public User(Integer user_ID, String username, String password, String email, String given_name, String surname,
                Boolean is_Active, Integer role_ID) {
        this.user_ID = user_ID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.given_name = given_name;
        this.surname = surname;
        this.is_Active = is_Active;
        this.role_ID = role_ID;
    }

    // GETTERS

    public Integer getID() {
        return user_ID;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getSurname() {
        return surname;
    }

    public Boolean getIs_Active() {
        return is_Active;
    }
    
    public Integer getRole_ID() {
        return role_ID;
    }

    // SETTERS

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIs_Active(Boolean is_Active) {
        this.is_Active = is_Active;
    }

    public void setRole_ID(Integer role_ID) {
        this.role_ID = role_ID;
    }

    @Override
    public String toString() {
        return "user_ID: " + user_ID + "\n" +
                "username:" + username + "\n" +
                "password: " + password + "\n" +
                "email: " + email + "\n" +
                "given_name: " + given_name + "\n" +
                "surname: " + surname + "\n" +
                "is_Active: " + is_Active + "\n" +
                "role_ID: " + role_ID + "\n";
    }
}
