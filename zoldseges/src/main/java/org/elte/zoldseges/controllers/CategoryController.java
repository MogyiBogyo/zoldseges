package org.elte.zoldseges.controllers;

import org.elte.zoldseges.dto.CategoryDto;
import org.elte.zoldseges.entities.Category;
import org.elte.zoldseges.entities.Income;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.CategoryRepository;
import org.elte.zoldseges.repositories.ProductRepository;
import org.elte.zoldseges.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin
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
     * @return return all category
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Category>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    /**
     * @param id
     * @return return a category with this id, if it exists
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
     * @param category
     * @return add a new category
     */
    @PostMapping("")
    public ResponseEntity<Category> post(@RequestBody CategoryDto categoryDto) {

        Category savedCategory = categoryRepository.save(mapFromCategoryDtoToCategoryEntity(categoryDto));
        return ResponseEntity.ok(savedCategory);
    }

    private Category mapFromCategoryDtoToCategoryEntity(CategoryDto category){
        return  new Category(
                category.getName(),
                category.getSalePrice(),
                category.isSale(),
                category.getProductList()
        );
    }

    /**
     * @param id
     * @return modify a category if id exists
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

    private Category modifyEntityWithDto(CategoryDto categoryDto, Category findedCategory){
          findedCategory.setCategoryName(categoryDto.getName());
          findedCategory.setSalePrice(categoryDto.getSalePrice());
          findedCategory.setSale(categoryDto.isSale());
          findedCategory.setProductList(categoryDto.getProductList());
        return findedCategory;
    }

    /**
     * @param id
     * @return delete a category whit this id, if it exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
