package sk.itaps.portal.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String note;
    @Enumerated(EnumType.ORDINAL)
    private ProjectType type;
    @ManyToMany
    private List<User> leaders;
    @ManyToMany
    private List<User> users;
    @OneToMany
    private List<Timesheet> timesheets;
    @OneToOne
    private Customer customer;
    private String contactToCustomer;
    private boolean internal = false;
    private boolean sendNotification = false;
    @ElementCollection
    private List<String> notificationEmails;
    private Integer requireMen;
    private Double requireHours;

    private String urlProposal;
    private String urlDeliveryAccept;

    private boolean active = true;
    @CreatedDate
    private LocalDate createdAt;
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
