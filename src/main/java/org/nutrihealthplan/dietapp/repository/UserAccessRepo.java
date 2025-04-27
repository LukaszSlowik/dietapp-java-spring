package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.model.UserAccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccessRepo extends JpaRepository<UserAccessEntity,Long> {
    List<UserAccessEntity> findByTarget_Id(Long targetUserId);
    //List<UserAccessEntity> findByAccessor_UserIdAndPermissionsContains(Long accessorUserId, String permission);
}
