package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.User;
import org.elte.zoldseges.repositories.UserRepository;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorktimeDto {
    public Integer id;
    private Date date;
    private String startHour;
    private String endHour;
    private User user;
    UserRepository userRepository;

    public WorktimeDto(Integer id, Date date, String startHour, String endHour, Integer userId) {
        this.id = id;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.user = userRepository.findById(userId).get();
    }
}
