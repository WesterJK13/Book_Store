package rps.osipova.bookstore.Bookstore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin extends User {

}
