package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.ProductDto;
import org.elte.zoldseges.entities.Category;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.CategoryRepository;
import org.elte.zoldseges.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Allocates the "/products" endpoint to control products
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new Product Entity from DTO
     * @param productDto data transfer object
     * @return a new Product
     */
    private Product mapFromDtoToEntity(ProductDto productDto) {
        Optional<Category> productCategory = categoryRepository.findById(productDto.getCategoryId());
        return new Product(
                productDto.getName(),
                productDto.getPrice(),
                productDto.getSalePrice(),
                productDto.isSale(),
                productCategory.get(),
                productDto.getStockList(),
                productDto.getIncomeList(),
                productDto.getSaleList(),
                productDto.getPlannedOrderList()
        );
    }

    /**
     * Modify a Product with DTO's data
     * @param productDto data transfer object
     * @param findedProduct Product for modify
     * @return an updated Product
     */
    private Product modifyEntityWithDto(ProductDto productDto, Product findedProduct) {
        Optional<Category> productCategory = categoryRepository.findById(productDto.getCategoryId());
        findedProduct.setName(productDto.getName());
        findedProduct.setPrice((productDto.getPrice()));
        findedProduct.setSalePrice(productDto.getSalePrice());
        findedProduct.setSale(productDto.isSale());
        findedProduct.setCategory(productCategory.get());
        findedProduct.setStockList(productDto.getStockList());
        findedProduct.setIncomeList(productDto.getIncomeList());
        findedProduct.setSaleList(productDto.getSaleList());
        findedProduct.setPlannedOrderList(productDto.getPlannedOrderList());
        return findedProduct;
    }


    /**
     * Returns all the Products
     * @return ResponseEntity of Products
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }


    /**
     * Returns a Product by ID
     * @param id Id of Product
     * @return ResponseEntity of a Product
     * Returns Not Found if Product doesn't exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Returns a Category by Product ID
     * @param id Id of Product
     * @return ResponseEntity of a Category
     * Returns Not Found if Product doesn't exists
     */
    @GetMapping("/{id}/category")
    public ResponseEntity<Category> getProductCategory(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get().getCategory());
        }

        return ResponseEntity.notFound().build();
    }


    /**
     * Creates a new Product
     * @param productDto The Product data transfer Object to add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created Product
     */
    @PostMapping("")
    public ResponseEntity<Product> post(@RequestBody ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findByName(productDto.getName());
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(optionalProduct.isPresent() ){
            return ResponseEntity.badRequest().build();
        } else if(!optionalCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product savedProduct = productRepository.save(mapFromDtoToEntity(productDto));
        return ResponseEntity.ok(savedProduct);
    }

    /**
     * Updates a Product by ID
     * @param id Id of Group which to modify
     * @param productDto The Product data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Product
     * Returns Not Found if Product doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> put(@RequestBody ProductDto productDto, @PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(productRepository.save(modifyEntityWithDto(productDto, optionalProduct.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a Product by Product ID
     * @param id ID of Product
     * @return ResponseEntity
     * Returns Not Found if Product doesn't exists
     * Returns Bad Request if Product is not deletable
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getIncomeList().isEmpty() && product.getPlannedOrderList().isEmpty()
                    && product.getSaleList().isEmpty() && product.getStockList().isEmpty()) {
                productRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
