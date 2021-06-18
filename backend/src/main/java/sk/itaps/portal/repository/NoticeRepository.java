package sk.itaps.portal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sk.itaps.portal.domain.jpa.Notice;

import java.util.Date;
import java.util.List;

public interface NoticeRepository extends CrudRepository<Notice, Integer> {
    @Override
    public List<Notice> findAll();
    @Query("SELECT a FROM Notice a Where :current_date between start_date And end_date")
    public List<Notice> findNoticesByDateBetweenStartDateAndEndDate(@Param("current_date") Date date);
}
