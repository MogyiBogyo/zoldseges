package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Date date;

    @Column
    private String buyer;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"salePrice", "isSale", "category"})
    private Product product;

    public Sale(Integer quantity, Date date, String buyer, Integer price, Product product) {
        this.quantity = quantity;
        this.date = date;
        this.buyer = buyer;
        this.price = price;
        this.product = product;
    }

}
