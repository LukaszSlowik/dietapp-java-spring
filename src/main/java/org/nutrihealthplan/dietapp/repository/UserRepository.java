package org.nutrihealthplan.dietapp.repository;


import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.UserEntity;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

}
