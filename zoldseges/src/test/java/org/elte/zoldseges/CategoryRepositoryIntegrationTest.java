package org.elte.zoldseges;

import org.elte.zoldseges.controllers.CategoryController;
import org.elte.zoldseges.entities.Category;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;


import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    Category testCat = new Category( "testCategoryName", 2020, false, null);

    @Test
    public void categoryRepositoryTest(){

        categoryRepository.save(testCat);
        //System.out.println(categoryRepository.findById(4).get().getCategoryName());
        Optional<Category> foundedCategory = categoryRepository.findById(testCat.getId());
        assertTrue(foundedCategory.getClass().equals(categoryRepository.findById(1).getClass()));
        assertTrue(foundedCategory.isPresent());
        //assertEquals("new category id is 4?", 4, categoryRepository.findBycategoryName("testCategoryName").getId() );

        //assertEquals("new category name is testCategoryName", categoryRepository.findById(4).get(), categoryRepository.findBycategoryName("testCategoryName"));

        //assertEquals("new category salePrice is 2020?", categoryRepository.findBySalePrice(2020), testCat);

    }
}
