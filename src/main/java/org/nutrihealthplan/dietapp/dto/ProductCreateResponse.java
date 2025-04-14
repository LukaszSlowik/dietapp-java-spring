package org.nutrihealthplan.dietapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrihealthplan.dietapp.model.enums.Scope;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponse {
    private Long id;
    private String name;
    private String scope;
}
