package sk.itaps.portal.service;

import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.Timesheet;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.jpa.custom.DaysStatisticsTimesheet;
import sk.itaps.portal.domain.jpa.custom.ProjectTimesheet;

import java.util.Date;
import java.util.List;

public interface TimesheetService {
    public Timesheet getTimesheet(int id);
    public Timesheet postTimesheet(Timesheet timesheet);
    public Timesheet update(int id, Timesheet timesheet);
    public void delete(int id);
    public List<ProjectTimesheet> getUserProjectTimesheetByDate(int userId, Date start, Date end);
    public List<ProjectTimesheet> getUserProjectTimesheetByMonth(int userId, Date month);
    public List<Timesheet> findUserTimesheetOnProject(User user, Project project, Date start, Date end);
    public List<Timesheet> findUserTimesheetOnProject(User user, Project project, Date month);
    public DaysStatisticsTimesheet getUserStatisticsByDateBetween(int userId, Date from, Date to);
    public DaysStatisticsTimesheet getUserStatisticsByDateMonth(int userId, Date month);
}
