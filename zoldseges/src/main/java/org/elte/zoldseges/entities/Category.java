package org.elte.zoldseges.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer salePrice;

    @Column(nullable = false)
    private boolean isSale;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;


    public Category(String name, Integer salePrice, boolean isSale, List<Product> productList) {
        this.name = name;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.productList = productList;
    }

    public Category(String name, Integer salePrice, boolean isSale) {
        this.name = name;
        this.salePrice = salePrice;
        this.isSale = isSale;
    }
}
