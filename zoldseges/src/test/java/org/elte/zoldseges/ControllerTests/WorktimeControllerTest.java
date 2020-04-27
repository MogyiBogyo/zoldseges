package org.elte.zoldseges.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.ProductDto;
import org.elte.zoldseges.dto.WorktimeDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class WorktimeControllerTest {
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
    public void shouldGetAllWorktimes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/worktimes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetWorktimesById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/worktimes/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetWorktimesById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/worktimes/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetUserOfWorktimeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/worktimes/1/user"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetUserOfWorktimeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/worktimes/99999/user"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteWorktimeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/worktimes/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailDeleteWorktimeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/worktimes/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewWorktime() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/worktimes")
                .content(jsonToString(
                        WorktimeDto.builder()
                                .date(date)
                                .userId(1)
                                .startHour("10:00")
                                .endHour("16:00")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToCreateNewWorktime() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/worktimes")
                .content(jsonToString(
                        WorktimeDto.builder()
                                .date(date)
                                .userId(9999)
                                .startHour("10:00")
                                .endHour("16:00")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateWorktime() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/worktimes/1")
                .content(jsonToString(
                        WorktimeDto.builder()
                                .date(date)
                                .userId(1)
                                .startHour("10:00")
                                .endHour("16:00")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToUpdateWorktime() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/worktimes/99999")
                .content(jsonToString(
                        WorktimeDto.builder()
                                .date(date)
                                .userId(1)
                                .startHour("10:00")
                                .endHour("16:00")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToUpdateWorktimeWithBadUserId() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("2020/03/04");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/worktimes/1")
                .content(jsonToString(
                        WorktimeDto.builder()
                                .date(date)
                                .userId(9999)
                                .startHour("10:00")
                                .endHour("16:00")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
