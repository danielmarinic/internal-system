package sk.itaps.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sk.itaps.portal.domain.dto.NoticeDto;
import sk.itaps.portal.domain.dto.PositionDto;
import sk.itaps.portal.domain.jpa.Notice;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.service.NoticeService;
import sk.itaps.portal.service.PositionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApplicationController {
    @Autowired
    PositionService positionService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    Mapper mapper;

    @GetMapping("/position")
    public List<PositionDto> getPositions() {
        return positionService.getPositions()
                .stream()
                .map(mapper::positionToPositionDto)
                .collect(Collectors.toList());
    }


    // Notice
    // todo: prerobit all na DTO object
    @GetMapping("/notice")
    @ResponseBody
    public List<Notice> getNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/notice/actual")
    @ResponseBody
    public List<Notice> getActualNotices() {
        return noticeService.getActualNotice();
    }

    @PostMapping("/notice")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Notice postNotice(@RequestBody NoticeDto notice) {
        return noticeService.saveOrUpdate(mapper.noticeDtoToNotice(notice));
    }
}
