package myapps.bookloan_app.model;

import jakarta.persistence.*;
import myapps.bookloan_app.core.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    /* ---------- PERSONAL INFO ---------- */

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    /* ---------- ROLE ---------- */

    @Enumerated(EnumType.STRING)
    private Role role;

    /* ---------- WISHLIST ---------- */

    @ManyToMany
    @JoinTable(
            name = "user_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> wishlist = new HashSet<>();

    public User() {}

    /* ---------- getters & setters ---------- */

    public Long getId() {
        return id;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /* ---------- wishlist helpers ---------- */

    public Set<Book> getWishlist() {
        return wishlist;
    }

    public void addToWishlist(Book book) {
        wishlist.add(book);
    }

    public void removeFromWishlist(Book book) {
        wishlist.remove(book);
    }

    public boolean hasInWishlist(Book book) {
        return wishlist.contains(book);
    }
}
