package sk.itaps.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.Timesheet;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.jpa.custom.DaysStatisticsTimesheet;
import sk.itaps.portal.domain.jpa.custom.ProjectTimesheet;
import sk.itaps.portal.repository.TimesheetRepository;
import sk.itaps.portal.service.TimesheetService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TimesheetServiceImpl implements TimesheetService {
    @Autowired
    TimesheetRepository timesheetRepository;

    public Timesheet getTimesheet(int id) {
        return timesheetRepository.findById(id).get();
    }

    public Timesheet postTimesheet(Timesheet timesheet) {
        return timesheetRepository.save(timesheet);
    }

    public Timesheet update(int id, Timesheet timesheet) {
        timesheet.setId(id);
        return timesheetRepository.save(timesheet);
    }

    public void delete(int id) {
        timesheetRepository.deleteById(id);
    }

    public List<ProjectTimesheet> getUserProjectTimesheetByDate(int userId, Date start, Date end) {
        return timesheetRepository.getUserProjectTimeByDate(userId, start, end);
    }

    public List<ProjectTimesheet> getUserProjectTimesheetByMonth(int userId, Date month) {
        Date start = getFirstDateOfMonth(month);
        Date end = getLastDateOfMonth(month);
        return timesheetRepository.getUserProjectTimeByDate(userId, start, end);
    }

    public List<Timesheet> findUserTimesheetOnProject(User user, Project project, Date start, Date end) {
        return timesheetRepository.findByUserAndProjectAndWorkDateBetweenOrderByWorkDateAscWorkDateStartAsc(user, project, start, end);
    }

    public List<Timesheet> findUserTimesheetOnProject(User user, Project project, Date month) {
        return timesheetRepository.findByUserAndProjectAndWorkDateBetweenOrderByWorkDateAscWorkDateStartAsc(user, project, getFirstDateOfMonth(month), getLastDateOfMonth(month));
    }

    public DaysStatisticsTimesheet getUserStatisticsByDateBetween(int userId, Date from, Date to) {
        return timesheetRepository.getDaysStatisticsByUserAndDateBeetween(userId, from, to);
    }

    public DaysStatisticsTimesheet getUserStatisticsByDateMonth(int userId, Date month) {
        Date start = getFirstDateOfMonth(month);
        Date end = getLastDateOfMonth(month);
        return getUserStatisticsByDateBetween(userId, start, end);
    }

    private Date getFirstDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    private Date getLastDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}
