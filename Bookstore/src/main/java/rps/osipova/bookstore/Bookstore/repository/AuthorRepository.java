package rps.osipova.bookstore.Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

}
