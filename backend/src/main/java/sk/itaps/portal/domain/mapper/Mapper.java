package sk.itaps.portal.domain.mapper;

import com.microsoft.graph.models.Group;
import com.microsoft.graph.models.PasswordProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import sk.itaps.portal.domain.dto.*;
import sk.itaps.portal.domain.jpa.*;

import java.sql.Date;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    @Value("${company.domain}")
    private String domain;
    @Value("${company.name}")
    private String company;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public User adUserToJpaUser(com.microsoft.graph.models.User user) {
        User userJpa = new User();
        userJpa.setObjectId(user.id);
        userJpa.setEmail(user.userPrincipalName);
        userJpa.setUserProfile(adUserToJpaUserProfile(user));
        return userJpa;
    }

    public UserProfile adUserToJpaUserProfile(com.microsoft.graph.models.User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(user.givenName);
        userProfile.setSurname(user.surname);
        userProfile.setPersonalNo(user.mobilePhone);
        userProfile.setBirthdate(user.birthday != null ? convertToDateViaSqlDate(user.birthday.toLocalDate()) : null);
        userProfile.setUserType(user.userType != null && user.userType.equalsIgnoreCase("member") ? UserType.MEMBER : UserType.GUEST);
        userProfile.setAddress(adUserToJpaAddress(user));
        userProfile.setAlternateEmail(user.mail);
        userProfile.setCompany(user.companyName);
        return userProfile;
    }

    public Address adUserToJpaAddress(com.microsoft.graph.models.User user) {
        Address address = new Address();
        address.setCountry(user.country);
        address.setCity(user.city);
        address.setStreet(user.streetAddress);
        address.setPostalCode(user.postalCode);
        return address;
    }

    public com.microsoft.graph.models.User userRegistrationDtoToAdUser(UserRegistrationDto userDto, String password, Department department, String position) {
        String fullname = userDto.getFirstname() + " " + userDto.getSurname();
        com.microsoft.graph.models.User newUser = new com.microsoft.graph.models.User();
        newUser.givenName = userDto.getFirstname();
        newUser.surname = userDto.getSurname();
        newUser.displayName = fullname;
        newUser.mailNickname = stripAccents(fullname.replace(" ", "."));
        newUser.userPrincipalName = newUser.mailNickname.toLowerCase() + "@" + domain;
        newUser.otherMails = new ArrayList<String>();
        newUser.otherMails.add(userDto.getAlternateEmail());
        newUser.mobilePhone = userDto.getPhoneNo();
        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.forceChangePasswordNextSignIn = true;
        passwordProfile.password = password;
        newUser.passwordProfile = passwordProfile;
        newUser.department = department.getName();
        newUser.jobTitle = position;
        newUser.companyName = company;
        newUser.accountEnabled = true;
        return newUser;
    }

    public UserProfile userRegistrationDtoToUserProfile(UserRegistrationDto userRegistrationDto, Department department, Position position) {
        UserProfile userProfile = new UserProfile();
        userProfile.setCompany(company);
        userProfile.setUserType(UserType.MEMBER);
        userProfile.setAlternateEmail(userRegistrationDto.getAlternateEmail());
        userProfile.setPhoneNo(userRegistrationDto.getPhoneNo());
        userProfile.setFirstName(userRegistrationDto.getFirstname());
        userProfile.setSurname(userRegistrationDto.getSurname());
        userProfile.setDepartment(department);
        userProfile.setPosition(position);
        userProfile.setManager(department.getManager());
        return userProfile;
    }

//    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {
//        User newUser = modelMapper.map(userRegistrationDto, User.class);
//        return newUser;
//    }

    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto, com.microsoft.graph.models.User adUser, Department department, Position position) {
        User userJpa = new User();
        userJpa.setObjectId(adUser.id);
        userJpa.setEmail(adUser.userPrincipalName);
        userJpa.setUserProfile(userRegistrationDtoToUserProfile(userRegistrationDto, department, position));
        return userJpa;
    }

    public com.microsoft.graph.models.User userToAdUser(User user, String password) {
        String fullname = user.getUserProfile().getFirstName() + " " + user.getUserProfile().getSurname();
        com.microsoft.graph.models.User newUser = new com.microsoft.graph.models.User();
        newUser.givenName = user.getUserProfile().getFirstName();
        newUser.surname = user.getUserProfile().getSurname();
        newUser.displayName = fullname;
        newUser.mailNickname = stripAccents(fullname.replace(" ", "."));
        newUser.userPrincipalName = newUser.mailNickname.toLowerCase() + "@" + domain;
        newUser.otherMails = new ArrayList<String>();
        newUser.otherMails.add(user.getUserProfile().getAlternateEmail());
        newUser.mobilePhone = user.getUserProfile().getPhoneNo();
        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.forceChangePasswordNextSignIn = true;
        passwordProfile.password = password;
        newUser.passwordProfile = passwordProfile;
        newUser.companyName = company;
        newUser.accountEnabled = true;
        return newUser;
    }

