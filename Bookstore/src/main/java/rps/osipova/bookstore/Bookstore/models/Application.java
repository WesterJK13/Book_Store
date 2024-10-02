package rps.osipova.bookstore.Bookstore.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "application")
public class Application extends GenericEntity {

    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private Customer applicant;

    @ManyToOne
    @JoinColumn(name = "future_friend_id", referencedColumnName = "id")
    private Customer futureFriend;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;

}
