package com.rafaelsantos.beveragesecommerce.controllers.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BeverageControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private String beverageName;

    private String adminToken;

    @BeforeEach
    void setUp() throws Exception{
        beverageName = "Pepsi";
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception{

        ResultActions result = mockMvc
                .perform(get("/beverages?name={beverageName}", beverageName)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(2L));
        result.andExpect(jsonPath("$.content[0].name").value("Pepsi"));
        result.andExpect(jsonPath("$.content[0].price").value(1.89));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://example.com/pepsi.jpg"));
    }

    @Test
    public void findAllShouldReturnPageWhenParamIsEmpty() throws Exception{
        ResultActions result = mockMvc
                .perform(get("/beverages", beverageName)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(1L));
        result.andExpect(jsonPath("$.content[0].name").value("Coca-Cola"));
        result.andExpect(jsonPath("$.content[0].price").value(1.99));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://example.com/cocacola.jpg"));
    }

    @Test
    public void insertShouldReturnBeverageDTOCreatedWhenAdminLogged() throws Exception{
        String jsonBody = "";

        ResultActions result = mockMvc
                .perform(post("/beverages")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON));
    }
}
