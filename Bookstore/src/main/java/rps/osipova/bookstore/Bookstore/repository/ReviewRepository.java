package rps.osipova.bookstore.Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.Customer;
import rps.osipova.bookstore.Bookstore.models.Product;
import rps.osipova.bookstore.Bookstore.models.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findAllByProduct(Product product);

    List<Review> findAllByProductAndCustomer(Product product, Customer customer);
}
