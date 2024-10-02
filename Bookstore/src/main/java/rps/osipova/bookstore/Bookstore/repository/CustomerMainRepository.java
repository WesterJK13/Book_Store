package rps.osipova.bookstore.Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerMainRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByCardNumber(String cardNumber);

    @Query("SELECT c FROM Customer c WHERE c.id <> :id")
    List<Customer> findAllExcept(@Param("id") String id);

    @Query("SELECT cf FROM Customer c JOIN  c.friends cf WHERE c.id = :id")
    List<Customer> findAllFriends(@Param("id") String myId);

    @Query("SELECT c FROM Customer c JOIN c.friends cf WHERE cf.id = :id")
    List<Customer> findAllCustomerByFriends(@Param("id") String myId);

    @Query("SELECT c FROM Customer c WHERE c NOT IN (SELECT cf FROM Customer c JOIN  c.friends cf WHERE c.id = :id) AND c NOT IN (" +
            "SELECT c FROM Customer c JOIN c.friends cf WHERE cf.id = :id) AND c NOT IN (SELECT c FROM Customer c WHERE c.id = :id)")
    List<Customer> findAllCustomerNotConnected(@Param("id") String myId);
}
