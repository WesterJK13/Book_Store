package rps.osipova.bookstore.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpDTO {

    private String name;

    private String surname;

    private String patronymic;

    private String phone;

    @Email
    private String email;

    private String password;
}
