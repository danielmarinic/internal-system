package sk.itaps.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.itaps.portal.domain.dto.ProjectDetailPostDto;
import sk.itaps.portal.domain.jpa.Customer;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.domain.mapper.ProjectMapper;
import sk.itaps.portal.repository.ProjectRepository;
import sk.itaps.portal.service.CustomerService;
import sk.itaps.portal.service.ProjectService;
import sk.itaps.portal.service.UserService;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getAllActiveProjects() {
        return projectRepository.findProjectsByActive(1);
    }

    public List<Project> getAllHistoryProjects() {
        return projectRepository.findProjectsByActive(0);
    }

    public Project findById(int id) {
        return projectRepository.findById(id).get();
    }

    public Project saveOrUpdate(Project project) {
        return projectRepository.save(project);
    }

    public Project create(ProjectDetailPostDto projectDetailPostDto) {
        Customer customer = customerService.findById(projectDetailPostDto.getCustomer().getId());
        List<User> leaders = userService.getAllUserById(projectDetailPostDto.getLeaders());

        //todo: notificationEmails save
        return projectRepository.save(projectMapper.projectDetailPostDtoToProject(projectDetailPostDto, leaders, customer));
    }

    public Project update(Project project, int id) {
        project.setId(id);
        return projectRepository.save(project);
    }

    public void delete(int id) {
        Project project = projectRepository.findById(id).get();
        project.setActive(false);
        projectRepository.save(project);
    }
    public List<User> getProjectUsers(int id) {
        Project project = projectRepository.findById(id).get();
        return project.getUsers();
    }

    public List<User> addProjectUsers(int id, List<User> users) {
        Project project = projectRepository.findById(id).get();
        project.getUsers().addAll(users);
        return projectRepository.save(project).getUsers();
    }

    public List<User> deleteProjectUser(int id, int userId) {
        Project project = projectRepository.findById(id).get();
        User user = userService.getUserById(userId);
        project.getUsers().remove(user);
        return projectRepository.save(project).getUsers();
    }

    public List<User> getProjectLeaders(int id) {
        return projectRepository.findById(id).get().getLeaders();
    }

    public List<User> addProjectLeaders(int id, List<User> leaders) {
        Project project = projectRepository.findById(id).get();
        project.getLeaders().addAll(leaders);
        return projectRepository.save(project).getLeaders();
    }

    public List<User> deleteProjectLeader(int id, int leaderId) {
        Project project = projectRepository.findById(id).get();
        User leader = userService.getUserById(leaderId);
        project.getLeaders().remove(leader);
        return projectRepository.save(project).getLeaders();
    }

    public List<Project> getUserProject(User user) {
        return projectRepository.findProjectsByUsers(user);
    }
}
