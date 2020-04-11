package org.elte.zoldseges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elte.zoldseges.entities.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;
    private Integer salePrice;
    private boolean isSale;
    private List<Product> productList;

    public CategoryDto(String name, Integer salePrice, boolean isSale, List<Product> productList) {
        this.name = name;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.productList = productList;
    }

    public CategoryDto(String name, Integer salePrice, boolean isSale) {
        this.name = name;
        if(salePrice != null ){
            this.salePrice = salePrice;
        }else {
            this.salePrice = 0;
        }

        this.isSale = isSale;
        this.productList = null;
    }

    public CategoryDto(String name, boolean isSale) {
        this.name = name;
        this.salePrice = 0;
        this.isSale = isSale;
        this.productList = null;
    }
}
