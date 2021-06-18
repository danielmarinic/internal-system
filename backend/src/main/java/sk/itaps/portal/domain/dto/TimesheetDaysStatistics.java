package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TimesheetDaysStatistics {
    private Integer workedDays;
    private Integer workDays;
    private Integer totalDays;
    private Double timeTotal;
}
