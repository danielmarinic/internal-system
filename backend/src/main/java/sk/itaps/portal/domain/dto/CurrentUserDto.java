package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrentUserDto {
    private String objectId;
    private String name;
    private String email;
}
