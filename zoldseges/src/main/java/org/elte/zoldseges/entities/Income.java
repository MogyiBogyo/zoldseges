package org.elte.zoldseges.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @Column
    private String seller;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JsonIgnoreProperties({"salePrice", "isSale", })
    private Product product;

    public Income(Integer quantity, String seller, Date date, Integer price, Product product) {
        this.quantity = quantity;
        this.seller = seller;
        this.date = date;
        this.price = price;
        this.product = product;
    }



}
