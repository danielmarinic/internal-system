package sk.itaps.portal.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
//@Builder
@NoArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @OneToOne(optional = false)
    private User manager;
    @ManyToMany
    private List<User> members;
    @ManyToMany
    private List<DepartmentGroup> assignedGroups;
}
