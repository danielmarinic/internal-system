package sk.itaps.portal;

import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.repository.ProjectRepository;
import sk.itaps.portal.repository.TimesheetRepository;
import sk.itaps.portal.repository.UserRepository;
import sk.itaps.portal.service.MicrosoftGraphService;
import sk.itaps.portal.service.UserService;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ItapsPortalApplicationTests {

    @Autowired
    MicrosoftGraphService microsoftGraphService;

    @Autowired
    UserService userService;

    @Autowired
    Mapper mapper;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TimesheetRepository timesheetRepository;

    @Test
    void contextLoads() {

    }

    @Test
    void checkGetUsersFromGraph() {
        List<User> users = microsoftGraphService.getAllUsers();
        users.forEach(user -> {
            System.out.println(user.displayName);
        });
    }

    @Test
    void tryCreateUser() {
        User user = new User();
        user.givenName = "Peter";
        user.surname = "Kmeť";
        user.accountEnabled = true;
        user.displayName = "Peter Kmeť";
        user.mailNickname = "Kmet1";
        user.userPrincipalName = "peter.kmet@danoportal.onmicrosoft.com";
        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.forceChangePasswordNextSignIn = true;
        passwordProfile.password = "BB11bb11*";
        user.passwordProfile = passwordProfile;
        microsoftGraphService.createUser(user);
    }

    @Test
    void checkCreatedUser() {
        System.out.println(microsoftGraphService.findUser("miroslav.kohut@danoportal.onmicrosoft.com"));
    }

    @Test
    void syncUsersFromAd() {
        userService.synchronizeAdUsers();
    }

    @Test
    void getMapAllAdUser() {
        List<User> adUsers = microsoftGraphService.getAllUsers();
        List<sk.itaps.portal.domain.jpa.User> users = adUsers.stream().map(mapper::adUserToJpaUser).collect(Collectors.toList());
        users.forEach(user -> {
            System.out.println(user.getUserProfile());
        });
    }

    @Test
    void checkLocalTimeDiff() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalTime startTime = LocalTime.parse("2021-05-24 14:00", formatter);
        LocalTime endTime = LocalTime.parse("2021-05-24 18:00", formatter);
        Duration duration = Duration.between(startTime, endTime);

        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(duration.toMinutes());
        System.out.println(LocalTime.of((int)duration.toMinutes()/60, (int)duration.toMinutes()%60));
    }

//    @Test
//    void getProjectTimesheet() {
//        try {
//            List<ProjectTimesheet> projectTimesheets = projectRepository.findProjectTimesheetByUserAndMonth(1);
//            projectTimesheets.forEach(projectTimesheet -> {
//                System.out.println(projectTimesheet);
//            });
//        } catch (Exception e) {
//
//        }
//    }

//    @Test
//    public void getTimesheet() {
//        projectRepository.getProjectTimesheet().forEach(projectTimesheet -> {
//                    System.out.println(projectTimesheet);
//                }
//        );
//    }

    @Test
    void getProjectTime() {
        timesheetRepository.getProjectTime().forEach(projectTimesheet -> {
            System.out.println(projectTimesheet.getId());
            System.out.println(projectTimesheet.getProjectName());
            System.out.println(projectTimesheet.getProjectTime());
        });
    }

    @Test
    void getUserProjectTimeByMonth() {
        try {
            timesheetRepository.getUserProjectTimeByDate(1,
                    new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-01"),
                    new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-31"))
                    .forEach(projectTimesheet -> {
                System.out.println(projectTimesheet.getId());
                System.out.println(projectTimesheet.getProjectName());
                System.out.println(projectTimesheet.getProjectTime());
            });
        } catch (Exception e) {
            //todo: Error handling
        }
    }

    @Test
    void checkAddressAdUsers() {
        List<User> adUsers = microsoftGraphService.getAllUsers();
        adUsers.forEach(user -> {
            System.out.println(user.country);
            System.out.println(user.city);
            System.out.println(user.streetAddress);
            System.out.println(user.postalCode);
        });
    }

    @Test
    void addUserToGroup() {
        microsoftGraphService.addUserToGroup("efe4c812-b2aa-40b2-ba1b-9f85cac30a6f", "73c97f9b-1c2d-4e3f-b416-7340635d4f99");
    }

    /*
    @Test
    void testOrsr() {
        System.out.println(orsrService.getCompany("46326570")[0].getName());
    }*/

//    @Test
//    void testMapper() {
//        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
//                .userProfileFirstName("Mapper")
//                .userProfileSurname("Test")
//                .userProfileAlternateEmail("mapper@gmail.com")
//                .userProfilePhoneNo("+421915388243")
//                .userProfileDepartmentId(1)
//                .userProfilePositionId(1)
//                .userProfileGroups(Arrays.asList("efe4c812-b2aa-40b2-ba1b-9f85cac30a6f"))
//                .build();
//        sk.itaps.portal.domain.jpa.User user = modelMapper.map(userRegistrationDto, sk.itaps.portal.domain.jpa.User.class);
//        userRepository.save(user);
//    }
}
