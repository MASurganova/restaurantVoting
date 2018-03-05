package ru.voting.model;


import ru.voting.util.exception.TimeDelayException;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Set;

public class User extends AbstractNamedEntity {

    private String password;

    private boolean enabled = true;

    private Restaurant choice;

    private Set<Role> roles;

    public User(Integer id, String name, String password, Role role, Role ... roles) {
        this(id, name, password, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String password, boolean enabled, EnumSet<Role> roles) {
        super(id, name);
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
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
        LocalTime time = LocalTime.now();
        if (time.isAfter(LocalTime.of(11, 0))) throw new TimeDelayException("attempt to change the choice after 11:00");
        this.choice = choice;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", enabled=" + enabled +
                ", choice=" + choice +
                ", roles=" + roles +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
