package rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import rps.osipova.bookstore.Bookstore.models.Role;
import rps.osipova.bookstore.Bookstore.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для наследования репозиториями Админа, Продавца, Покупателя
 */

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);
}
