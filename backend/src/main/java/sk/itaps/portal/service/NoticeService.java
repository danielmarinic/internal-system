package sk.itaps.portal.service;

import sk.itaps.portal.domain.jpa.Notice;

import java.util.List;

public interface NoticeService {
    public List<Notice> getAllNotices();
    public Notice findById(int id);
    public Notice saveOrUpdate(Notice notice);
    public void delete(int id);
    public void update(Notice notice, int id);
    public List<Notice> getActualNotice();
}
