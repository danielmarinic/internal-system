package sk.itaps.portal.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDetailPostDto {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private CodeListStringDto type;
    @NotNull
    private List<UserDefaultDto> leaders;
    @NotNull
    private CustomerDefaultDto customer;

    private String contactToCustomer;
    private Boolean notify;
    private List<String> notiftyEmails;
    private String note;

    private Boolean internal;
    private Integer requireMen;
    private Double requireHours;
    private String urlProposal;
    private String urlDeliveryAccept;
}
