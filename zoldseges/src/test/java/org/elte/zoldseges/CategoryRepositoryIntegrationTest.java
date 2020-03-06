package org.elte.zoldseges;

import org.elte.zoldseges.entities.Category;
import org.elte.zoldseges.entities.Product;
import org.elte.zoldseges.repositories.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;


//@RunWith(SpringRunner.class)
//@DataJpaTest
public class CategoryRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    /*@Test
    public void whenFindByname_thenReturnCategory(){
        List<Product> emptylist = new ArrayList<>();
        Category testCat = new Category(6, "testCategoryName", 2020, false,emptylist);
        categoryRepository.save(testCat);
        //testEntityManager.flush();


        Optional<Category> foundedCategory = categoryRepository.findById(testCat.getId());

        // then
        assertEquals("",foundedCategory.get().getId(),testCat.getId());


    }*/
}
