package sk.itaps.portal.service;

import com.microsoft.graph.models.Group;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.multipart.MultipartFile;
import sk.itaps.portal.domain.dto.UserDefaultDto;
import sk.itaps.portal.domain.dto.UserRegistrationDto;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.jpa.UserProfile;

import java.util.List;

public interface UserService {
    public void synchronizeAdUsers();
    public List<User> getUsers();
    public User getUserById(int id);
    public byte[] getUserPhoto(String id);
    public void uploadUserPhoto(MultipartFile photo, String id);
    public User register(UserRegistrationDto user);
    public List<Group> getAdGroup();
    public List<User> getAllUserById(List<UserDefaultDto> users);
    public User getCurrentUser(OAuth2User principal);
    public UserProfile updateProfile(int userId, UserProfile userProfile);
    public UserProfile getUserProfile(int id);
//    public void register(User user, com.microsoft.graph.models.User adUser);
}
