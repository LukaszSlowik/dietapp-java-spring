package org.nutrihealthplan.dietapp.repository;

import org.junit.jupiter.api.Test;
import org.nutrihealthplan.dietapp.dto.filter.ProductFilter;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.enums.MatchType;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DataJpaTest
@ActiveProfiles("test") //to use inMemory base
@Sql(scripts = "/sql/test-products-contains.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProductSpecificationTest {

    @Autowired
    private ProductRepository productRepository;

    @ParameterizedTest
    @CsvSource({
            "abl,            CONTAINS,     2",
            "jablko,         EQUAL,        1",
            "jab,            STARTS_WITH,  1",
            "ko,             ENDS_WITH,    1",
            "notexist,       CONTAINS,     0"
    })
    void testProductNameMatching(String name, MatchType matchType, int expectedSize) {
        ProductFilter filter = new ProductFilter();
        filter.setName(name);
        filter.setNameMatchType(matchType);
        List<ProductEntity> results = productRepository.findAll(ProductSpecifications.buildFromFilter(filter));
        assertEquals(expectedSize, results.size());
    }

    @Test
    void testScopeFilter() {
        ProductFilter filter = new ProductFilter();
        filter.setScope(Scope.PRIVATE);
        List<ProductEntity> results = productRepository.findAll(ProductSpecifications.buildFromFilter(filter));
        assertEquals(1, results.size());
    }
}
