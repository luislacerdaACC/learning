package com.accenture.learning.controller;

import com.accenture.learning.dto.EmployeeDTO;
import com.accenture.learning.exception.EmployeeNotFoundException;
import com.accenture.learning.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> list() {
        EmployeeDTO dto = EmployeeDTO.builder().build();
        return employeeService.search(dto);
    }

    @GetMapping("/{id}")
    public EmployeeDTO list(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeService.get(id);
    }

    @PostMapping
    public EmployeeDTO insert(@RequestBody EmployeeDTO dto) {
        return employeeService.insert(dto);
    }

    @PutMapping
    public EmployeeDTO update(@RequestBody  EmployeeDTO dto) throws EmployeeNotFoundException {
        return employeeService.update(dto);
    }

    @PatchMapping
    public EmployeeDTO merge(@RequestBody  EmployeeDTO dto) throws EmployeeNotFoundException {
        return employeeService.merge(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws EmployeeNotFoundException {
        employeeService.delete(id);
    }
}
