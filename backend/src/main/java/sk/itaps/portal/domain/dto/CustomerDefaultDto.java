package sk.itaps.portal.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
public class CustomerDefaultDto {
    @NotNull
    @Positive
    private Integer id;
    @NotNull
    @Max(10)
    private String nameShort;
    @NotNull
    private String name;
}
