package com.accenture.learning.mapper;

import com.accenture.learning.dto.EmployeeDTO;
import com.accenture.learning.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO entityToDTO(Employee entity);

    Employee dtoToEntity(EmployeeDTO dto);

    Employee updateEntity(@MappingTarget Employee entity, EmployeeDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    Employee mergeEntity(@MappingTarget Employee entity, EmployeeDTO dto);
}
