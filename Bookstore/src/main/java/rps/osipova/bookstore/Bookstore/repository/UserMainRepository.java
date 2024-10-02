package rps.osipova.bookstore.Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.User;

import java.util.Optional;

@Repository
public interface UserMainRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
