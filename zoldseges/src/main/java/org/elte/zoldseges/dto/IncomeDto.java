package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDto {
    private Integer id;

    private Integer quantity;

    private String seller;

    private Date date;

    private Integer price;

    private Integer productId;

    public IncomeDto(Integer quantity, String seller, Date date, Integer price, Integer productId) {
        this.quantity = quantity;
        this.seller = seller;
        this.date = date;
        this.price = price;
        this.productId = productId;
    }
}
