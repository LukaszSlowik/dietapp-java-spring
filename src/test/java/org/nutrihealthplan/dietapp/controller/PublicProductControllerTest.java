package org.nutrihealthplan.dietapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nutrihealthplan.dietapp.config.RedisConfig;
import org.nutrihealthplan.dietapp.service.PublicProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PublicProductController.class)
public class PublicProductControllerTest {
    private static long startTime;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private PublicProductService publicProductService;
    @MockitoBean
    private RedisConfig redisConfig;



    @BeforeAll
    static void setUp(){startTime = System.nanoTime();}

    @Test
    public void getPublicProducts_validJson_returnsSuccess() throws Exception {

        mockMvc.perform((get("/api/public/products"))
                .with(csrf())
                .param("name","banan")
                .param("page", "0")
                .param("size","10"))
                .andExpect(status().isOk());


    }




    @AfterAll
    static void tearDown(){
        long endTime = System.nanoTime();
        long durationInMs = (endTime - startTime)/ 1_000_000;
        System.out.println("Test duration: " + durationInMs + " ms");
    }
}
