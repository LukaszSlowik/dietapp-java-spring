package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.dto.filter.ProductFilter;
import org.nutrihealthplan.dietapp.dto.filter.PublicProductFilter;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.enums.MatchType;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<ProductEntity> nameEquals(String name) {
        return (root, query, cb) -> name == null ? null : cb.equal(cb.lower(root.get("name")), name.toLowerCase());
    }

    public static Specification<ProductEntity> nameContains(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<ProductEntity> nameStartsWith(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%");
    }

    public static Specification<ProductEntity> nameEndsWith(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase());
    }

    public static Specification<ProductEntity> hasScope(Scope scope) {
        return (root, query, cb) -> scope == null ? null : cb.equal(root.get("scope"), scope);
    }
    public static Specification<ProductEntity> onlyGlobalScope(){
        return (root,query,cb) -> cb.equal(root.get("scope"),Scope.GLOBAL);
    }

    public static Specification<ProductEntity> isOwnedBy(Long userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("owner").get("id"), userId);
    }

    public static Specification<ProductEntity> includePrivateOnlyIfOwned(Scope scope, Long userId) {
        return (root, query, cb) -> {
            if (scope == null || scope == Scope.GLOBAL) {
                return cb.equal(root.get("scope"), Scope.GLOBAL);
            }
            return cb.and(
                    cb.equal(root.get("scope"), Scope.PRIVATE),
                    cb.equal(root.get("owner").get("id"), userId)
            );
        };
    }

    private static Specification<ProductEntity> nameMatchSpec(String name,MatchType matchType){
        if(name == null || name.isBlank()) return null;
        if(matchType == null) {
            throw  new IllegalArgumentException("MatchType must be explicitly specified for name matching.");
        }
        return switch (matchType){
            case EQUAL -> nameEquals(name);
            case CONTAINS -> nameContains(name);
            case STARTS_WITH -> nameStartsWith(name);
            case ENDS_WITH -> nameEndsWith(name);
        };
    }

    public static Specification<ProductEntity> buildFromFilter(ProductFilter filter) {
        Specification<ProductEntity> spec = Specification.where(null);
        spec = spec.and(nameMatchSpec(filter.getName(),filter.getNameMatchType()));
        if (filter.getScope() != null) {
            spec = spec.and(hasScope(filter.getScope()));
        }

        return spec;
    }

    public static  Specification<ProductEntity> buildFromPublicFilter(PublicProductFilter filter){
        Specification<ProductEntity> spec = Specification.where(null);
        spec = spec.and(nameMatchSpec(filter.getName(),filter.getNameMatchType()));
        spec = spec.and(onlyGlobalScope());
        return spec;
    }
}