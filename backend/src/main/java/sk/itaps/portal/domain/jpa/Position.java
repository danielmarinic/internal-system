package sk.itaps.portal.domain.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
//@Builder
@NoArgsConstructor
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany
    private List<User> users;
}
