package rps.osipova.bookstore.Bookstore.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "reviews")
public class Review extends GenericEntity{

    @Column(name = "comment_for_all")
    private String commentForAll;

    @Column(name = "comment_for_friends")
    private String commentForFriends;

    @Column(name = "review_date_for_all")
    private Date reviewDateForAll;

    @Column(name = "review_date_for_friends")
    private Date reviewDateForFriends;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
