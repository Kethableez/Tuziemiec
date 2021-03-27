package pl.karpiozaury.Tuziemiec_api.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Column(nullable = false)
    private LocalDate dayOfBirth;
    @Transient
    private Integer age;

    // Constructors
    public User() {}

    public User(String firstName,
                String lastName,
                String email,
                String password,
                LocalDate dayOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dayOfBirth = dayOfBirth;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public Integer getAge() {
        return Period.between(this.dayOfBirth, LocalDate.now()).getYears();
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    //ToString method
    @Override
    public String toString(){
        return "User{" +
                "id=" + id + '\'' +
                ", firstName=" + firstName + '\'' +
                ", lastName=" + lastName + '\'' +
                ", email=" + email + '\'' +
                ", password=" + password + '\'' +
                ", dayOfBirth=" + dayOfBirth + '\'' +
                ", age=" + age + '\'' +
                '}';
    }

}
