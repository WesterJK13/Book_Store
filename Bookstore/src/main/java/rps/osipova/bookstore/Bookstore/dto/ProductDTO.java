package rps.osipova.bookstore.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private String id;

    private String name;

    private String definition;

    private int price;

    private int quantity;

    private boolean availability;

    private String link;

    private List<AuthorDTO> authors;
}
