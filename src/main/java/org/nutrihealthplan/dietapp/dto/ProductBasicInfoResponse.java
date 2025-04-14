package org.nutrihealthplan.dietapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutrihealthplan.dietapp.model.UserEntity;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasicInfoResponse {
    private Long id;
    private String name;
    private double kcalPer100g;
    private double fatPer100g;
    private OwnerInfo owner;
    private String scope;
    private List<UnitInfo> units;


}
