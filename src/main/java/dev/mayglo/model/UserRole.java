package dev.mayglo.model;

public enum UserRole
{
    /**
     * Roles for users
     */
    EMPLOYEE("EMPLOYEE"),
    MANAGER("MANAGER"),
    ADMINISTRATOR("ADMINISTRATOR");

    /**
     * The role of this user
     */
    public final String role;

    /**
     * Constructor --> Sets the role to a specific string
     * @param role String indicating role of this user
     */
    UserRole(String role) { this.role = role;}
}
