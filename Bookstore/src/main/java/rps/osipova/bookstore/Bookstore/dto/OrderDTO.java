package rps.osipova.bookstore.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rps.osipova.bookstore.Bookstore.models.OrderStatus;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String id;

    private String trackNumber;

    private int pin;

    private int totalPrice;

    private Date dateCreate;

    private SellerDTO seller;

    private CustomerDTO customer;

    private OrderStatus orderStatus;

    private List<ProductDTO> productList;

}
