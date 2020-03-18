package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "familyname")
    private String familyname;

    @Column(nullable = false)
    @JsonProperty(value = "givenname")
    private String givenname;

    @Column(unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @JsonIgnore
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

    public User(String familyname, String givenname, String username, String email, String password,  List<WorkTime> workTimeList) {
        this.familyname = familyname;
        this.givenname = givenname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enable = true;
        this.workTimeList = workTimeList;
        this.role = Role.ROLE_WORKER;
    }


    public User(String familyname, String givenname, String username, String email, String password, Role role,  List<WorkTime> workTimeList) {
        this.familyname = familyname;
        this.givenname = givenname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enable = true;
        this.workTimeList = workTimeList;
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

}
