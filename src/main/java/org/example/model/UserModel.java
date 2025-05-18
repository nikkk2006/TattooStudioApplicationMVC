package org.example.model;


public class UserModel {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role;

    public UserModel(String name, String email, String password, String role) {
        this(null, name, email, password, role);
    }

    public UserModel(Integer id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role.toUpperCase();
    }

    // Геттеры и сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}