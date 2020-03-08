package org.elte.zoldseges.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Column(nullable = false)
    private String buyer;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale)) return false;
        Sale sale = (Sale) o;
        return Objects.equals(getId(), sale.getId()) &&
                Objects.equals(getQuantity(), sale.getQuantity()) &&
                Objects.equals(getDate(), sale.getDate()) &&
                Objects.equals(getBuyer(), sale.getBuyer()) &&
                Objects.equals(getPrice(), sale.getPrice()) &&
                Objects.equals(getProduct(), sale.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getDate(), getBuyer(), getPrice(), getProduct());
    }
}
