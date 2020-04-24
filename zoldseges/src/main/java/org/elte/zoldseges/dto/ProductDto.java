package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.Income;
import org.elte.zoldseges.entities.PlannedOrder;
import org.elte.zoldseges.entities.Sale;
import org.elte.zoldseges.entities.Stock;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    private String name;
    private Integer price;
    private Integer salePrice;
    private boolean isSale;
    private Integer categoryId;
    private List<Stock> stockList;
    private List<Income> incomeList;
    private List<Sale> saleList;
    private List<PlannedOrder> plannedOrderList;

    public ProductDto(String name, Integer price, Integer salePrice, boolean isSale, Integer categoryID, List<Stock> stockList, List<Income> incomeList, List<Sale> saleList, List<PlannedOrder> plannedOrderList) {
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.categoryId = categoryID;
        this.stockList = stockList;
        this.incomeList = incomeList;
        this.saleList = saleList;
        this.plannedOrderList = plannedOrderList;
    }

    public ProductDto(String name, Integer price, Integer salePrice, boolean isSale, Integer categoryId) {
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.categoryId = categoryId;
    }
}
