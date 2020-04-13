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

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


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
     * @return all product
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    /**
     * @param id
     * @return product with this id, if it exists
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

    @GetMapping("/{id}/category")
    public ResponseEntity<Category> getProductCategory(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get().getCategory());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param productDto
     * @return adds a new product
     */
    @PostMapping("")
    public ResponseEntity<Product> post(@RequestBody ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findByName(productDto.getName());
        if(optionalProduct.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            Product savedProduct = productRepository.save(mapFromDtoToEntity(productDto));
            return ResponseEntity.ok(savedProduct);
        }
    }

    /**
     * @param id
     * @return modify a product if id exists
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

    /*TODO: Get sales...*/


}
