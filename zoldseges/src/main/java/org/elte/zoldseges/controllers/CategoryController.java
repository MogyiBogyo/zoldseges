package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.CategoryDto;
import org.elte.zoldseges.entities.Category;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.CategoryRepository;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Allocates the "/categories" endpoint to control categories
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/categories")
public class CategoryController {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    /**
     * Returns all the Categories
     * @return ResponseEntity of Categories
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Category>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    /**
     * Returns a Category by ID
     * @param id Id of group
     * @return ResponseEntity of a Category
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return ResponseEntity.ok(optionalCategory.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns Products of a Category by Cateogtry Id
     * @param id Id of Category
     * @return ResponseEntity of Products
     */
    @GetMapping("/productlist/{id}")
    public ResponseEntity<Iterable<Product>> getProducts(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return ResponseEntity.ok(optionalCategory.get().getProductList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new Category
     * @param categoryDto The Category data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of newly created Category
     */
    @PostMapping("")
    public ResponseEntity<Category> post(@RequestBody CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryDto.getName());
        if(optionalCategory.isPresent()){
            return ResponseEntity.badRequest().build();
        }else{
            Category savedCategory = categoryRepository.save(mapFromCategoryDtoToCategoryEntity(categoryDto));
            return ResponseEntity.ok(savedCategory);
        }
    }


    /**
     * Creates a new Category Entity from DTO
     * @param categorydto The Category data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return a new Category
     */
    private Category mapFromCategoryDtoToCategoryEntity(CategoryDto categorydto) {
        return new Category(
                categorydto.getName(),
                categorydto.getSalePrice(),
                categorydto.isSale()
        );
    }

    /**
     * Updates a Category by ID
     * @param id Id of Category which to modify
     * @param categoryDto The Category data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @return ResponseEntity of the updated Category
     * Returns Not Found if Category doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> put(@RequestBody CategoryDto categoryDto, @PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {

            return ResponseEntity.ok(categoryRepository.save(modifyEntityWithDto(categoryDto, optionalCategory.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Modify a Category with DTO's data
     * @param categoryDto The Category data transfer Object to make Entity and add to DB (e.g.: JSON)
     * @param foundedCategory  founded Category by name
     * @return an updated Category
     */
    private Category modifyEntityWithDto(CategoryDto categoryDto, Category foundedCategory) {
        foundedCategory.setName(categoryDto.getName());
        if(categoryDto.getSalePrice() == null){
           foundedCategory.setSalePrice(0);
        }else {
            foundedCategory.setSalePrice(categoryDto.getSalePrice());
        }
        foundedCategory.setSale(categoryDto.isSale());
        foundedCategory.setProductList(categoryDto.getProductList());
        return foundedCategory;
    }

    /**
     * Deletes a Category by Category ID
     * @param id ID of Group
     * @return ResponseEntity
     * Returns Bad Request if Group is not deletable or if Category doesn't exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent() && optionalCategory.get().getProductList().isEmpty()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
