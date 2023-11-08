package com.CCA.web.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.Valid;

import java.io.IOException;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "notice_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            // 공지사항이 존재하지 않는 경우 처리
            return "redirect:/notice/list";
        }
        model.addAttribute("notice", notice);
        return "notice_detail";
    }

    @GetMapping("/create")
    public String noticeCreateForm(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "notice_form";
    }

    @PostMapping("/create")
    public String noticeCreate(@Valid NoticeForm noticeForm, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 파일을 업로드하고 Notice 엔티티에 저장
                byte[] fileData = file.getBytes();
                String fileName = file.getOriginalFilename();
                noticeForm.setFileData(fileData);
                noticeForm.setFileName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        noticeService.createNotice(noticeForm.getTitle(), noticeForm.getContent(), noticeForm.getFileData(), noticeForm.getFileName());
        return "redirect:/notice/list";
    }

    @GetMapping("/modify/{id}")
    public String noticeModifyForm(Model model, @PathVariable("id") Integer id) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            // 공지사항이 존재하지 않는 경우 처리
            return "redirect:/notice/list";
        }
        model.addAttribute("noticeForm", new NoticeForm());
        model.addAttribute("noticeId", id);
        return "notice_modify_form";
    }

    @PostMapping("/modify/{id}")
    public String noticeModify(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm, @PathVariable("id") Integer id) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            // 공지사항이 존재하지 않는 경우 처리
            return "redirect:/notice/list";
        }
        noticeService.updateNotice(notice, noticeForm.getTitle(), noticeForm.getContent());
        return String.format("redirect:/notice/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String noticeDelete(@PathVariable("id") Integer id) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice != null) {
            noticeService.deleteNotice(notice);
        }
        return "redirect:/notice/list";
    }
}
