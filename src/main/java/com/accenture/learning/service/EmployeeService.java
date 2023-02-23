package com.accenture.learning.service;

import com.accenture.learning.dto.EmployeeDTO;
import com.accenture.learning.entity.Employee;
import com.accenture.learning.exception.EmployeeNotFoundException;
import com.accenture.learning.mapper.EmployeeMapper;
import com.accenture.learning.repository.EmployeeRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    private EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    public List<EmployeeDTO> search(EmployeeDTO dto) {
        Employee entity = mapper.dtoToEntity(dto);
        return repo.findAll(Example.of(entity)).stream()
                .map(mapper::entityToDTO).toList();
    }

    public EmployeeDTO insert(EmployeeDTO dto) {
        System.out.println(dto.getFirstName());
        Employee entity = mapper.dtoToEntity(dto);
        return mapper.entityToDTO(repo.save(entity));
    }

    public EmployeeDTO update(EmployeeDTO dto) throws EmployeeNotFoundException {
        Employee entity = Optional.ofNullable(dto)
                .map(EmployeeDTO::getId)
                .flatMap(repo::findById)
                .orElseThrow(EmployeeNotFoundException::new);
        return mapper.entityToDTO(repo.save(mapper.updateEntity(entity, dto)));
    }

    public EmployeeDTO merge(EmployeeDTO dto) throws EmployeeNotFoundException {
        Employee entity = Optional.ofNullable(dto)
                .map(EmployeeDTO::getId)
                .flatMap(repo::findById)
                .orElseThrow(EmployeeNotFoundException::new);
        return mapper.entityToDTO(repo.save(mapper.mergeEntity(entity, dto)));
    }

    public EmployeeDTO get(Long id) throws EmployeeNotFoundException {
        return Optional.ofNullable(id)
                .flatMap(repo::findById)
                .map(mapper::entityToDTO)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public void delete(Long id) throws EmployeeNotFoundException {
        Employee bean = Optional.ofNullable(id)
                .flatMap(repo::findById)
                .orElseThrow(EmployeeNotFoundException::new);
        repo.delete(bean);
    }
}
