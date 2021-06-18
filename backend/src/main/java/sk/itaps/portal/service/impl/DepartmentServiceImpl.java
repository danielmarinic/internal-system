package sk.itaps.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.itaps.portal.domain.dto.DepartmentPostDto;
import sk.itaps.portal.domain.jpa.Department;
import sk.itaps.portal.domain.jpa.DepartmentGroup;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.repository.DepartmentGroupRepository;
import sk.itaps.portal.repository.DepartmentRepository;
import sk.itaps.portal.service.DepartmentService;
import sk.itaps.portal.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentGroupRepository departmentGroupRepository;

    @Autowired
    private UserService userService;

    @Autowired
    Mapper mapper;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(int id) {
        return departmentRepository.findDepartmentById(id);
    }

    public Department create(DepartmentPostDto departmentPostDto) {
        User manager = userService.getUserById(departmentPostDto.getManager().getId());
        List<DepartmentGroup> departmentGroups =  departmentGroupRepository.findAllByGroupIdIn(departmentPostDto.getGroups().stream().map(groupDto -> { return groupDto.getId();}).collect(Collectors.toList()));
        return departmentRepository.save(mapper.departmentDtoToDepartment(departmentPostDto, manager, departmentGroups));
    }

    public Department update(int id, DepartmentPostDto departmentPostDto) {
        User manager = userService.getUserById(departmentPostDto.getManager().getId());
        List<DepartmentGroup> departmentGroups =  departmentGroupRepository.findAllByGroupIdIn(departmentPostDto.getGroups().stream().map(groupDto -> { return groupDto.getId();}).collect(Collectors.toList()));
        Department department = mapper.departmentDtoToDepartment(departmentPostDto, manager, departmentGroups);
        department.setId(id);
        return departmentRepository.save(department);
    }

    public void delete(int id) {
        departmentRepository.deleteById(id);
    }

    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    public List<DepartmentGroup> getDepartmentGroups(int id) {
        return departmentRepository.findDepartmentById(id).getAssignedGroups();
    }

    public List<User> getDepartmentMembers(int id) {
        return departmentRepository.findById(id).get().getMembers();
    }

    public List<User> addDepartmentMembers(int id, List<User> members) {
        Department department = departmentRepository.findDepartmentById(id);
        department.getMembers().addAll(members);
        return departmentRepository.save(department).getMembers();
    }
    public List<User> deleteDepartmentMember(int id, int memberId) {
        Department department = departmentRepository.findDepartmentById(id);
        User member = userService.getUserById(memberId);
        department.getMembers().remove(member);
        return departmentRepository.save(department).getMembers();
    }
}
