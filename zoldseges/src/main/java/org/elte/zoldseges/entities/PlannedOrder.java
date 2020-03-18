package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PlannedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"salePrice", "isSale", })
    private Product product;

    public PlannedOrder(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlannedOrder)) return false;
        PlannedOrder that = (PlannedOrder) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getProduct());
    }
}
