package org.elte.zoldseges.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userID;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer startHour;

    @Column
    private Integer endHour;

}
