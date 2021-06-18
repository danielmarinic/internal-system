package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeListStringDto {
    private String id;
    private String description;
}
