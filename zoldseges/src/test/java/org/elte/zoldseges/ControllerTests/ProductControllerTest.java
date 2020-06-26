package org.elte.zoldseges.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.ProductDto;
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
public class ProductControllerTest {

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
    public void shouldGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetCategoryOfProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/1/category"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetCategoryOfProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/999/category"))
                .andExpect(status().isNotFound());
    }


    @Test
        @WithMockUser(roles = "ADMIN")
        public void shouldDeleteProductById() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                    .delete("/products/14"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        public void shouldFailDeleteProductById() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                    .delete("/products/999"))
                    .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(jsonToString(
                        ProductDto.builder()
                                .categoryId(1)
                                .name("testProductCreated")
                                .price(999)
                                .salePrice(9999)
                                .isSale(false)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToCreateNewProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(jsonToString(
                        ProductDto.builder()
                                .categoryId(9999)
                                .name("testProductCreated")
                                .price(999)
                                .salePrice(9999)
                                .isSale(false)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/products/1")
                .content(jsonToString(
                        ProductDto.builder()
                                .categoryId(1)
                                .name("updatedName")
                                .price(999)
                                .salePrice(9999)
                                .isSale(false)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updatedName"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToUpdateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/products/99999")
                .content(jsonToString(
                        ProductDto.builder()
                                .categoryId(1)
                                .name("updatedName")
                                .price(999)
                                .salePrice(9999)
                                .isSale(false)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
