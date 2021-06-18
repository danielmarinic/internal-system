package sk.itaps.portal.domain.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.itaps.portal.domain.dto.ProjectTimesheetDto;
import sk.itaps.portal.domain.dto.TimesheetDaysStatistics;
import sk.itaps.portal.domain.dto.TimesheetDto;
import sk.itaps.portal.domain.dto.TimesheetGetDto;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.Timesheet;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.jpa.custom.DaysStatisticsTimesheet;
import sk.itaps.portal.domain.jpa.custom.ProjectTimesheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TimesheetMapper {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(TimesheetMapper.class);

    @Autowired
    Mapper mapper;

    @Autowired
    ProjectMapper projectMapper;

    private final List<String> days = Arrays.asList("Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota");

    public Timesheet timesheetDtoToTimesheet(TimesheetDto timesheetDto, User user) {
        try {
            Timesheet timesheet = new Timesheet();
            Project project = new Project();
            project.setId(timesheetDto.getProject().getId());
            timesheet.setProject(project);
            timesheet.setUser(user);
            timesheet.setWorkDate(new SimpleDateFormat("yyyy-MM-dd").parse(timesheetDto.getWorkDate()));

            // todo: asi by bolo fajn vyriesit v Dto cez DateTimeFormatter no pattern nefungoval
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            timesheet.setWorkDateStart(LocalTime.parse(timesheetDto.getStartTime(), formatter));
            timesheet.setWorkDateEnd(LocalTime.parse(timesheetDto.getEndTime(), formatter));
            Duration duration = Duration.between(
                    LocalTime.parse(timesheetDto.getStartTime(), formatter),
                    LocalTime.parse(timesheetDto.getEndTime(), formatter));
            timesheet.setTimeWorked(LocalTime.of((int) duration.toMinutes()/60, (int) duration.toMinutes()%60));
            timesheet.setNote(timesheetDto.getNote());
            return timesheet;
        } catch (ParseException e) {
            logger.error(e.getMessage());
        } catch (DateTimeParseException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public TimesheetDto timesheetToTimesheetDto(Timesheet timesheet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
        return TimesheetDto.builder()
                .id(timesheet.getId())
                .user(mapper.userToUserDefaultDto(timesheet.getUser()))
                .project(projectMapper.projectToCodeListDto(timesheet.getProject()))

                .workDate(timesheet.getWorkDate().toString())
                .startTime(LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd").format(timesheet.getWorkDate()) + timesheet.getWorkDateStart().toString(), formatter).toString())
                .endTime(LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd").format(timesheet.getWorkDate()) + timesheet.getWorkDateEnd().toString(), formatter).toString())
                .note(timesheet.getNote())
                .build();
    }

    public ProjectTimesheetDto projectTimesheetToProjectTimesheetDto(ProjectTimesheet projectTimesheet) {
        return ProjectTimesheetDto.builder()
                .id(projectTimesheet.getId())
                .name(projectTimesheet.getProjectName())
                .time(projectTimesheet.getProjectTime()/60)
                .build();
    }

    public TimesheetGetDto timesheetToTimesheetGetDto(Timesheet timesheet) {
        return TimesheetGetDto.builder()
                .id(timesheet.getId())
                .userId(timesheet.getUser().getId())
                .day(getDayName(timesheet.getWorkDate()))
                .date(timesheet.getWorkDate())
                .start(timesheet.getWorkDateStart())
                .end(timesheet.getWorkDateEnd())
                .time(((double) timesheet.getTimeWorked().getHour())+(double) timesheet.getTimeWorked().getMinute()/60.0)
                .note(timesheet.getNote())
                .build();
    }

    public TimesheetDaysStatistics dayStatisticsTimesheetToTimesheetDaysStatistics(DaysStatisticsTimesheet daysStatisticsTimesheet) {
        return TimesheetDaysStatistics.builder()
                .workedDays(daysStatisticsTimesheet.getWorkedDays() != null ? daysStatisticsTimesheet.getWorkedDays() : 0)
                .workDays(daysStatisticsTimesheet.getWorkDays())
                .totalDays(daysStatisticsTimesheet.getTotalDays())
                .timeTotal(daysStatisticsTimesheet.getTimeTotal() != null ? daysStatisticsTimesheet.getTimeTotal() : 0)
                .build();
    }

    private Integer getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    private String getDayName(Date date) {
        return days.get(getDay(date)-1);
    }
}
