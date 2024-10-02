package rps.osipova.bookstore.Bookstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends GenericEntity {

    @NotEmpty(message = "Email shouldn't be empty")
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password shouldn't be empty")
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @NotEmpty(message = "Name shouldn't be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Surname shouldn't be empty")
    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birth_date")
    private Date birthDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public User(String email, String password, Role role, String name, String surname, String patronymic, Date birthDate) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public User() {
    }
}
