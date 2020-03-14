package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String familyname;

    @Column(nullable = false)
    private String givenname;

    @Column(unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private boolean enable;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkTime> workTimeList;

    public enum Role {
        ROLE_WORKER, ROLE_ADMIN;
    }

    public User(String familyname, String givenname, String username, String email, String password, Role role, boolean enable, List<WorkTime> workTimeList) {
        this.familyname = familyname;
        this.givenname = givenname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enable = enable;
        this.workTimeList = workTimeList;
    }

    public User(String familyname, String givenname, String username, String email, String password, boolean enable, List<WorkTime> workTimeList) {
        this.familyname = familyname;
        this.givenname = givenname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.workTimeList = workTimeList;
        this.role = Role.ROLE_WORKER;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", familyname='" + familyname + '\'' +
                ", givenname='" + givenname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", enabled=" + enable +
                ", workTimeList=" + workTimeList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enable == user.enable &&
                id.equals(user.id) &&
                familyname.equals(user.familyname) &&
                givenname.equals(user.givenname) &&
                username.equals(user.username) &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                role == user.role &&
                Objects.equals(workTimeList, user.workTimeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, familyname, givenname, username, email, password, role, enable, workTimeList);
    }
}
