package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.UserEntity;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long>, JpaSpecificationExecutor<ProductEntity> {
//    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productUnitEntities WHERE p.id = :id")
//    Optional<ProductEntity> findByIdWithUnits(@Param("id") Long id);
//    @Query(
//            value = """
//        SELECT p FROM ProductEntity p
//        LEFT JOIN FETCH p.productUnitEntities
//        """,
//            countQuery = """
//        SELECT COUNT(p) FROM ProductEntity p
//        """
//    )
//
//    Page<ProductEntity> findAllWithUnits(Pageable pageable);
    @EntityGraph(attributePaths = {"productUnitEntities"})
    Page<ProductEntity> findAll(Pageable pageable);
    List<ProductEntity> findByOwnerIdAndScope(Long ownerId, Scope scope);

}
