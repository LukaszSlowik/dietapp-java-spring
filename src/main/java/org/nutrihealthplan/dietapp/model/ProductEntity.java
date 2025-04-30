package org.nutrihealthplan.dietapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(
        indexes = {
                @Index(name="idx_product_name_scope", columnList = "scope, name")
        }
)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Scope scope;
    private BigDecimal kcalPer100g;
    private BigDecimal fatPer100g;
    private BigDecimal carbsPer100g;
    private BigDecimal proteinPer100g;

    @ManyToMany
    @JoinTable(
            name = "product_owner_group",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> ownerGroup = new HashSet<>();
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity createdBy;
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity modifiedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private UserEntity owner;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant modifiedDate;
    @OneToMany(mappedBy = "product")
    private List<MealProductEntity> productSelectionEntities = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductUnitEntity> productUnitEntities = new ArrayList<>();

    @Column(unique = true)
    private String nameAndScope;
    @PrePersist
    @PreUpdate
    private void generateUniqueCombination(){
        if(Scope.GLOBAL.equals(this.scope)){
            this.nameAndScope = name.toLowerCase().trim() + ":" + scope.name();
        } else if(Scope.PRIVATE.equals(this.scope)){
            this.nameAndScope = name.toLowerCase().trim() + ":" + owner.getId() + ":" + scope.name();
        }
    }
}



