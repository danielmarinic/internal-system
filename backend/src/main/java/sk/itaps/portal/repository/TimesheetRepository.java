package sk.itaps.portal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.Timesheet;
import sk.itaps.portal.domain.jpa.User;
import sk.itaps.portal.domain.jpa.custom.DaysStatisticsTimesheet;
import sk.itaps.portal.domain.jpa.custom.ProjectTimesheet;

import java.util.Date;
import java.util.List;

public interface TimesheetRepository extends CrudRepository<Timesheet, Integer> {
        public List<Timesheet> findByUserAndWorkDateBetween(User user, Date start, Date end);
        public List<Timesheet> findByUserAndProjectAndWorkDateBetweenOrderByWorkDateAscWorkDateStartAsc(User user, Project project, Date start, Date end);

        @Query(value = "select b.id, b.name as projectName, sum(datediff(minute, '0:00:00', a.time_worked)) as projectTime " +
                "from Timesheet a " +
                "join Project b On a.project_id = b.id " +
                "group by b.id, b.name", nativeQuery = true)
        public List<ProjectTimesheet> getProjectTime();

        @Query(value = "select b.id, b.name as projectName, sum(datediff(minute, '0:00:00', a.time_worked)) as projectTime " +
                "from Timesheet a " +
                "join Project b On a.project_id = b.id " +
                "where a.user_id = ?1 and a.work_date between ?2 and ?3 " +
                "group by b.id, b.name, a.user_id", nativeQuery = true)
        public List<ProjectTimesheet> getUserProjectTimeByDate(int userId, Date start, Date end);

        @Query(value = "Select " +
                "(Select Count(Distinct work_date) From timesheet Where user_id = ?1 And work_date between ?2 And ?3 Group by user_id) workedDays " +
                ",(DATEDIFF(dd, ?2, ?3) + 1) " +
                "-(DATEDIFF(wk, ?2, ?3) * 2) " +
                "-(CASE WHEN DATENAME(dw, ?2) = 'Sunday' THEN 1 ELSE 0 END) " +
                "-(CASE WHEN DATENAME(dw, ?3) = 'Saturday' THEN 1 ELSE 0 END) workDays" +
                ",datediff(day, ?2, ?3) totalDays" +
                ",(Select SUM(DATEDIFF(MINUTE, '0:00:00', time_worked))/60.0 From timesheet Where user_id = ?1 And work_date between ?2 And ?3 Group by user_id) timeTotal", nativeQuery = true)
        public DaysStatisticsTimesheet getDaysStatisticsByUserAndDateBeetween(int userId, Date from, Date to);

}
