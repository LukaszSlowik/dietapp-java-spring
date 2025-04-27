package org.nutrihealthplan.dietapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nutrihealthplan.dietapp.dto.meal.MealProductCreateResponse;
import org.nutrihealthplan.dietapp.security.SecurityConfig;
import org.nutrihealthplan.dietapp.service.MealProductService;
import org.nutrihealthplan.dietapp.utils.JsonTestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MealController.class)

class MealControllerTest {

    private static long startTime;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private MealProductService mealProductService;

    @BeforeAll
    static void setUp(){ startTime = System.nanoTime();}

    @Test
    @WithMockUser(username = "test")
    public void createMeal_validJson_returnsSuccess() throws Exception{
        String validRequestJson = JsonTestUtil.readJson("meal-create-request-valid.json");
        String validResponseJson = JsonTestUtil.readJson(("meal-create-response-valid.json"));
       MealProductCreateResponse mealProductCreateResponse = objectMapper.readValue(validResponseJson, MealProductCreateResponse.class);
        System.out.println("MOCK RESPONSE: " + objectMapper.writeValueAsString(mealProductCreateResponse));

       when(mealProductService.createMeal(any())).thenReturn(mealProductCreateResponse);

       mockMvc.perform(post("/api/meals")
               .contentType(MediaType.APPLICATION_JSON)
               .with(csrf())
               .content(validRequestJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.mealId").exists())
               .andExpect(jsonPath("$.data.mealNumber").value(1))
               .andExpect(jsonPath("$.data.products").isArray())
               .andExpect(jsonPath("$.data.products[0].productId").value(101))
               .andExpect(jsonPath("$.data.products[0].productName").value("Apple"))
               .andExpect(jsonPath("$.data.products[0].nutrition.kcal").value(52))
               .andExpect(jsonPath("$.data.products[0].nutrition.protein").value(0.3))
               .andExpect(jsonPath("$.data.nutrition.kcal").value(202))
               .andExpect(jsonPath("$.data.nutrition.protein").value(8.3))
               .andExpect(jsonPath("$.data.nutrition.fat").value(8.2))
               .andExpect(jsonPath("$.data.nutrition.carbs").value(26))
               .andExpect(jsonPath("$.data.message").value("Meal created successfully"));

    }
    @Test
    @WithMockUser(username = "test")
    void createMeal_invalidAmount_returnsBadRequest() throws Exception {
        String invalidRequestJson = JsonTestUtil.readJson("meal-create-request-invalid.json");

        mockMvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createMeal_unauthenticated_returnsUnauthorized() throws Exception {
        String validRequestJson = JsonTestUtil.readJson("meal-create-request-valid.json");

        mockMvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(validRequestJson))
                        .andExpect(status().isUnauthorized());
    }


    @AfterAll
    static void tearDown() {
        long endTime = System.nanoTime();
        long durationInMs = (endTime - startTime) / 1_000_000;
        System.out.println("Test duration: " + durationInMs + " ms");
    }
}