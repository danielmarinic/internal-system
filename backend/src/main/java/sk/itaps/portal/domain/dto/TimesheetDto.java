package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TimesheetDto {
    private Integer id;
    private UserDefaultDto user;
    private CodeListDto project;
    private String workDate;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String startTime;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String endTime;
    private String note;
}
