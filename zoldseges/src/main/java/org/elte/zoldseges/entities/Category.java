package org.elte.zoldseges.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Column(nullable = false)
    private Integer salePrice;

    @Column(nullable = false)
    private boolean isSale;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;

    public Category(String categoryName, Integer salePrice, boolean isSale, List<Product> productList) {
        this.categoryName = categoryName;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.productList = productList;
    }
}
