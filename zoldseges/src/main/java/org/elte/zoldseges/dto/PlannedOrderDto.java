package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlannedOrderDto {
    private Integer id;
    private Integer quantity;
    private Product product;

    public PlannedOrderDto(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

}
