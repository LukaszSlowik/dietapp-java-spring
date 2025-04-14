package org.nutrihealthplan.dietapp.repository;

import org.junit.jupiter.api.Test;
import org.nutrihealthplan.dietapp.dto.ProductsGetResponse;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.enums.MatchType;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductSpecificationTest {

    @Autowired
    private ProductRepository productRepository;

    @Sql(scripts = "/sql/test-products-contains.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    //@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void testContainsName(){
//        ProductEntity product1 = ProductEntity.builder()
//                .name("Sok jabłkowy")
//                .scope(Scope.GLOBAL)
//                .build();
//
//        ProductEntity product2 = ProductEntity.builder()
//                .name("masło")
//                .scope(Scope.GLOBAL)
//                .build();
//
//        ProductEntity product3 = ProductEntity.builder()
//                .name("Sok marchewkowy")
//                .scope(Scope.GLOBAL)
//                .build();
//
//        ProductEntity product4 = ProductEntity.builder()
//                .name("Ser")
//                .scope(Scope.GLOBAL)
//                .build();
//
//        ProductEntity product5 = ProductEntity.builder()
//                .name("jabłecznik")
//                .scope(Scope.GLOBAL)
//                .build();
//      productRepository.saveAll(List.of(product1,product2,product3,product4,product5));
        Specification<ProductEntity> spec = ProductSpecifications.nameMatches("jab", MatchType.CONTAINS);
        List<ProductEntity> results = productRepository.findAll(spec);

        assertEquals(2, results.size());
    }

}
