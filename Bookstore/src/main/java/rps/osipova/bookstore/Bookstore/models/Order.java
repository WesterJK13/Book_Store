package rps.osipova.bookstore.Bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order extends GenericEntity {

    @NotEmpty(message = "Track number shouldn't be empty")
    @Column(name = "track_number")
    private String trackNumber;

    @Column(name = "pin")
    private int pin;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "date_create")
    private Date dateCreate;

//    @ManyToOne()
//    @JoinColumn(name = "seller_id", referencedColumnName = "id")
//    private Seller seller;

    //Продавец, который собрал заказ и перевел его в статус IS_READY
    @ManyToOne()
    @JoinColumn(name = "seller_preparing_id", referencedColumnName = "id")
    private Seller sellerPreparing;

    //Продавец, который оформил заказ на кассе и перевел его в статус RECEIVED
    @ManyToOne()
    @JoinColumn(name = "seller_finished_id", referencedColumnName = "id")
    private Seller sellerFinished;

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

    @PrePersist
    private void init() {
        dateCreate = new Date();
    }
}
