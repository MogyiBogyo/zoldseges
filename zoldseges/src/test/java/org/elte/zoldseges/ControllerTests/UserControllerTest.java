package org.elte.zoldseges.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elte.zoldseges.dto.UserDto;
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
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

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
    public void shouldGetAllEnabledUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetAllDisabledUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/disabled"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetUserWorktimes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/1/worktimes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailGetUserWorktimes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/9999/worktimes"))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/4"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailDeleteUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRegisterNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .content(jsonToString(
                        UserDto.builder()
                                .username("cica")
                                .password("mica")
                                .email("ci@ca.com")
                                .familyname("Nagy")
                                .givenname("Lajos")
                                .enable(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToRegisterNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .content(jsonToString(
                        UserDto.builder()
                                .username("Pistike")
                                .password("mica")
                                .email("ci@ca.com")
                                .familyname("Nagy")
                                .givenname("Lajos")
                                .enable(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(jsonToString(
                        UserDto.builder()
                                .username("cica")
                                .password("mica")
                                .email("ci@ca.com")
                                .familyname("Nagy")
                                .givenname("Lajos")
                                .enable(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToCreateNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(jsonToString(
                        UserDto.builder()
                                .username("Pistike")
                                .password("mica")
                                .email("ci@ca.com")
                                .familyname("Nagy")
                                .givenname("Lajos")
                                .enable(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/users/1")
                .content(jsonToString(
                        UserDto.builder()
                                .username("Pistike")
                                .password("password")
                                .email("ci@ca.com")
                                .familyname("Nagy")
                                .givenname("Lajos")
                                .enable(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ci@ca.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/users/9999")
                .content(jsonToString(
                        UserDto.builder()
                                .username("Pistike")
                                .password("password")
                                .email("ci@ca.com")
                                .familyname("Nagy")
                                .givenname("Lajos")
                                .enable(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldFailToMakeAdminUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/users/9999/makeAdmin")
                .content(jsonToString(
                        UserDto.builder()
                                .username("Pistike")
                                .password("password")
                                .email("ci@ca.com")
                                .familyname("Nagy")
                                .givenname("Lajos")
                                .enable(true)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
