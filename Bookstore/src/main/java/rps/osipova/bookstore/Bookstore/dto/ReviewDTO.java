package rps.osipova.bookstore.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDTO {

    private String commentForAll;

    private String commentForFriends;

    private Date reviewDateForAll;

    private Date reviewDateForFriends;

    private ProductDTO product;

    private CustomerDTO customer;
}
