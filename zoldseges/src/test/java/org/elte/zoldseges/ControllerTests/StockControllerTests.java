package org.elte.zoldseges.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.ProductDto;
import org.elte.zoldseges.dto.StockDto;
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
public class StockControllerTests {
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
    public void shouldGetAllStocks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetStockById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stocks/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGeStockById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stocks/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetProductOfStockById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stocks/1/product"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetProductOfStockById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stocks/99999/product"))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteStockyId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/stocks/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToDeleteProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/stocks/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/stocks")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(1)
                                .quantity(10)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToCreateNewStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/stocks")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(9999)
                                .quantity(10)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/stocks/1")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(1)
                                .quantity(10)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value("10"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateStockQuantityById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/stocks/product/1")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(1)
                                .quantity(10)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value("25"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailUpdateStockQuantityById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/stocks/product/9999")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(9999)
                                .quantity(10)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDecreaseQuantityByProductId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/stocks/product/decrease/1")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(1)
                                .quantity(10)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value("5"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToDecreaseQuantityByProductId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/stocks/product/decrease/9999")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(9999)
                                .quantity(10)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToDecreaseQuantityByProductIdCauseBadQuantity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/stocks/product/decrease/1")
                .content(jsonToString(
                        StockDto.builder()
                                .productId(1)
                                .quantity(100)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

}
