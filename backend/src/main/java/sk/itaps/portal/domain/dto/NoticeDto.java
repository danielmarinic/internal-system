package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NoticeDto {
//    private int id;
    private String title;
    private String message;
    private String startDate;
    private String endDate;
    private String level;
}
