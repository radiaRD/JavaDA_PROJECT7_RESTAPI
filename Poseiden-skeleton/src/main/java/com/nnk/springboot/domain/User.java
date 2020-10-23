package com.nnk.springboot.domain;

        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;

        import javax.persistence.*;
        import javax.validation.constraints.NotBlank;
        import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final Logger logger = LogManager.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User() {
    }

    public User( @NotBlank(message = "Username is mandatory") String username, @NotBlank(message = "Password is mandatory") String password, @NotBlank(message = "FullName is mandatory") String fullname, @NotBlank(message = "Role is mandatory") String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
