package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    private Integer id;
    private Product product;
    private Integer quantity;

    public StockDto(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
