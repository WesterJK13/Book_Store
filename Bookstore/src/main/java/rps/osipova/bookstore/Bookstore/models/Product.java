package rps.osipova.bookstore.Bookstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product extends GenericEntity {

    @NotEmpty(message = " shouldn't be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Definition shouldn't be empty")
    @Column(name = "definition")
    private String definition;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "link")
    private String link;

    @ManyToMany(mappedBy = "favourites", fetch = FetchType.LAZY)
    private List<Customer> customerFavourites;

    @ManyToMany(mappedBy = "libraries", fetch = FetchType.LAZY)
    private List<Customer> customerLibraries;

    @ManyToMany(mappedBy = "basket", fetch = FetchType.LAZY)
    private List<Customer> customerBaskets;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> review;

    @ManyToMany(mappedBy = "productList", fetch = FetchType.LAZY)
    private List<Order> order;

    @ManyToMany()
    @JoinTable(
            name = "product_author",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

}
