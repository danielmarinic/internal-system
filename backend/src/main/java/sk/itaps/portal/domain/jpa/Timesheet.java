package sk.itaps.portal.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Project project;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date workDate;
    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime workDateStart;
    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime workDateEnd;
    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime timeWorked;
    private String note;

//    @CreatedDate
//    @Column(columnDefinition = "TIMESTAMP")
//    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;

    /*@PrePersist
    private void setTimeWorked() {
        Duration duration = Duration.between(this.workDateStart, this.workDateEnd);
        this.timeWorked = LocalTime.of(duration.toMinutesPart()/60, duration.toMinutesPart()%60);
    }*/
}
