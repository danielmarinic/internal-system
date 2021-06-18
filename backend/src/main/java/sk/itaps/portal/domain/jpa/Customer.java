package sk.itaps.portal.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;
    private String nameShort;
    private String registrationNo;
    private String VatNo;
    private String IcDph;
    @OneToOne
    private Address address;
    private String registration;
    private String registrationSection;
}
