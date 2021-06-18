package sk.itaps.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import sk.itaps.portal.domain.dto.*;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.domain.mapper.ProjectMapper;
import sk.itaps.portal.service.ProjectService;
import sk.itaps.portal.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    Mapper mapper;

    @GetMapping
    @ResponseBody
    public List<ProjectDetailGetDto> getAllProjects() {
        return projectService.getAllProjects()
                .stream()
                .map(projectMapper::projectToProjectDetailGetDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDetailGetDto createProject(@RequestBody ProjectDetailPostDto projectDetailPostDto) {
        return projectMapper.projectToProjectDetailGetDto(projectService.create(projectDetailPostDto));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProjectDetailGetDto getProject(@PathVariable int id) {
        return projectMapper.projectToProjectDetailGetDto(projectService.findById(id));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ProjectDetailGetDto updateProject(@PathVariable int id, @RequestBody ProjectDetailPostDto projectDetailPostDto) {
        return projectMapper.projectToProjectDetailGetDto(projectService
        .update(projectMapper.projectDetailPostDtoToProject(projectDetailPostDto), id));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable int id) {
        projectService.delete(id);
    }

    @GetMapping("/{id}/user")
    @ResponseBody
    public List<UserDto> getProjectMembers(@PathVariable int id) {
        return projectService.getProjectUsers(id).stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<UserDto> addProjectUsers(@PathVariable int id, @RequestBody List<UserDefaultDto> users) {
        return projectService.addProjectUsers(id,
                users.stream()
                        .map(mapper::userDefaultDtoToUserId)
                        .collect(Collectors.toList()))
                .stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}/user/{userId}")
    @ResponseBody
    public List<UserDto> deleteProjectUser(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        return projectService.deleteProjectUser(id, userId).stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/leader")
    @ResponseBody
    public List<UserDto> getProjectLeaders(@PathVariable int id) {
        return projectService.getProjectLeaders(id).stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/leader")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<UserDto> addProjectLeaders(@PathVariable int id, @RequestBody List<UserDefaultDto> leaders) {
        return projectService.addProjectLeaders(id,
                leaders.stream()
                        .map(mapper::userDefaultDtoToUserId)
                        .collect(Collectors.toList())).stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}/leader/{leaderId}")
    @ResponseBody
    public List<UserDto> deleteProjectLeader(@PathVariable("id") int id, @PathVariable("leaderId") int leaderId) {
        return projectService.deleteProjectLeader(id, leaderId).stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/me")
    @ResponseBody
    public List<CodeListDto> getUserProject(@AuthenticationPrincipal OAuth2User principal) {
        User user = new User();
        user.setId(userService.getCurrentUser(principal).getId());
        return projectService.getUserProject(user)
                .stream()
                .map(projectMapper::projectToCodeListDto)
                .collect(Collectors.toList());
    }
}
