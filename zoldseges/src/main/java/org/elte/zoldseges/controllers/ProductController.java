package org.elte.zoldseges.controllers;

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

    /**
     * @param product
     * @return adds a new product
     */
    @PostMapping("")
    public ResponseEntity<Product> post(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    /**
     * @param product
     * @param id
     * @return modify a product if id exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> put(@RequestBody Product product, @PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            //product.setId(id);
            return ResponseEntity.ok(productRepository.save(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param id
     * @return delete a product if it exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param id
     * @return return category of the product
     */
    @GetMapping("/{id}/category")
    public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get().getCategory());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