//    public com.microsoft.graph.models.User userRegistrationDtoToAdUser(UserRegistrationDto userDto) {
//        com.microsoft.graph.models.User adUser = new com.microsoft.graph.models.User();
//        adUser.
//    }

    public DepartmentDto departmentToDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .manager(userToUserDefaultDto(department.getManager()))
                .assignedGroups(department.getAssignedGroups()
                        .stream()
                        .map(this::departmentGroupsToGroupDto)
                        .collect(Collectors.toList()))
                .count_users(department.getMembers() == null ? 0 : department.getMembers().size())
                .count_groups(department.getAssignedGroups() == null ? 0 : department.getAssignedGroups().size())
                .build();
    }

    public CodeListDto departmentToCodeListDto(Department department) {
        return CodeListDto.builder()
                .id(department.getId())
                .description(department.getName())
                .build();
    }

    public GroupDto departmentGroupsToGroupDto(DepartmentGroup departmentGroup) {
        return GroupDto.builder()
                .id(departmentGroup.getGroupId())
                .description(departmentGroup.getName())
                .build();
    }

//    public DepartmentLeaderDto userManagerToDepartmentLeader(User manager) {
//        return DepartmentLeaderDto.builder()
//                .id(manager.getId())
//                .objectId(manager.getObjectId())
////                .photo("http://localhost:8080/user/"+manager.getObjectId()+"/photo")
//                .photo("https://reqres.in/img/faces/1-image.jpg")
//                .name(manager.getUserProfile().getFirstName() +" "+ manager.getUserProfile().getSurname())
//                .build();
//    }

    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getUserProfile().getFirstName())
                .surname(user.getUserProfile().getSurname())
                .fullname(user.getUserProfile().getFirstName() + " " + user.getUserProfile().getSurname())
                .email(user.getEmail())
                .department(user.getUserProfile().getDepartment() != null ? user.getUserProfile().getDepartment().getName() : null)
                .position(user.getUserProfile().getPosition() != null ? user.getUserProfile().getPosition().getName() : null)
//                .photo("http://localhost:8080/user/"+user.getObjectId()+"/photo")
                .photo("https://reqres.in/img/faces/1-image.jpg")
                .build();
    }

    public UserDefaultDto userToUserDefaultDto(User user) {
        return UserDefaultDto.builder()
                .id(user.getId())
                .name(user.getUserProfile().getFirstName() + " " + user.getUserProfile().getSurname())
                .photo("https://reqres.in/img/faces/1-image.jpg")
                .build();
    }

    public GroupDto adGroupToGroupDto(Group group) {
        return GroupDto.builder()
                .id(group.id)
                .description(group.displayName)
                .build();
    }

    public PositionDto positionToPositionDto(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .description(position.getName())
                .build();
    }

    public Notice noticeDtoToNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        try {
//            notice.setId(noticeDto.getId());
            notice.setTitle(noticeDto.getTitle());
            notice.setMessage(noticeDto.getMessage());
            notice.setLevel(noticeDto.getLevel());
            notice.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(noticeDto.getStartDate()));
            notice.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(noticeDto.getEndDate()));
        } catch (ParseException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return notice;
    }

    public CustomerDefaultDto customerToCustomerDefaultDto(Customer customer) {
        return CustomerDefaultDto.builder()
                .id(customer.getId())
                .nameShort(customer.getNameShort())
                .name(customer.getName())
                .build();
    }

    public Customer customerDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
