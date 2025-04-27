package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.model.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {
}
