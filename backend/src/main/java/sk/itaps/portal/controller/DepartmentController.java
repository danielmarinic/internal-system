package sk.itaps.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sk.itaps.portal.domain.dto.*;
import sk.itaps.portal.domain.jpa.Department;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.service.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    Mapper mapper;

    @GetMapping()
    @ResponseBody
    public List<DepartmentDto> getDepartments() {
        List<Department> department = departmentService.getDepartments();
        return department
                .stream()
                .map(mapper::departmentToDepartmentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/list")
    @ResponseBody
    public List<CodeListDto> getDepartmentsList() {
        return departmentService.getDepartments()
                .stream()
                .map(mapper::departmentToCodeListDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public DepartmentDto getDepartment(@PathVariable int id) {
        return mapper.departmentToDepartmentDto(departmentService.getDepartment(id));
    }

    @GetMapping("/{id}/group")
    @ResponseBody
    public List<GroupDto> getGroups(@PathVariable int id) {
        return departmentService.getDepartmentGroups(id)
                .stream()
                .map(mapper::departmentGroupsToGroupDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/member")
    @ResponseBody
    public List<UserDto> members(@PathVariable int id) {
        return departmentService.getDepartmentMembers(id)
                .stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/member")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDto> addMembers(@PathVariable int id, @RequestBody List<UserDefaultDto> members) {
        return departmentService.addDepartmentMembers(id,
                members
                .stream()
                .map(mapper::userDefaultDtoToUserId)
                .collect(Collectors.toList())
        )
            .stream()
            .map(mapper::userToUserDto)
            .collect(Collectors.toList()
            );
    }

    @DeleteMapping("/{id}/member/{memberId}")
    @ResponseBody
    public List<UserDto> deleteMember(@PathVariable("id") int id, @PathVariable("memberId") int memberId) {
        return departmentService.deleteDepartmentMember(id, memberId).stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    public DepartmentDto createDepartment(@RequestBody DepartmentPostDto departmentPostDto) {
        return mapper.departmentToDepartmentDto(departmentService.create(departmentPostDto));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public DepartmentDto updateDepartment(@PathVariable int id, @RequestBody DepartmentPostDto departmentPostDto) {
        return mapper.departmentToDepartmentDto(departmentService.update(id, departmentPostDto));
    }
}
