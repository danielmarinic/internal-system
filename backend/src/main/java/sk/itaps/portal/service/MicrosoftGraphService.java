package sk.itaps.portal.service;

import com.microsoft.graph.models.Group;
import com.microsoft.graph.models.User;

import java.util.List;

public interface MicrosoftGraphService {
    public List<User> getAllUsers();
    public User findUser(String id);
    public User createUser(User user);
    public void updateUser(User user);
    public byte[] getUserPhoto(String id);
    public void uploadPhoto(byte[] stream, String id);
    public List<Group> getAllGroups();
    public void addUserToGroup(String group, String user);
    public void addUserToGroupAsync(String group, String user);
    public void assignUserManager(String userId, String managerId);
    public void assignUserManagerAsync(String userId, String managerId);
}
