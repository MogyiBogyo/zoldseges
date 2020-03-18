package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @JsonProperty(value = "name")
    private String name;

    @Column(nullable = false)
    @JsonProperty(value = "price")
    private Integer price;

    @Column(nullable = false)
    @JsonProperty(value = "salePrice")
    private Integer salePrice;

    @Column(nullable = false)
    @JsonProperty(value = "isSale")
    private boolean isSale;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Stock> stockList;


    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Income> incomeList;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Sale> saleList;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<PlannedOrder> plannedOrderList;

    public Product(String name, Integer price, Integer salePrice, boolean isSale, Category category, List<Stock> stockList, List<Income> incomeList, List<Sale> saleList, List<PlannedOrder> plannedOrderList) {
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.category = category;
        this.stockList = stockList;
        this.incomeList = incomeList;
        this.saleList = saleList;
        this.plannedOrderList = plannedOrderList;
    }

    public Product(String name, Integer price, Integer salePrice, boolean isSale, Category category) {
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return isSale() == product.isSale() &&
                Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(getSalePrice(), product.getSalePrice()) &&
                Objects.equals(getCategory(), product.getCategory()) &&
                Objects.equals(getStockList(), product.getStockList()) &&
                Objects.equals(getIncomeList(), product.getIncomeList()) &&
                Objects.equals(getSaleList(), product.getSaleList()) &&
                Objects.equals(getPlannedOrderList(), product.getPlannedOrderList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getSalePrice(), isSale(), getCategory(), getStockList(), getIncomeList(), getSaleList(), getPlannedOrderList());
    }
}
