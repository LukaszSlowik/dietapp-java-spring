package org.nutrihealthplan.dietapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutrihealthplan.dietapp.model.enums.MatchType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicProductFilter {
    private String name;
    private MatchType nameMatchType;
}
