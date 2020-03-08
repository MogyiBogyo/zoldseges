package org.elte.zoldseges.dto;

import org.elte.zoldseges.entities.Product;

import java.util.List;

public class CategoryDto {
    private Integer id;
    private Integer salePrice;
    private boolean isSale;
    private List<Product> productList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public CategoryDto(Integer id, Integer salePrice, boolean isSale, List<Product> productList) {
        this.id = id;
        this.salePrice = salePrice;
        this.isSale = isSale;
        this.productList = productList;
    }


}
