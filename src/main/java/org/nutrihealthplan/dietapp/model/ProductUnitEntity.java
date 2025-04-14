package org.nutrihealthplan.dietapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.nutrihealthplan.dietapp.model.enums.UnitType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitType unitType;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
    @Positive
    @Column(nullable = false)
    private Double gramsPerUnit;
}
