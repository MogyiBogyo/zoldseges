package org.elte.zoldseges.entities;

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

    @Column(nullable = false)
    private String seller;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn
    private Product product;

    public Income(Integer quantity, String seller, Date date, Integer price, Product product) {
        this.quantity = quantity;
        this.seller = seller;
        this.date = date;
        this.price = price;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Income)) return false;
        Income income = (Income) o;
        return Objects.equals(getId(), income.getId()) &&
                Objects.equals(getQuantity(), income.getQuantity()) &&
                Objects.equals(getSeller(), income.getSeller()) &&
                Objects.equals(getDate(), income.getDate()) &&
                Objects.equals(getPrice(), income.getPrice()) &&
                Objects.equals(getProduct(), income.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getSeller(), getDate(), getPrice(), getProduct());
    }
}
