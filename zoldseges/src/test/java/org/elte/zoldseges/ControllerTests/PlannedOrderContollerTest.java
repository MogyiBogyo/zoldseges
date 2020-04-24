package org.elte.zoldseges.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.PlannedOrderDto;
import org.elte.zoldseges.entities.PlannedOrder;
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
public class PlannedOrderContollerTest {

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
    public void shouldGetAllPlans() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/plans"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetPlanById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/plans/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetPlannedOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/plans/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetProductsOfAPlanById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/plans/1/product"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetProductsOfAPlanById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/plans/99999/product"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeletePlannedOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/plans/3"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToDeletePlannedOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/plans/9999"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewPlannedOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/plans")
                .content(jsonToString(
                        PlannedOrderDto.builder()
                                .productId(1)
                                .quantity(11)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToCreateNewPlannedOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/plans")
                .content(jsonToString(
                        PlannedOrderDto.builder()
                                .productId(9999)
                                .quantity(11)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldUpdatePlannedOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/plans/1")
                .content(jsonToString(
                        PlannedOrderDto.builder()
                                .productId(1)
                                .quantity(11)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(11));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldFailToUpdatePlannedOrderByBadProductId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/plans/1")
                .content(jsonToString(
                        PlannedOrderDto.builder()
                                .productId(9999)
                                .quantity(11)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(roles = "USER")
    public void shouldFailToUpdatePlanByBadPlanId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/plans/9999")
                .content(jsonToString(
                        PlannedOrderDto.builder()
                                .productId(1)
                                .quantity(11)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldModifyAnExistedPlan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/plans/product/1")
                .content(jsonToString(
                        PlannedOrderDto.builder()
                                .productId(1)
                                .quantity(11)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(30));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldFailToModifyAnExistedPlan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/plans/product/4")
                .content(jsonToString(
                        PlannedOrderDto.builder()
                                .productId(4)
                                .quantity(11)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
