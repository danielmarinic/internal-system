package sk.itaps.portal.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class School {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany
    private List<Faculty> faculty;

    @CreatedDate
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return id == school.id && Objects.equals(name, school.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
