package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorktimeDto {
    private Integer id;
    private Date date;
    private String startHour;
    private String endHour;
    private Integer userId;

    public WorktimeDto(Date date, String startHour, String endHour, Integer userId) {
        this.id = id;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.userId = userId;
    }
}
