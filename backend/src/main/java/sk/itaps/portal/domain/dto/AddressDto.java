package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
public class AddressDto {
    @NotNull
    private String country;
    @NotNull
    private String city;
//    @NotNull
    private String street;
    @NotNull
    @Positive
    private String number;
    @NotNull
    private String postalCode;
}
