package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DepartmentLeaderDto {
    private int id;
    private String objectId;
    private String photo;
    private String name;
}
