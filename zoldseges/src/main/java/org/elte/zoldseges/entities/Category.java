package org.elte.zoldseges.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column()
    private Integer salePrice;

    @Column(nullable = false)
    private boolean isSale;

    @JsonProperty(value= "productList")
    @JsonIgnoreProperties(value= "{category}")
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

    public Category(String name, boolean isSale) {
        this.name = name;
        this.isSale = isSale;
        this.salePrice = 0;
    }
}
