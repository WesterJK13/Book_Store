package rps.osipova.bookstore.Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rps.osipova.bookstore.Bookstore.models.Application;
import rps.osipova.bookstore.Bookstore.models.ApplicationStatus;
import rps.osipova.bookstore.Bookstore.models.Customer;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findAllByFutureFriend(Customer customer);
    List<Application> findAllByFutureFriendAndApplicationStatus(Customer customer, ApplicationStatus applicationStatus);

}
