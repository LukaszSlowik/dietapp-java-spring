package org.nutrihealthplan.dietapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.nutrihealthplan.dietapp.dto.OwnerInfo;
import org.nutrihealthplan.dietapp.model.User;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OwnerInfoMapper {

    OwnerInfo toDto(User entity);
}
