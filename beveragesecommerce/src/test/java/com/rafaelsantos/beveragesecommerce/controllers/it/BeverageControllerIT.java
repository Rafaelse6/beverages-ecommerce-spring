package com.rafaelsantos.beveragesecommerce.controllers.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelsantos.beveragesecommerce.entities.Beverage;
import com.rafaelsantos.beveragesecommerce.entities.Category;
import com.rafaelsantos.beveragesecommerce.entities.DTO.BeverageDTO;
import com.rafaelsantos.beveragesecommerce.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BeverageControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingBeverageId, nonExistingBeverageId, dependentBeverageId;
    private String beverageName;
    private BeverageDTO beverageDTO;
    private Beverage beverage;

    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken, invalidToken;

    @BeforeEach
    void setUp() throws Exception {
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        existingBeverageId = 2L;
        nonExistingBeverageId = 100L;
        dependentBeverageId = 1L;

        Category category = new Category(2L, "Non-Alcoholic");

        beverage = new Beverage(null, "Orange Juice", "'Fresh squeezed orange juice", 0.50, "https://example.com/orangejuice.jpg");
        beverage.getCategories().add(category);

        beverageDTO = new BeverageDTO(beverage);
        beverageName = "Pepsi";

        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {

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
    public void findAllShouldReturnPageWhenParamIsEmpty() throws Exception {
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
    public void insertShouldReturnBeverageDTOCreatedWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(beverageDTO);

        ResultActions result = mockMvc
                .perform(post("/beverages")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void insertShouldThrowUnprocessableEntityWHenAdminLoggedAndInvalidName() throws Exception {
        Beverage beverage = new Beverage();
        beverage.setName("ab");
        beverageDTO = new BeverageDTO(beverage);

        String jsonBody = objectMapper.writeValueAsString(beverageDTO);

        ResultActions result = mockMvc
                .perform(post("/beverages")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowUnprocessableEntityWhenAdminLoggedAndInvalidDescription() throws Exception {
        Beverage beverage = new Beverage();
        beverage.setName("ab");
        beverageDTO = new BeverageDTO(beverage);

        String jsonBody = objectMapper.writeValueAsString(beverageDTO);

        ResultActions result = mockMvc
                .perform(post("/beverages")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowUnprocessableEntityWhenAdminLoggedAndNegativePrice() throws Exception {
        Beverage beverage = new Beverage();
        beverage.setPrice(-50.0);
        beverageDTO = new BeverageDTO(beverage);

        String jsonBody = objectMapper.writeValueAsString(beverageDTO);

        ResultActions result = mockMvc
                .perform(post("/beverages")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowUnprocessableEntityWhenAdminLoggedAndPriceIsZero() throws Exception {
        Beverage beverage = new Beverage();
        beverage.setPrice(0.00);
        beverageDTO = new BeverageDTO(beverage);

        String jsonBody = objectMapper.writeValueAsString(beverageDTO);

        ResultActions result = mockMvc
                .perform(post("/beverages")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowUnprocessableEntityWhenAdminLoggedAndBeverageHasNoCategory() throws Exception {
        Beverage beverage = new Beverage();
        beverage.getCategories().clear();
        beverageDTO = new BeverageDTO(beverage);

        String jsonBody = objectMapper.writeValueAsString(beverageDTO);

        ResultActions result = mockMvc
                .perform(post("/beverages")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowForbiddenWhenClientLogged() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(beverageDTO);

        ResultActions result = mockMvc.perform(post("/beverages").header("Authorization", "Bearer " + clientToken)
                .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExistsAndAdminLogged() throws Exception {
        ResultActions result = mockMvc.perform(delete("/beverages/{id}", existingBeverageId)
                .header("Authorization", "Bearer " + adminToken).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdExistsAndAdminLogged() throws Exception {
        ResultActions result = mockMvc.perform(delete("/beverages/{id}", nonExistingBeverageId)
                .header("Authorization", "Bearer " + adminToken).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteShouldReturnBadRequestWhenIdExistsAndAdminLogged() throws Exception {
        ResultActions result = mockMvc.perform(delete("/beverages/{id}", dependentBeverageId)
                .header("Authorization", "Bearer " + adminToken)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnForbiddenWhenClientLogged() throws Exception {
        ResultActions result = mockMvc.perform(delete("/beverages/{id}", existingBeverageId)
                .header("Authorization", "Bearer " + clientToken).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldReturnUnauthorizedWhenIdExistsAndInvalidToken() throws Exception {
        ResultActions result = mockMvc.perform(delete("/beverages/{id}", existingBeverageId)
                .header("Authorization", "Bearer " + invalidToken).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }
}
