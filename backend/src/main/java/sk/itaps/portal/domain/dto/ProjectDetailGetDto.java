package sk.itaps.portal.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDetailGetDto extends ProjectDetailPostDto {
    private Boolean active;
    private UserDefaultDto createdBy;
    private LocalDate createdAt;
    private UserDefaultDto updatedBy;
    private LocalDateTime updatedAt;
}
