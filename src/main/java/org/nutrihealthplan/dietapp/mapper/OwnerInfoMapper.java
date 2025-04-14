package org.nutrihealthplan.dietapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nutrihealthplan.dietapp.dto.OwnerInfo;
import org.nutrihealthplan.dietapp.model.UserEntity;

@Mapper(componentModel = "spring")
public interface OwnerInfoMapper {

    OwnerInfo toDto(UserEntity entity);
}
