package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String startHour;

    @Column
    private String endHour;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    @JsonProperty(value = "user_password")
    private User user;

    public WorkTime(Date date, String startHour, String endHour, User user) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkTime)) return false;
        WorkTime workTime = (WorkTime) o;
        return Objects.equals(getId(), workTime.getId()) &&
                Objects.equals(getDate(), workTime.getDate()) &&
                Objects.equals(getStartHour(), workTime.getStartHour()) &&
                Objects.equals(getEndHour(), workTime.getEndHour()) &&
                Objects.equals(getUser(), workTime.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getStartHour(), getEndHour(), getUser());
    }
}
