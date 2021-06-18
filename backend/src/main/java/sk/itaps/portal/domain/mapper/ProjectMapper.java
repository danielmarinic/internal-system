package sk.itaps.portal.domain.mapper;

import org.springframework.stereotype.Component;
import sk.itaps.portal.domain.dto.*;
import sk.itaps.portal.domain.jpa.Customer;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.ProjectType;
import sk.itaps.portal.domain.jpa.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public Project projectDetailPostDtoToProject(ProjectDetailPostDto projectDetailPostDto, List<User> leaders, Customer customer) {
       Project project = new Project();
//       project.setId(projectDetailPostDto.getId());
       project.setName(projectDetailPostDto.getName());
       project.setType(ProjectType.valueOf(projectDetailPostDto.getType().getId()));
       project.setLeaders(leaders);
       project.setCustomer(customer);
       project.setNote(projectDetailPostDto.getNote());
       project.setContactToCustomer(projectDetailPostDto.getContactToCustomer());
       project.setInternal(projectDetailPostDto.getInternal());
       project.setSendNotification(projectDetailPostDto.getNotify());
       project.setNotificationEmails(projectDetailPostDto.getNotiftyEmails());
       project.setRequireMen(projectDetailPostDto.getRequireMen());
       project.setRequireHours(projectDetailPostDto.getRequireHours());
       project.setUrlProposal(projectDetailPostDto.getUrlProposal());
       project.setUrlDeliveryAccept(projectDetailPostDto.getUrlDeliveryAccept());
       project.setNote(projectDetailPostDto.getNote());
       return project;
    }

    public Project projectDetailPostDtoToProject(ProjectDetailPostDto projectDetailPostDto) {
        Project project = new Project();
//       project.setId(projectDetailPostDto.getId());
        project.setName(projectDetailPostDto.getName());
        project.setType(ProjectType.valueOf(projectDetailPostDto.getType().getId()));
        List<User> leaders = new ArrayList<User>();
        projectDetailPostDto.getLeaders().forEach(userDefaultDto -> {
            User user = new User();
            user.setId(userDefaultDto.getId());
            leaders.add(user);
        });
        project.setLeaders(leaders);
        Customer customer = new Customer();
        customer.setId(projectDetailPostDto.getCustomer().getId());
        project.setCustomer(customer);
        project.setNote(projectDetailPostDto.getNote());
        project.setContactToCustomer(projectDetailPostDto.getContactToCustomer());
        project.setInternal(projectDetailPostDto.getInternal());
        project.setSendNotification(projectDetailPostDto.getNotify());
        project.setNotificationEmails(projectDetailPostDto.getNotiftyEmails());
        project.setRequireMen(projectDetailPostDto.getRequireMen());
        project.setRequireHours(projectDetailPostDto.getRequireHours());
        project.setUrlProposal(projectDetailPostDto.getUrlProposal());
        project.setUrlDeliveryAccept(projectDetailPostDto.getUrlDeliveryAccept());
        return project;
    }

    /*public ProjectDetailPostDto projectToProjectDetailPostDto(Project project) {
        ProjectDetailPostDto projectDetailPostDto = new ProjectDetailPostDto();
        projectDetailPostDto.setId(project.getId());
        projectDetailPostDto.setName(project.getName());
        projectDetailPostDto.setType(project.getType() == ProjectType.BODY_LEASING ? "BODY_LEASING" : "FIX_PRICE");
//        projectDetailGetDto.setCustomer(project.getCustomer().getName());
        projectDetailPostDto.setNotify(project.isSendNotification());
        projectDetailPostDto.setNotiftyEmails(project.getNotificationEmails());
        projectDetailPostDto.setRequireHours(project.getRequireHours());
        projectDetailPostDto.setRequireMen(project.getRequireMen());
        projectDetailPostDto.setUrlProposal(project.getUrlProposal());
        projectDetailPostDto.setUrlDeliveryAccept(project.getUrlDeliveryAccept());
        return projectDetailPostDto;
    }*/

//    public ProjectDetailGetDto projectToProjectDetailGetDto(Project project) {
//        return ProjectDetailGetDto.builder()
//                .id(project.getId())
//                .name(project.getName())
////               .type(project.getType() === ProjectType.FIX_PRICE ? 1 : 2)
//                .leaders(project.getLeaders().stream().map(this::userToUserDefaultDto).collect(Collectors.toList()))
//                .customer(customerToCustomerDefaultDto(project.getCustomer()))
//                .notify(project.isSendNotification())
//                .notiftyEmails(project.getNotificationEmails())
//                .requireMen(project.getRequireMen())
//                .requireHours(project.getRequireHours())
//                .urlProposal(project.getUrlProposal())
//                .urlDeliveryAccept(project.getUrlDeliveryAccept())
//                .active(project.isActive())
////                .createdBy(userToUserDefaultDto(project.getCreatedBy()))
//                .createdAt(project.getCreatedAt())
////                .updatedBy(userToUserDefaultDto(project.getUpdatedBy()))
//                .updatedAt(project.getUpdatedAt())
//                .build();
//    }

    public ProjectDetailGetDto projectToProjectDetailGetDto(Project project) {
        ProjectDetailGetDto projectDetailGetDto = new ProjectDetailGetDto();
        projectDetailGetDto.setId(project.getId());
        projectDetailGetDto.setName(project.getName());
        projectDetailGetDto.setType(project.getType() == ProjectType.BODY_LEASING ?
                CodeListStringDto.builder()
                        .id("BODY_LEASING")
                        .description("Body Leasing")
                        .build() :
                CodeListStringDto.builder()
                        .id("FIX_PRICE")
                        .description("Fix Price")
                        .build());
        projectDetailGetDto.setLeaders(project.getLeaders()
                .stream()
                .map(this::userToUserDefaultDto)
                .collect(Collectors.toList()));
        projectDetailGetDto.setCustomer(customerToCustomerDefaultDto(project.getCustomer()));
        projectDetailGetDto.setNotify(project.isSendNotification());
        projectDetailGetDto.setNotiftyEmails(project.getNotificationEmails());
        projectDetailGetDto.setInternal(project.isInternal());
        projectDetailGetDto.setRequireHours(project.getRequireHours());
        projectDetailGetDto.setRequireMen(project.getRequireMen());
        projectDetailGetDto.setUrlProposal(project.getUrlProposal());
        projectDetailGetDto.setUrlDeliveryAccept(project.getUrlDeliveryAccept());
        projectDetailGetDto.setNote(project.getNote());

        projectDetailGetDto.setActive(project.isActive());
        projectDetailGetDto.setCreatedAt(project.getCreatedAt());
        projectDetailGetDto.setUpdatedAt(project.getUpdatedAt());
        return projectDetailGetDto;
    }

    public UserDefaultDto userToUserDefaultDto(User user) {
        return UserDefaultDto.builder()
                .id(user.getId())
                .name(user.getUserProfile().getFirstName() + ' ' + user.getUserProfile().getSurname())
//                .photo("http://localhost:8080/user/"+user.getObjectId()+"/photo")
                .photo("https://reqres.in/img/faces/1-image.jpg")
                .build();
    }

    public CustomerDefaultDto customerToCustomerDefaultDto(Customer customer) {
        return CustomerDefaultDto.builder()
                .id(customer.getId())
                .nameShort(customer.getNameShort())
                .name(customer.getName())
                .build();
    }

    public CodeListDto projectToCodeListDto(Project project) {
        return CodeListDto.builder()
                .id(project.getId())
                .description(project.getName())
                .build();
    }
}
