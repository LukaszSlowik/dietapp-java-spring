package org.nutrihealthplan.dietapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.security.test.context.support;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nutrihealthplan.dietapp.dto.ProductCreateRequest;
import org.nutrihealthplan.dietapp.dto.ProductCreateResponse;
//import org.nutrihealthplan.dietapp.jwt.AuthTokenFilter;
//import org.nutrihealthplan.dietapp.jwt.AuthTokenFilter;
import org.nutrihealthplan.dietapp.jwt.AuthTokenFilter;
import org.nutrihealthplan.dietapp.service.ProductService;
import org.nutrihealthplan.dietapp.utils.JsonTestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.nutrihealthplan.dietapp.model.enums.Scope.PRIVATE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthTokenFilter.class))
//@Import(ProductMapperImpl.class)
//Import(SecurityConfig.class)
//@ContextConfiguration(classes = {AuthTokenFilter.class})
//@Import(TestSecurityConfig.class)
//@AutoConfigureMockMvc // needed nly for SpringBootTest


public class ProductControllerTest {

    private static long startTime;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;


    @Captor
    private ArgumentCaptor<List<ProductCreateRequest>> productCreateRequestCaptor;

    private static final TypeReference<List<ProductCreateResponse>> PRODUCT_CREATE_RESPONSE_LIST_TYPE =
            new TypeReference<>() {
            };
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
    //@PreAuthorize("hasRole('USER')")
    public void createProduct_validJson_returnsSuccess() throws Exception {

        String validJson = JsonTestUtil.readJson("product-create-request-valid.json");

        //String validJsonResponseProductsPartJson = new String(validJsonResponseProductsPart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        //String validJsonResponseProductsPartJson = Files.readString(Paths.get("src/test/resources/product-create-response-valid-products-part.json"));
        String validJsonResponseProductsPartJson = JsonTestUtil.readJson("product-create-response-valid-products-part.json");
        //List<ProductCreateRequest> productCreateRequest = objectMapper.readValue(validJson, new TypeReference<>() {
        //        }
        // );
        List<ProductCreateResponse> productCreateResponses = objectMapper.readValue(validJsonResponseProductsPartJson, PRODUCT_CREATE_RESPONSE_LIST_TYPE);

        when(productService.createProduct(any())).thenReturn(productCreateResponses);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(validJson))

                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Operation successful"))
                .andExpect(jsonPath("$.data[0].id").value(23))
                .andExpect(jsonPath("$.data[0].name").value("Chicken Breast"))
                .andExpect(jsonPath("$.data[0].scope").value("PRIVATE"));

//assert captured argument
        verify(productService).createProduct(productCreateRequestCaptor.capture());
        List<ProductCreateRequest> capturedRequestList = productCreateRequestCaptor.getValue();
        assertThat(capturedRequestList)
                .hasSize(1)
                .extracting(ProductCreateRequest::getName, ProductCreateRequest::getScope)
                .containsExactly(tuple("Chicken Breast", PRIVATE));


    }

    @AfterAll
    static void tearDown() {
        long endTime = System.nanoTime();
        long durationInMs = (endTime - startTime) / 1_000_000;
        System.out.println("Test duration: " + durationInMs + " ms");
    }
}

