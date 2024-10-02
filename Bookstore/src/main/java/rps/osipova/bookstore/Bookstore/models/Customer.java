package rps.osipova.bookstore.Bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import rps.osipova.bookstore.Bookstore.repository.CustomerMainRepository;
import rps.osipova.bookstore.Bookstore.repository.RegistrAndAuth.CustomerRepository;
import rps.osipova.bookstore.Bookstore.service.CustomerService;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Data
@Entity
@Table(name = "customers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer extends User {

    @NotEmpty(message = "Phone shouldn't be empty")
    @Column(name = "phone")
    private String phone;

    @NotEmpty(message = "Card number shouldn't be empty")
    @Column(name = "card_number")
    private String cardNumber;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> favourites;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libraries",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> libraries;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "baskets",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> basket;

    /**
     * При создании таблицы настроить так, чтобы связь была симметричной, т.е. не должно быть таких записей:
     * (1 - 2)
     * (1 - 3)
     * (2 - 1)
     * (3 - 1)
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_friends",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Customer> friends;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Review> reviews;

    //Список отправленных заявок в друзья другим пользователям
    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY)
    private List<Application> createdApplicationList;

    //Список полученных заявок в друзья от других пользователей
    @OneToMany(mappedBy = "futureFriend", fetch = FetchType.LAZY)
    private List<Application> receivedApplicationList;


    public Customer(String email, String password, Role role, String name, String surname, String patronymic, Date birthDate,
                    String phone, String cardNumber) {
        super(email, password, role, name, surname, patronymic, birthDate);
        this.phone = phone;
        this.cardNumber = cardNumber;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer: id = " + getId() + " name = " + getName() + " surname = " + getSurname();
    }

}
