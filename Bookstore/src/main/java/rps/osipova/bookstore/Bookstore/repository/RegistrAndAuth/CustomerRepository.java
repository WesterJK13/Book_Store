package rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.User;

import java.util.Optional;

@Repository
@Primary
public interface CustomerRepository extends UserRepository {

    Optional<User> findByEmail(String email);
}
