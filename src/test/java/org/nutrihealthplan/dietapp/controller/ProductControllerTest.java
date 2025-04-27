package org.nutrihealthplan.dietapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.security.test.context.support;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nutrihealthplan.dietapp.dto.ProductCreateRequest;
import org.nutrihealthplan.dietapp.dto.ProductCreateResponse;
import org.nutrihealthplan.dietapp.service.ProductService;
import org.nutrihealthplan.dietapp.utils.JsonTestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;



import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(ProductController.class)
//@AutoConfigureMockMvc // needed nly for SpingBootTest
public class ProductControllerTest {

    private static long startTime;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ProductService productService;


//    @Value("classpath:product-create-request-invalid-enum.json")
//    private Resource invalidJsonResource;
//    @Value("classpath:product-create-request-valid.json")
//    private Resource validJsonResource;
//    @Value("classpath:product-create-response-valid-products-part.json")
//    private Resource validJsonResponseProductsPart;

    @BeforeAll
    static void setUp() {
        startTime = System.nanoTime();
    }

    @Test
    @WithMockUser(username = "test")
    public void createProduct_invalidJson_returnsBadRequest() throws Exception {
        //String invalidJson = Files.readString(Paths.get("src/test/resources/product-create-request-invalid-enum.json"));
        //String invalidJson = new String(invalidJsonResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String invalidJson = JsonTestUtil.readJson("product-create-request-invalid-enum.json");
        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message", containsString("not one of the values accepted for Enum class")));

    }

    @Test
    @WithMockUser(username = "test")
    public void createProduct_validJson_returnsSuccess() throws Exception {


        //get data request and response from jsons
        //String validJson = new String(validJsonResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        //String validJson = Files.readString(Paths.get("src/test/resources/product-create-request-valid.json"));
        String validJson = JsonTestUtil.readJson("product-create-request-valid.json");

        //String validJsonResponseProductsPartJson = new String(validJsonResponseProductsPart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        //String validJsonResponseProductsPartJson = Files.readString(Paths.get("src/test/resources/product-create-response-valid-products-part.json"));
        String validJsonResponseProductsPartJson = JsonTestUtil.readJson("product-create-response-valid-products-part.json");
        //List<ProductCreateRequest> productCreateRequest = objectMapper.readValue(validJson, new TypeReference<>() {
        //        }
        // );
        List<ProductCreateResponse> productCreateResponses = objectMapper.readValue(validJsonResponseProductsPartJson, new TypeReference<>() {
        });


        when(productService.createProduct(anyList())).thenReturn(productCreateResponses);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(validJson))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Operation successful"))
                .andExpect(jsonPath("$.data").exists());
    }

    @AfterAll
    static void tearDown() {
        long endTime = System.nanoTime();
        long durationInMs = (endTime - startTime) / 1_000_000;
        System.out.println("Test duration: " + durationInMs + " ms");
    }
}
//
//    @Test
//    public void createProduct_emptyBody_returnsBadRequest() throws Exception {
//        mockMvc.perform(post("/api/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("[]")) // Pusty JSON
//                .andExpect(status().isBadRequest()) // Sprawdzamy, czy status to 400
//                .andExpect(jsonPath("$.status").value("error"))
//                .andExpect(jsonPath("$.message").exists())
//                .andExpect(jsonPath("$.data").doesNotExist());
//    }
