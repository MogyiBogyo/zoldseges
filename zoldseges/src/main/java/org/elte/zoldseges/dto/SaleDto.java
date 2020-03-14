package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.Product;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    private Integer id;
    private Integer quantity;
    private Date date;
    private String buyer;
    private Integer price;
    private Product product;

    public SaleDto(Integer quantity, Date date, String buyer, Integer price, Product product) {
        this.quantity = quantity;
        this.date = date;
        this.buyer = buyer;
        this.price = price;
        this.product = product;
    }
}
