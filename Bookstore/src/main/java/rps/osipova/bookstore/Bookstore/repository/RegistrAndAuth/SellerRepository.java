package rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth;

import rps.osipova.bookstore.Bookstore.models.User;

import java.util.Optional;

public interface SellerRepository extends UserRepository {

    Optional<User> findByEmail(String email);
}
