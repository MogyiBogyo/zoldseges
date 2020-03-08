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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkTime> workTimeList;

    public enum Role {
        ROLE_WORKER, ROLE_ADMIN;
    }

    public User(String familyname, String givenname, String username, String email, String password, Role role, List<WorkTime> workTimeList) {
        this.familyname = familyname;
        this.givenname = givenname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
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
                ", workTimeList=" + workTimeList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getFamilyname(), user.getFamilyname()) &&
                Objects.equals(getGivenname(), user.getGivenname()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                getRole() == user.getRole() &&
                Objects.equals(getWorkTimeList(), user.getWorkTimeList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFamilyname(), getGivenname(), getUsername(), getEmail(), getPassword(), getRole(), getWorkTimeList());
    }
}
