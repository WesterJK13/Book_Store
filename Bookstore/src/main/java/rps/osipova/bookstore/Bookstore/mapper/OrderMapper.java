package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import rps.osipova.bookstore.Bookstore.dto.OrderDTO;
import rps.osipova.bookstore.Bookstore.models.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    List<Order> transformFromDTO(List<OrderDTO> orderDTOList);
    List<OrderDTO> transform(List<Order> orderList);

    OrderDTO transform(Order order);
    Order transform(OrderDTO orderDTO);
}
