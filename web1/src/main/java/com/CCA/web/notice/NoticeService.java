package com.CCA.web.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Notice getNoticeById(Integer id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice.orElse(null);
    }

    public void createNotice(String subject, String content, byte[] fileData, String fileName) {
        Notice notice = new Notice();
        notice.setTitle(subject);
        notice.setContent(content);
        notice.setFileData(fileData);
        notice.setFileName(fileName);
        notice.setCreateDate(LocalDateTime.now());
        // 다른 필드 설정
        noticeRepository.save(notice);
    }

    public void updateNotice(Notice notice, String title, String content) {
        notice.setTitle(title);
        notice.setContent(content);
        notice.setModifyDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    public void deleteNotice(Notice notice) {
        noticeRepository.delete(notice);
    }
}
