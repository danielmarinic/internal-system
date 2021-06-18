package sk.itaps.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.itaps.portal.domain.jpa.Project;
import sk.itaps.portal.domain.jpa.User;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Override
    public List<Project> findAll();
    public List<Project> findProjectsByActive(int id);
    public List<Project> findProjectsByUsers(User user);
//    @Query("Select " +
//            "a.id,a.name as projectName" +
////            ",Convert(Numeric(20, 2), SUM(DATEDIFF(MINUTE, '0:00:00', c.timeWorked))/60.0) as timeWorked " +
//            "From Project a " +
//            "Join a.users b " +
////            "On a.id = b.project_id " +
//            "Join a.timesheets c ")// +
////            "On a.id = c.project_id " +
////            "And b.user_id = c.user_id " +
////            "Where b.userId = ?1 " +
////            "and c.work_date between ?2 And EOMONTH(?2) " +
////            "Group by a.id, a.name, c.userId")
//    public List<ProjectTimesheet> findProjectTimesheetByUserAndMonth(int user_id);
}
