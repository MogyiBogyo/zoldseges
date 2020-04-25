package org.elte.zoldseges.ControllerTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.IncomeDto;
import org.elte.zoldseges.dto.SaleDto;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class SaleControllerTest {

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
    public void shouldGetAllSales() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/sales"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetSaleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/sales/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetSaleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/sales/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetProductOfSale() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/sales/1/product"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetProductOfSale() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/sales/9999/product"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteSaleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/sales/4"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToDeleteSaleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/sales/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewSale() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/sales")
                .content(jsonToString(
                        SaleDto.builder()
                                .date(date)
                                .quantity(22)
                                .buyer("Kis Kinga")
                                .price(125)
                                .productId(1)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToCreateNewSale() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/sales")
                .content(jsonToString(
                        SaleDto.builder()
                                .date(date)
                                .quantity(22)
                                .buyer("Kis Kinga")
                                .price(125)
                                .productId(9999)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateSale() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2021/12/12");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/incomes/1")
                .content(jsonToString(
                        SaleDto.builder()
                                .date(date)
                                .quantity(22)
                                .buyer("Kis Kinga")
                                .price(125)
                                .productId(1)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(22));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToUpdateSale() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2021/12/12");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/incomes/999")
                .content(jsonToString(
                        SaleDto.builder()
                                .date(date)
                                .quantity(22)
                                .buyer("Kis Kinga")
                                .price(125)
                                .productId(1)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
