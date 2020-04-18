package org.elte.zoldseges.repositoryTests;

import org.elte.zoldseges.entities.Category;
import org.elte.zoldseges.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    Category testCat = new Category("testCategoryName", 2020, false);

    @Test
    public void categoryRepositoryTest() {

        categoryRepository.save(testCat);
        Optional<Category> foundedCategory = categoryRepository.findById(testCat.getId());
        assertTrue(foundedCategory.getClass().equals(categoryRepository.findById(1).getClass()));


    }
}
