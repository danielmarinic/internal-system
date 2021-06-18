package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DepartmentPostDto {
    private String name;
    private UserDefaultDto manager;
    private List<GroupDto> groups;
    private List<UserDefaultDto> members;
}
