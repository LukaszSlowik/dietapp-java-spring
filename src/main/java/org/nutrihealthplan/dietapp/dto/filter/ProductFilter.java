package org.nutrihealthplan.dietapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutrihealthplan.dietapp.model.enums.MatchType;
import org.nutrihealthplan.dietapp.model.enums.Scope;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {
    private String name;
    private MatchType nameMatchType;
    private Scope scope;
    private Long userId;
    private Boolean available;
    private Double priceFrom;
    private Double priceTo;

}