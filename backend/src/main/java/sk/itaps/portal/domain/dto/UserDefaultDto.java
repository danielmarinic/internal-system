package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDefaultDto {
    private Integer id;
    private String name;
    private String photo;
}
