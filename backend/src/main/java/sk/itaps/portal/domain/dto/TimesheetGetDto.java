package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class TimesheetGetDto {
    private Integer id;
    private Integer userId;
    private String day;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime end;
    private Double time;
    private String note;
}
