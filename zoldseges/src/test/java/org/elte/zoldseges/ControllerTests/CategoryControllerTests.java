package org.elte.zoldseges.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private String jsonToString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/9999"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetProductsOfCategoryByCategoryId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/productlist/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetProductsOfCategoryByCategoryId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/productlist/99999"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/categories/4"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailDeleteCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/categories/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/categories")
                .content(jsonToString(
                        CategoryDto.builder()
                                .name("testCategory")
                                .isSale(false)
                                .salePrice(125)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailCreateNewCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/categories")
                .content(jsonToString(
                        CategoryDto.builder()
                                .name("gyümölcs")
                                .isSale(false)
                                .salePrice(125)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldUpdateCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/categories/1")
                .content(jsonToString(
                        CategoryDto.builder()
                                .name("gyümiUpdated")
                                .salePrice(123)
                                .isSale(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("gyümiUpdated"));
    }


    @Test
    @WithMockUser(roles = "USER")
    public void shouldFailToUpdateCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/categories/9999").content(jsonToString(
                        CategoryDto.builder()
                                .name("gyümiUpdated")
                                .salePrice(123)
                                .isSale(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
