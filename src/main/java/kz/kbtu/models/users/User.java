package kz.kbtu.models.users;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String id;
    protected boolean loggedIn;

    public User() {}

    public User(String username, String password, String firstName, String lastName, String id) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public boolean login(String enteredPassword) {
        if (Objects.equals(this.password, enteredPassword)) {
            this.loggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.loggedIn = false;
    }

    public boolean isLoggedIn() { return loggedIn; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String fullName() { return firstName + " " + lastName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return Objects.equals(id, ((User) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id='" + id + "', username='" + username +
                "', name='" + fullName() + "'}";
    }
}
