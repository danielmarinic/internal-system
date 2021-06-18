package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PositionDto {
    private Integer id;
    private String description;
}
