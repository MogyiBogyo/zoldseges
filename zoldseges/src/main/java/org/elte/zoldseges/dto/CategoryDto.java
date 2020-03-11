package org.elte.zoldseges.dto;

import lombok.Data;
import org.elte.zoldseges.entities.Product;
import java.util.List;

@Data
public class CategoryDto {
    private Integer id;
    private Integer salePrice;
    private String name;
    private boolean isSale;
    private List<Product> productList;


    public CategoryDto(Integer id, Integer salePrice, boolean isSale, List<Product> productList) {
        this.id = id;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.productList = productList;
    }

}
