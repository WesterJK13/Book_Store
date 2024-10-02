package rps.osipova.bookstore.Bookstore.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.Customer;
import rps.osipova.bookstore.Bookstore.models.Order;
import rps.osipova.bookstore.Bookstore.models.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByTrackNumber(String trackNumber);
    Optional<Order> findByTrackNumberAndOrderStatus(String trackNumber, OrderStatus status);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findAllByCustomer(Customer customer);
    List<Order> findAllByCustomer(Customer customer, Sort sort);

}