//        customer.setId(customerDto.getId());
        customer.setNameShort(customerDto.getNameShort());
        customer.setName(customerDto.getName());
        customer.setRegistrationNo(customerDto.getRegistrationNo());
        customer.setVatNo(customerDto.getVatNo());
        customer.setIcDph(customerDto.getIcDph());
        customer.setAddress(addressDtoToAddress(customerDto.getAddress()));
        customer.setRegistration(customerDto.getRegistration());
        customer.setRegistrationSection(customerDto.getRegistrationSection());
        return customer;
    }

    public CustomerDto customerToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .nameShort(customer.getNameShort())
                .name(customer.getName())
                .registrationNo(customer.getRegistrationNo())
                .vatNo(customer.getVatNo())
                .icDph(customer.getIcDph())
                .address(addressToAddressDto(customer.getAddress()))
                .registration(customer.getRegistration())
                .registrationSection(customer.getRegistrationSection())
                .build();
    }

    public Address addressDtoToAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setNumber(addressDto.getNumber());
        address.setPostalCode(addressDto.getPostalCode());
        return address;
    }

    public AddressDto addressToAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .number(address.getNumber())
                .build();
    }

    public Department departmentDtoToDepartment(DepartmentPostDto departmentDto, User manager, List<DepartmentGroup> departmentGroupList) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setManager(manager);
        department.setAssignedGroups(departmentGroupList);
        return department;
    }

    public User userDefaultDtoToUserId(UserDefaultDto userDefaultDto) {
        User user = new User();
        user.setId(userDefaultDto.getId());
        return user;
    }

    public CustomerDto orsrDtoToCustomerDto(OrsrDto orsrDto) {
        return CustomerDto.builder()
                .name(orsrDto.getName())
                .registrationNo(orsrDto.getCin())
                .vatNo(orsrDto.getTin().toString())
                .icDph(orsrDto.getVatin())
                .address(orsrDtoToAddressDto(orsrDto))
                .registration(orsrDto.getRegistration_office())
                .registrationSection(orsrDto.getRegistration_number())
                .build();
    }

    public AddressDto orsrDtoToAddressDto(OrsrDto orsrDto) {
        return AddressDto.builder()
                .country(orsrDto.getCountry())
                .city(orsrDto.getMunicipality())
                .street(orsrDto.getStreet())
                .number(orsrDto.getStreet_number())
                .postalCode(orsrDto.getPostal_code())
                .build();
    }

    public CurrentUserDto principalToCurrentUserDto(OAuth2User principal) {
        return CurrentUserDto.builder()
                .objectId(principal.getAttribute("oid"))
                .name(principal.getAttribute("name"))
                .email(principal.getAttribute("preferred_username"))
                .build();
    }

    public UserProfile userProfileDtoToUserProfile(UserProfileDto userProfileDto) {
        UserProfile userProfile = new UserProfile();
        try {
            userProfile.setFirstName(userProfileDto.getFirstname());
            userProfile.setSurname(userProfileDto.getSurname());
            userProfile.setAlternateEmail(userProfileDto.getAltEmail());
            userProfile.setPhoneNo(userProfileDto.getPhone());
            userProfile.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(userProfileDto.getBirthday()));
            userProfile.setBirthplace(userProfileDto.getBirthplace());
            userProfile.setAddress(addressDtoToAddress(userProfileDto.getAddress()));
            userProfile = userProfileSettingDtoToUserProfile(userProfileDto.getSetting(), userProfile);
            return userProfile;
        } catch (Exception e) {
            logger.error("Chyba pri parsovani datumu. ", e);
        }
        //todo: nemozem tak robit lebo update prazdneho uzivatela
//        return userProfile;
        return null;
    }

    public UserProfile userProfileSettingDtoToUserProfile(UserProfileSettingDto userProfileSettingDto, UserProfile userProfile) {
        Department department = new Department();
        department.setId(userProfileSettingDto.getDepartment().getId());
        userProfile.setDepartment(department);
        Position position = new Position();
        position.setId(userProfileSettingDto.getPosition().getId());
        userProfile.setPosition(position);
        User manager = new User();
        manager.setId(userProfileSettingDto.getManager().getId());
        userProfile.setManager(manager);
        userProfile.setEmployeeType(codeListDtoToUserType(userProfileSettingDto.getType()));
        return userProfile;
    }

    public UserProfileDto userProfileToUserProfileDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .firstname(userProfile.getFirstName())
                .surname(userProfile.getSurname())
                .altEmail(userProfile.getAlternateEmail())
                .phone(userProfile.getPhoneNo())
                .birthday(userProfile.getBirthdate() != null ? userProfile.getBirthdate().toString() : "")
                .birthplace(userProfile.getBirthplace())
                .address(addressToAddressDto(userProfile.getAddress()))
                .setting(userProfileToUserProfileSettingDto(userProfile))
                .build();
    }

    public UserProfileSettingDto userProfileToUserProfileSettingDto(UserProfile userProfile) {
        return UserProfileSettingDto.builder()
                .department(departmentToCodeListDto(userProfile.getDepartment()))
                .position(positionToCodeListDto(userProfile.getPosition()))
                .manager(userToUserDefaultDto(userProfile.getManager()))
                .type(employeeTypeToCodeListDto(userProfile.getEmployeeType()))
                //groups
                .build();
    }

    public CodeListDto positionToCodeListDto(Position position) {
        return CodeListDto.builder()
                .id(position.getId())
                .description(position.getName())
                .build();
    }

//    public CodeListStringDto groupToCodeListStringDto()

    public EmployeeType codeListDtoToUserType(CodeListDto employeeType) {
        switch (employeeType.getId()) {
            case 1:
                return EmployeeType.PART_TIME;
            case 2:
                return EmployeeType.SELF_EMPLOYED;
            case 3:
                return EmployeeType.FULL_TIME;
        }
        return EmployeeType.PART_TIME;
    }

    public CodeListDto employeeTypeToCodeListDto(EmployeeType employeeType) {
        switch (employeeType) {
            case PART_TIME:
                return CodeListDto.builder().id(1).description("Brigádnik").build();
            case SELF_EMPLOYED:
                return CodeListDto.builder().id(2).description("Živnostnik").build();
            case FULL_TIME:
                return CodeListDto.builder().id(3).description("Zamestnanec").build();
        }
        return CodeListDto.builder().id(1).description("Brigádnik").build();
    }

    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    private static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
