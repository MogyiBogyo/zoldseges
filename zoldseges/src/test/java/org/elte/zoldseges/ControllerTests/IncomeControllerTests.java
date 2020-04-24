package org.elte.zoldseges.ControllerTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.IncomeDto;
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


@SpringBootTest
@AutoConfigureMockMvc
public class IncomeControllerTests {

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
    public void shouldGetAllIncomes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/incomes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetIncomeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/incomes/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetIncomeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/incomes/9999"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteIncomeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/incomes/4"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailDeleteIncomeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/incomes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewIncome() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/incomes")
                .content(jsonToString(
                        IncomeDto.builder()
                                .date(date)
                                .quantity(22)
                                .seller("Kis Kinga")
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
    public void shouldFailToCreateNewIncome() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/incomes")
                .content(jsonToString(
                        IncomeDto.builder()
                                .date(date)
                                .quantity(22)
                                .seller("Kis Kinga")
                                .price(125)
                                .productId(9999)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateIncome() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2021/12/12");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/incomes/1")
                .content(jsonToString(
                        IncomeDto.builder()
                                .date(date)
                                .quantity(22)
                                .seller("Kis Kinga")
                                .price(125)
                                .productId(1)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.seller").value("Kis Kinga"));
    }


    @Test
    @WithMockUser(roles = "USER")
    public void shouldFailToUpdateIncome() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2021/12/12");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/incomes/9999").content(jsonToString(
                        IncomeDto.builder()
                                .date(date)
                                .quantity(22)
                                .seller("Kis Kinga")
                                .price(125)
                                .productId(1)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
