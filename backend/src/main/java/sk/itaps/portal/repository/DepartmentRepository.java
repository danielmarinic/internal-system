package sk.itaps.portal.repository;

import org.springframework.data.repository.CrudRepository;
import sk.itaps.portal.domain.jpa.Department;
import sk.itaps.portal.domain.jpa.User;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    public Department findDepartmentById(Integer id);
    @Override
    public List<Department> findAll();
}
