package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer salePrice;

    @Column(nullable = false)
    private boolean isSale;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Stock stock;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Income income;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Sale sale;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private PlannedOrder plannedOrder;

}
