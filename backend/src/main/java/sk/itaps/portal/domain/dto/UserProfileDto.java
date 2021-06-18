package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserProfileDto {
    private Integer id;
    @NotNull
    private String firstname;
    @NotNull
    private String surname;
    private String altEmail;
    private String phone;
    private String birthday;
    private String birthplace;
    private AddressDto address;
    private UserProfileSettingDto setting;
}
