package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.Product;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDto {
    private Integer id;

    private Integer quantity;

    private String seller;

    private Date date;

    private Integer price;

    private Product product;

    public IncomeDto(Integer quantity, String seller, Date date, Integer price, Product product) {
        this.quantity = quantity;
        this.seller = seller;
        this.date = date;
        this.price = price;
        this.product = product;
    }
}
