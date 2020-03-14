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
    private Integer productId;

    public PlannedOrderDto(Integer quantity, Integer productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

}
