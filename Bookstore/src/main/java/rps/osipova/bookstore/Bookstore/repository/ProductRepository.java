package rps.osipova.bookstore.Bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.Product;
import rps.osipova.bookstore.Bookstore.utils.CommonSpecifications;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

    default Page<Product> findAll(Product product, Pageable pageable) {
        return findAll(CommonSpecifications.nameContains(product.getName()), pageable);
    }
}
