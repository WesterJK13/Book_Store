package rps.osipova.bookstore.Bookstore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sellers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seller extends User {

    @OneToMany(mappedBy = "sellerPreparing", fetch = FetchType.LAZY)
    private List<Order> ordersPreparing;

    @OneToMany(mappedBy = "sellerFinished", fetch = FetchType.LAZY)
    private List<Order> ordersFinished;
}
