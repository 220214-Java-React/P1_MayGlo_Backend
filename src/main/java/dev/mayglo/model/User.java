package dev.mayglo.model;

/**
 * Stores User account information
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
     * @param username The username of this User
     * @param password The password of this User
     * @param email The email of this User
     * @param given_name The first (given) name of this User
     * @param surname The surname of this User
     * @param is_Active The activity status of this User
     * @param role_ID The role ID of this User
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
     * Creates a new User with all parameters for "ERS_USER" table plus the user_ID.
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

    /**
     * Gets the user_ID value of a User.
     * @return This User's user_ID
     */
    public Integer getID() {
        return user_ID;
    }
    /**
     * Gets the username value of a User.
     * @return This User's username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Gets the password value of a User.
     * @return This User's password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Gets the email value of a User.
     * @return This User's email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Gets the given_name value of a User.
     * @return This User's given_name
     */
    public String getGiven_name() {
        return given_name;
    }
    /**
     * Gets the surname value of a User.
     * @return This User's surname
     */
    public String getSurname() {
        return surname;
    }
    /**
     * Gets the is_Active (activity status) value of a User.
     * @return This User's is_Active value
     */
    public Boolean getIs_Active() {
        return is_Active;
    }
    /**
     * Gets the role_ID value of a User.
     * @return This User's role_ID
     */
    public Integer getRole_ID() {
        return role_ID;
    }

    /**
     * Sets a user's ID number. Used only for updating users.
     * @param user_ID The ID (int) to set
     */
    public void setUser_ID(Integer user_ID) {this.user_ID = user_ID;}

    /**
     * Sets a user's password.
     * @param password The password (string) to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets a user's activity status
     * @param is_Active A boolean
     */
    public void setIs_Active(Boolean is_Active) {
        this.is_Active = is_Active;
    }

    /**
     * Sets all User fields except user_ID. Used for updating users.
     * @param updatedUser User with updated data
     */
    public void setAll(User updatedUser) {
        this.username = updatedUser.getUsername();
        this.password = updatedUser.getPassword();
        this.email = updatedUser.getEmail();
        this.given_name = updatedUser.getGiven_name();
        this.surname = updatedUser.getSurname();
        this.is_Active = updatedUser.getIs_Active();
        this.role_ID = updatedUser.getRole_ID();
    }

    /**
     * Prints a User's information in an easily readable format.
     * @return A formatted string of all user variables and values
     */
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
