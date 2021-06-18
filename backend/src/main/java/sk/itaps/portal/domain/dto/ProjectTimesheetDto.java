package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectTimesheetDto {
    private Integer id;
    private String name;
    private Double time;
}
