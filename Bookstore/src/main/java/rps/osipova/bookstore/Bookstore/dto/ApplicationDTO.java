package rps.osipova.bookstore.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rps.osipova.bookstore.Bookstore.models.ApplicationStatus;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDTO {

    public String id;

    public CustomerDTO applicant;

    public CustomerDTO futureFriend;

    public ApplicationStatus applicationStatus;
}
