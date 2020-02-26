package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String familyName;

    @Column(nullable = false)
    private String givenName;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    /*@Column(nullable = false)
    private boolean isAdmin;*/

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkTime> workTimeList;

    /*public Role getRole() {
        return role;
    }*/

    public enum Role {
        ROLE_WORKER, ROLE_ADMIN;
    }
}
