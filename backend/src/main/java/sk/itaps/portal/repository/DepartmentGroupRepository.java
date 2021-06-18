package sk.itaps.portal.repository;

import org.springframework.data.repository.CrudRepository;
import sk.itaps.portal.domain.jpa.DepartmentGroup;

import java.util.List;

public interface DepartmentGroupRepository extends CrudRepository<DepartmentGroup, Integer> {
    @Override
    public List<DepartmentGroup> findAll();
    public List<DepartmentGroup> findAllByGroupIdIn(List<String> group);
}
