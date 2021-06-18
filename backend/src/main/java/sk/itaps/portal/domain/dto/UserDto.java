package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private int id;
    private String firstname;
    private String surname;
    private String fullname;
    private String email;
    private String department;
    private String position;
    private String photo;
}
