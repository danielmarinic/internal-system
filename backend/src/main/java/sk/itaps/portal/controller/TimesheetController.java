package sk.itaps.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import sk.itaps.portal.domain.dto.ProjectTimesheetDto;
import sk.itaps.portal.domain.dto.TimesheetDaysStatistics;
import sk.itaps.portal.domain.dto.TimesheetDto;
import sk.itaps.portal.domain.dto.TimesheetGetDto;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.mapper.TimesheetMapper;
import sk.itaps.portal.service.TimesheetService;
import sk.itaps.portal.service.UserService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {
    @Autowired
    TimesheetMapper timesheetMapper;

    @Autowired
    TimesheetService timesheetService;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public TimesheetDto getTimesheetById(@PathVariable int id) {
        return timesheetMapper.timesheetToTimesheetDto(timesheetService.getTimesheet(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTimesheet(@Valid @RequestBody TimesheetDto timesheetDto, @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.getCurrentUser(principal);
        timesheetService.postTimesheet(timesheetMapper.timesheetDtoToTimesheet(timesheetDto, user));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public TimesheetDto editTimesheet(@PathVariable int id, @RequestBody TimesheetDto timesheetDto, @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.getCurrentUser(principal);
        return timesheetMapper.timesheetToTimesheetDto(timesheetService.update(id, timesheetMapper.timesheetDtoToTimesheet(timesheetDto, user)));
    }

    @DeleteMapping("/{id}")
    public void deleteTimesheet(@PathVariable int id) {
        timesheetService.delete(id);
    }

    @GetMapping("/me/month")
    @ResponseBody
    public List<ProjectTimesheetDto> getUserProjectTimesheetByMonth(
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM-dd") Date month,
            @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.getCurrentUser(principal);
            return timesheetService.getUserProjectTimesheetByMonth(user.getId(), month)
                    .stream()
                    .map(timesheetMapper::projectTimesheetToProjectTimesheetDto)
                    .collect(Collectors.toList());

    }

    @GetMapping("/me/date")
    @ResponseBody
    public List<ProjectTimesheetDto> getUserTimesheetByDate(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.getCurrentUser(principal);
        return timesheetService.getUserProjectTimesheetByDate(user.getId(), from, to)
                .stream()
                .map(timesheetMapper::projectTimesheetToProjectTimesheetDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/me/project/{id}")
    @ResponseBody
    public List<TimesheetGetDto> getUserProjectByDate(
            @PathVariable int id,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.getCurrentUser(principal);
        Project project = new Project();
        project.setId(id);
        return timesheetService.findUserTimesheetOnProject(user, project, from, to)
                .stream()
                .map(timesheetMapper::timesheetToTimesheetGetDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/me/statistic")
    @ResponseBody
    public TimesheetDaysStatistics getDateDaysStatistics(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.getCurrentUser(principal);
        return timesheetMapper.dayStatisticsTimesheetToTimesheetDaysStatistics(timesheetService.getUserStatisticsByDateBetween(user.getId(), from, to));
    }
}
