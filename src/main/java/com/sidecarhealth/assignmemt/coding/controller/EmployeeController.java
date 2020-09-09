package com.sidecarhealth.assignmemt.coding.controller;

import com.sidecarhealth.assignmemt.coding.dao.EmployeeDao;
import com.sidecarhealth.assignmemt.coding.model.Employee;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    Logger logger;

    @GetMapping
    public Employee getEmployee(@RequestParam String id) {
        return employeeDao.read(id);
    }

    @GetMapping("/count")
    @ApiOperation(value = "Get total employee number",
            notes = "This returns the total number of employees.")
    public int getEmployeeCount() {
        return employeeDao.getEmployeeCount();
    }

    /**
     * Get all the employees in the database.
     * This is just for demonstration purposes.
     * @return
     */
    @GetMapping("/all")
    public List<Employee> getAllEmployee() {
        return employeeDao.getAllEmployees();
    }

    /**
     * Assuming the id generation process happens on the client.
     * @param employee
     * @return
     */
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        insertEmployee(employee);

        return employee;
    }

    @PutMapping
    public Employee putEmployee(@RequestBody Employee employee) {
        if(!employeeDao.update(employee)) {
            logger.info("Unable to find employee with id " + employee.getId() + ". creating the employee..");
            insertEmployee(employee);
        }
        return employee;
    }

    @DeleteMapping
    public void deleteEmployee(@RequestParam String id) {
        employeeDao.delete(id);
    }

    private void insertEmployee(Employee employee) {
        try {
            employeeDao.create(employee);
        } catch(DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Employee id already exists.", e);
        }
    }

}
