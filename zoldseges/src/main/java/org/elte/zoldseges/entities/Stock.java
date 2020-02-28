package org.elte.zoldseges.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    @OneToMany(mappedBy = "stock")
    private List<Product> productList;
    */
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}
