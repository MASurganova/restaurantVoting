package ru.voting.model;


import ru.voting.util.exception.TimeDelayException;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Set;

public class User extends AbstractNamedEntity {

    private String email;

    private String password;

    private boolean enabled = true;

    private Restaurant choice;

    private Set<Role> roles;


    public User(Integer id, String name, String email, String password, Role role, Role ... roles) {
        this(id, name, email, password, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, boolean enabled, EnumSet<Role> roles) {
        super(id, name);
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Restaurant getChoice() {
        return choice;
    }

    public void setChoice(Restaurant choice) throws TimeDelayException {
        this.choice = choice;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", choice=" + choice +
                ", roles=" + roles +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
