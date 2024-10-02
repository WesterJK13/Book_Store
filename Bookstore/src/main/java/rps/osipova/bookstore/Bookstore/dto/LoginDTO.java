package rps.osipova.bookstore.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    private String email;
    private String password;

}