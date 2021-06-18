package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DepartmentDto {
    private Integer id;
    private String name;
    private UserDefaultDto manager;
    private List<GroupDto> assignedGroups;
    private Integer count_users;
    private Integer count_groups;
}
