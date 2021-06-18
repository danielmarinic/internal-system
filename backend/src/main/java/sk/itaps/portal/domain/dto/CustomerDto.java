package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CustomerDto {
    private Integer id;
    @NotNull
    private String name;
    private String nameShort;
    private String registrationNo;
    private String vatNo;
    private String icDph;
    private AddressDto address;
    private String registration;
    private String registrationSection;
}
