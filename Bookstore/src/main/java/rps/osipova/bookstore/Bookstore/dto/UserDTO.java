package rps.osipova.bookstore.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rps.osipova.bookstore.Bookstore.models.Role;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;

    @Email
    private String email;

    private Role role;

    private String name;

    private String surname;

    private String patronymic;

    private Date birthDate;
}
