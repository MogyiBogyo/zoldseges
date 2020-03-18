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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return isSale() == category.isSale() &&
                Objects.equals(getId(), category.getId()) &&
                Objects.equals(getCategoryName(), category.getCategoryName()) &&
                Objects.equals(getSalePrice(), category.getSalePrice()) &&
                Objects.equals(getProductList(), category.getProductList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategoryName(), getSalePrice(), isSale(), getProductList());
    }
}
