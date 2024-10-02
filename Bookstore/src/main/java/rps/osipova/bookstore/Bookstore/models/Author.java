package rps.osipova.bookstore.Bookstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "author")
public class Author extends GenericEntity{

    @NotEmpty(message = "Author FIO should not be empty")
    private String fio;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Product> productList;

}
