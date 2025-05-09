package model;

public class User {
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;

    // Constructors
    public User() {}  // Default constructor (important for libraries like JDBC, Hibernate, etc.)

    public User(String name, String email, String username, String password) {  //we skip id here since the DB will auto-generate it)
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
