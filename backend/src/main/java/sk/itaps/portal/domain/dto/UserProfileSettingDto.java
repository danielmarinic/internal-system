package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserProfileSettingDto {
    private CodeListDto department;
    private CodeListDto position;
    private UserDefaultDto manager;
    private CodeListDto type;
    private List<GroupDto> groups;
}
