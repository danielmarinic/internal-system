package sk.itaps.portal.service.impl;

import com.microsoft.graph.models.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sk.itaps.portal.domain.dto.GroupDto;
import sk.itaps.portal.domain.dto.UserDefaultDto;
import sk.itaps.portal.domain.dto.UserRegistrationDto;
import sk.itaps.portal.domain.jpa.Department;
import sk.itaps.portal.domain.jpa.Position;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.jpa.UserProfile;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.repository.UserRepository;
import sk.itaps.portal.service.DepartmentService;
import sk.itaps.portal.service.MicrosoftGraphService;
import sk.itaps.portal.service.PositionService;
import sk.itaps.portal.service.UserService;
import sk.itaps.portal.utils.PasswordGenerator;

import javax.mail.SendFailedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    PositionService positionService;

    @Autowired
    Mapper mapper;

    @Autowired
    MicrosoftGraphService microsoftGraphService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public void synchronizeAdUsers() {
        List<User> adUsers = microsoftGraphService.getAllUsers()
                .stream()
                .map(mapper::adUserToJpaUser)
                .collect(Collectors.toList());

        adUsers.forEach(user -> {
            if(userRepository.findByObjectId(user.getObjectId()) == null) {
                userRepository.save(user);
            }
        });
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

//    public Page<User> findUsersPaginated(int page, int size) {
//        userRepository.find
//    }

    public byte[] getUserPhoto(String  id) {
        return microsoftGraphService.getUserPhoto(id);
    }

    public void uploadUserPhoto(MultipartFile photo, String id) {
        try {
            byte[] stream = photo.getBytes();
            microsoftGraphService.uploadPhoto(stream, id);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
//                    + ". Please try again!");
        }
    }
    //stara
    public User register(UserRegistrationDto userRegistrationDto) {
        //find codelist object
        Department department = departmentService.getDepartment(userRegistrationDto.getDepartment().getId());
        Position position = positionService.getPosition(userRegistrationDto.getPosition().getId());
        //todo: exception if not exists

        String password = generatePassword();
        com.microsoft.graph.models.User adUser = mapper.userRegistrationDtoToAdUser(userRegistrationDto, password, department, position.getName());
        adUser = microsoftGraphService.createUser(adUser);
        for (GroupDto group : userRegistrationDto.getGroups()) {
            microsoftGraphService.addUserToGroupAsync(group.getId(), adUser.id);
        }
        microsoftGraphService.assignUserManagerAsync(adUser.id, department.getManager().getObjectId());
        User userJpa = mapper.userRegistrationDtoToUser(userRegistrationDto, adUser, department, position);
        userJpa = userRepository.save(userJpa);
        try {
            sendPassword(userRegistrationDto.getAlternateEmail(), password);
        } catch (SendFailedException e) {
            System.out.println("ERROR");
            logger.error(e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR2");
            logger.error(e.getMessage());
        }
        return userJpa;
        //todo: Heslo zaslat na uzivatela
    }

//    public void register(User user, com.microsoft.graph.models.User adUser) {
//        String password = generatePassword();
//        com.microsoft.graph.models.User adUser = mapper.userRegistrationDtoToAdUser(user, password);
//        adUser = microsoftGraphService.createUser(adUser);
//        for (String group : user.getUserProfileGroups()) {
//            microsoftGraphService.addUserToGroupAsync(group, adUser.id);
//        }
//        User userJpa = mapper.adUserToJpaUser(adUser);
//        userRepository.save(userJpa);
//        //todo: Heslo zaslat na uzivatela
//    }

    public List<Group> getAdGroup() {
        return microsoftGraphService.getAllGroups();
    }

    public List<User> getAllUserById(List<UserDefaultDto> userDefaultDtos) {
        List<Integer> usersId = userDefaultDtos.stream().map(userDefaultDto -> { return userDefaultDto.getId(); }).collect(Collectors.toList());
        return userRepository.findUsersByIdIn(usersId);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public User getCurrentUser(OAuth2User principal) {
        return userRepository.findByObjectId(principal.getAttribute("oid"));
    }

    public UserProfile updateProfile(int userId, UserProfile userProfile) {
        User user = userRepository.findById(userId).get();
        userProfile.setId(user.getUserProfile().getId());
        user.setUserProfile(userProfile);
        //todo: Groups save, AD save
        return userRepository.save(user).getUserProfile();
    }

    public UserProfile getUserProfile(int id) {
        return userRepository.findById(id).get().getUserProfile();
    }

    private String generatePassword() {
            String password = new PasswordGenerator.Builder()
                .digits(4)
                .lower(1)
                .upper(2)
                .punctuation()
                .generate(10);
            return password;
    }

    private void sendPassword(String email, String password) throws SendFailedException {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("info@portal.itaps.sk");
            msg.setTo(email);

            msg.setSubject("[ITAPS Portál] Prihlasovacie údaje");
            msg.setText("Ahoj,\nTvoje heslo na ITAPS portál je: "+password+"\nITAPS Portál\n");

            javaMailSender.send(msg);
    }
}
