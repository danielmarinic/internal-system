package sk.itaps.portal.domain.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "[user]", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String objectId;
    private String email;
//    private String password;
    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @CreatedDate
    private Date createdAt;
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
}
