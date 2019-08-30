package com.example.springbootswagger.controller;

import com.example.springbootswagger.exception.ResourceNotFoundException;
import com.example.springbootswagger.model.Employee;
import com.example.springbootswagger.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Operations pertaining to employee in Employee Management System")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

  private final EmployeeRepository repository;

  public EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }

  @ApiOperation(value = "View a list of available employees", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @GetMapping
  public List<Employee> getAllEmployees() {
    return repository.findAll();
  }

  @ApiOperation(value = "Get an employee by Id")
  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(
      @ApiParam(value = "Employee id from which employee object will retrieve", required = true)
      @PathVariable(value = "id") Long employeeId)
      throws ResourceNotFoundException {
    Employee employee = repository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    return ResponseEntity.ok().body(employee);
  }

  @ApiOperation(value = "Add an employee")
  @PostMapping
  public Employee createEmployee(
      @ApiParam(value = "Employee object store in database table") @Valid @RequestBody Employee employee) {
    return repository.save(employee);
  }

  @ApiOperation(value = "Update an employee")
  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(
      @ApiParam(value = "Employee Id to update employee object", required = true)
      @PathVariable(value = "id") Long employeeId,
      @ApiParam(value = "Update employee object", required = true)
      @Valid @RequestBody Employee employeeDetails)
      throws ResourceNotFoundException {
    Employee employee = repository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    employee.setEmail(employeeDetails.getEmail());
    employee.setLastName(employeeDetails.getLastName());
    employee.setFirstName(employeeDetails.getFirstName());
    Employee updateEmployee = repository.save(employee);
    return ResponseEntity.ok(updateEmployee);
  }

  @ApiOperation(value = "Delete an employee")
  @DeleteMapping("/{id}")
  public Map<String, Boolean> deleteEmployee(
      @ApiParam(value = "Employee Id from which employee object will delete from database table", required = true)
      @PathVariable(value = "id") Long employeeId)
      throws ResourceNotFoundException {
    Employee employee = repository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" + employeeId));
    repository.delete(employee);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
