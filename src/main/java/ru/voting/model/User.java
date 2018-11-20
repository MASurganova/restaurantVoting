package ru.voting.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.BY_EMAIL, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.WITH_CHOICE, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.choice WHERE u.id=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.choice ORDER BY u.name, u.email"),
})
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {

    public static final String DELETE = "User.delete";
    public static final String BY_EMAIL = "User.getByEmail";
    public static final String WITH_CHOICE = "User.getWithChoice";
    public static final String ALL_SORTED = "User.getAllSorted";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 64)
    private String password;

    @Column(name = "registered", columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
//    @JsonIgnore
    private Restaurant choice;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(Integer id, String name, String email, String password, Role role, Role ... roles) {
        this(id, name, email, password, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password,Restaurant choice, Role role, Role ... roles) {
        this(id, name, email, password, EnumSet.of(role, roles));
        this.choice = choice;
    }

    public User(Integer id, String name, String email, String password, Restaurant choice) {
        super(id, name);
        this.password = password;
        this.email = email;
        this.choice = choice;
    }

    public User(Integer id, String name, String email, String password, Set<Role> roles) {
        super(id, name);
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
        setChoice(user.getChoice());
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

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }


    public Restaurant getChoice() {
        return choice;
    }

    public void setChoice(Restaurant choice){
        this.choice = choice;
    }



    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (roles.size() != user.roles.size()) return false;
        return roles.containsAll(user.roles);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + roles.hashCode();
        return result;
    }
}
