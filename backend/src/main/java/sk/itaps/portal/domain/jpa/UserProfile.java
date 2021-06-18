package sk.itaps.portal.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserProfile {
    @Id
    @GeneratedValue
    private int id;

//    private String object_id;
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surname;

//    @Column(nullable = false)
    private String phoneNo;
    private String personalNo;
    private String registrationNo;
    private String iban;
    private String birthplace;
    private Date birthdate;
    private String idNo;
    @OneToOne
    private Position position;
    @OneToOne
    private Department department;
    @OneToOne
    private User manager;
    private String company;
    private String alternateEmail;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;


    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    @Enumerated(EnumType.ORDINAL)
    private EmployeeType employeeType;

//    @Column(nullable = false)
    private String photo;

    @CreatedDate
//    @Column(nullable = false)
    private Date createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    private School school;

    @OneToOne
    private Faculty faculty;

    //address

//    public UserProfile() {
//    }

//    public UserProfile(int id, String object_id, String firstName, String lastName, String phoneNo, String personalNo, String registrationNo, String iban, String birthplace, String idNo, UserType userType, Gender gender, String photo, Date createdAt, LocalDateTime updatedAt, School school, Faculty faculty) {
//        this.id = id;
//        this.object_id = object_id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNo = phoneNo;
//        this.personalNo = personalNo;
//        this.registrationNo = registrationNo;
//        this.iban = iban;
//        this.birthplace = birthplace;
//        this.idNo = idNo;
//        this.userType = userType;
//        this.gender = gender;
//        this.photo = photo;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.school = school;
//        this.faculty = faculty;
//    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", personalNo='" + personalNo + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", iban='" + iban + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", birthdate=" + birthdate +
                ", idNo='" + idNo + '\'' +
                ", position=" + position +
                ", department=" + department +
                ", manager=" + manager +
                ", company='" + company + '\'' +
                ", alternateEmail='" + alternateEmail + '\'' +
                ", address=" + address +
                ", userType=" + userType +
                ", gender=" + gender +
                ", employeeType=" + employeeType +
                ", photo='" + photo + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", school=" + school +
                ", faculty=" + faculty +
                '}';
    }
}
