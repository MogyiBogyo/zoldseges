package org.elte.zoldseges.dto;

import org.elte.zoldseges.entities.User;
import org.elte.zoldseges.entities.WorkTime;

import java.util.List;

public class UserDto {
    private Integer id;

    private String familyname;

    private String givenname;

    private String username;

    private String email;

    private String password;

    private User.Role role;

    private List<WorkTime> workTimeList;

    public Integer getId() {
        return id;
    }

    public String getFamilyname() {
        return familyname;
    }

    public String getGivenname() {
        return givenname;
    }

    public String getUsername() {
        return username;
    }

    /*public String getPassword(){
        return password;
    }*/

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public void setWorkTimeList(List<WorkTime> workTimeList) {
        this.workTimeList = workTimeList;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public User.Role getRole() {
        return role;
    }

    public List<WorkTime> getWorkTimeList() {
        return workTimeList;
    }

    public User makeUserEntity(){
        return new User(this.familyname, this.givenname, this.username, this.email, this.password, this.role, this.workTimeList );
    }
}
