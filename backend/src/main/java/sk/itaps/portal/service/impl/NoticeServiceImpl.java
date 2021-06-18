package sk.itaps.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.itaps.portal.domain.jpa.Notice;
import sk.itaps.portal.repository.NoticeRepository;
import sk.itaps.portal.service.NoticeService;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Notice findById(int id) {
        return noticeRepository.findById(id).get();
    }

    public Notice saveOrUpdate(Notice notice) {
        return noticeRepository.save(notice);
    }

    public void delete(int id) {
        noticeRepository.deleteById(id);
    }

    public void update(Notice notice, int id) {
        noticeRepository.save(notice);
    }

    public List<Notice> getActualNotice() {
        return noticeRepository.findNoticesByDateBetweenStartDateAndEndDate(new Date());
    }
}
