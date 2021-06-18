package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@Builder
public class UserRegistrationDto {
    @NotNull
    private String firstname;
    @NotNull
    private String surname;
    @NotNull
    @Email
    private String alternateEmail;
    @NotNull
    @Min(13)
    private String phoneNo;
    @NotNull
    private DepartmentDto department;
    @NotNull
    private List<GroupDto> groups;
    @NotNull
    private PositionDto position;
}
