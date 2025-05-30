package org.nutrihealthplan.dietapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.nutrihealthplan.dietapp.model.enums.UnitType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "uc_meal_user_date_number",
                columnNames = {"owner_id", "meal_date", "meal_number"}
        )
)
public class MealProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitType unitType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal grams;

    @Column(nullable = false)
    private Integer mealNumber;

    @Column(nullable = false)
    private UUID mealId;
    @Column(nullable = false)
    private LocalDate mealDate;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User modifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant modifiedDate;
//    @Column(unique = true)
//    private String ucMealUserDateNumber;
//
//    @PrePersist
//    @PreUpdate
//    private void generateUcMealUserDateNumber(){
//        this.ucMealUserDateNumber =
//                this.owner.getId() + this.mealNumber +  this.mealDate.toString();
//    }


}