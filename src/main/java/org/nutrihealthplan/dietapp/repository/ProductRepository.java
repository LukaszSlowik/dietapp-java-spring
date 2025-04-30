package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long>, JpaSpecificationExecutor<ProductEntity> {

    @EntityGraph(attributePaths = {"productUnitEntities"})
    Page<ProductEntity> findAll(Pageable pageable);
    List<ProductEntity> findByOwnerIdAndScope(Long ownerId, Scope scope);

}
