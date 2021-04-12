package pl.karpiozaury.Tuziemiec_api.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long Id;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false, updatable = false)
    @Email
    private String email;

    @Column(name = "password", nullable = false, updatable = false)
    private String password;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "dayOfBirth")
    private LocalDate dayOfBirth;

    @Column(name = "age")
    @Transient
    private int age;

    @Column(name = "active")
    private Boolean active;
//----------------------------------------------------------------------------------------------------------------------

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role>  roles;

    public User(String userName, @Email String email, String password, String firstName, String lastName, LocalDate dayOfBirth) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
    }

    public Integer getAge() {
        return Period.between(this.dayOfBirth, LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }
}
