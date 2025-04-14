package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.dto.filter.ProductFilter;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.enums.MatchType;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<ProductEntity> nameMatches(String name, MatchType matchType) {
        return (root, query, cb) -> {
            if (name == null || matchType == null) return null;

            return switch (matchType) {
                case EQUAL -> cb.equal(cb.lower(root.get("name")), name.toLowerCase());
                case CONTAINS -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
                case STARTS_WITH -> cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%");
                case ENDS_WITH -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase());
            };
        };
    }

    public static Specification<ProductEntity> hasNameLike(String name) {
        return ((root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%")
        );
    }
    public static Specification<ProductEntity> hasName(String name){
        return (root,query,cb)-> name == null ? null :
                cb.equal(root.get("name"),name);
    }

    public static Specification<ProductEntity> hasScope(Scope scope) {
        return (root, query, cb) -> scope == null ? null :
                cb.equal(root.get("scope"), scope);
    }

    public static Specification<ProductEntity> isOwnedBy(Long userId) {
        return (root, query, cb) -> userId == null ? null :
                cb.equal(root.get("owner").get("id"), userId);

    }

    public static Specification<ProductEntity> includePrivateOnlyIfOwned(Scope scope, Long userId) {
        return (root, query, cb) ->
        {
            if (scope == null || scope == Scope.GLOBAL) {
                return cb.equal(root.get("scope"), Scope.GLOBAL);
            } else {
                return cb.and(
                        cb.equal(root.get("scope"), Scope.PRIVATE),
                        cb.equal(root.get("owner").get("id"), userId)
                );
            }
        };
    }
    public static Specification<ProductEntity> buildFromFilter(ProductFilter filter){
        Specification<ProductEntity> spec = Specification.where(null);
        if(filter.getName() != null && !filter.getName().isBlank())
        {
            spec = spec.and(ProductSpecifications.nameMatches(filter.getName(),filter.getNameMatchType()));
        }
        if (filter.getScope() != null) {
            spec = spec.and(ProductSpecifications.hasScope(filter.getScope()));
        }
        return spec;
    }
}


