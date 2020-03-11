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
    private Integer salePrice;
    private boolean isSale;
    private List<Product> productList;

    public CategoryDto( Integer salePrice, boolean isSale, List<Product> productList) {
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.productList = productList;
    }


}
