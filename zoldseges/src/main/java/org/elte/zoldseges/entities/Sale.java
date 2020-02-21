package org.elte.zoldseges.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer productID;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String buyer;

    @Column(nullable = false)
    private Integer price;

}
