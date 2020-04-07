package org.elte.zoldseges.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"salePrice", "isSale", "category" })
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public Stock(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return Objects.equals(getId(), stock.getId()) &&
                Objects.equals(getProduct(), stock.getProduct().getId()) &&
                Objects.equals(getQuantity(), stock.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getQuantity());
    }
}
