package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    private Integer id;
    private Integer productId;
    private Integer quantity;

    public StockDto(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
