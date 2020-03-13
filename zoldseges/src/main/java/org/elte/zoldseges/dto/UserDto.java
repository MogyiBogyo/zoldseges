package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.User;
import org.elte.zoldseges.entities.WorkTime;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;

    private String familyname;

    private String givenname;

    private String username;

    private String email;

    private String password;

    private User.Role role;

    private boolean enable;

    private List<WorkTime> workTimeList;

    public UserDto(String familyname, String givenname, String username, String email, String password, User.Role role, List<WorkTime> workTimeList) {
        this.familyname = familyname;
        this.givenname = givenname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.workTimeList = workTimeList;
    }


}
