package sk.itaps.portal.service;

import sk.itaps.portal.domain.dto.DepartmentPostDto;
import sk.itaps.portal.domain.jpa.Department;
import sk.itaps.portal.domain.jpa.DepartmentGroup;
import sk.itaps.portal.domain.jpa.User;

import java.util.List;

public interface DepartmentService {
    public List<Department> getDepartments();
    public Department getDepartment(int id);
    public Department create(DepartmentPostDto department);
    public Department update(int id, DepartmentPostDto departmentPostDto);
    public void delete(int id);
    public void delete(Department department);
    public List<DepartmentGroup> getDepartmentGroups(int id);
    public List<User> getDepartmentMembers(int id);
    public List<User> addDepartmentMembers(int id, List<User> members);
    public List<User> deleteDepartmentMember(int id, int memberId);
}
