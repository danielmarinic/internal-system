package sk.itaps.portal.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrsrDto {
    private Integer id;
    private String cin;
    private Integer tin;
    private String vatin;
    private String name;
    private String formatted_address;
    private String street;
    private Integer reg_number;
    private String building_number;
    private String street_number;
    private String formatted_street;
    private String postal_code;
    private String municipality;
    private String country;
    private String established_on;
    private String terminated_on;
    private String vatin_paragraph;
    private String registration_office;
    private String registration_number;
    private String updated_at;
    private String legal_form;
}
