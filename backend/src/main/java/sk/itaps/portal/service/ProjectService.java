package sk.itaps.portal.service;

import sk.itaps.portal.domain.dto.ProjectDetailPostDto;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.User;

import java.util.List;

public interface ProjectService {
    public List<Project> getAllProjects();
    public List<Project> getAllActiveProjects();
    public List<Project> getAllHistoryProjects();
    public Project findById(int id);
    public Project saveOrUpdate(Project project);
    public Project create(ProjectDetailPostDto projectDetailPostDto);
    public Project update(Project project, int id);
    public void delete(int id);
    public List<User> getProjectUsers(int id);
    public List<User> addProjectUsers(int id, List<User> users);
    public List<User> deleteProjectUser(int id, int userId);
    public List<User> getProjectLeaders(int id);
    public List<User> addProjectLeaders(int id, List<User> leaders);
    public List<User> deleteProjectLeader(int id, int leaderId);
    public List<Project> getUserProject(User user);
}
