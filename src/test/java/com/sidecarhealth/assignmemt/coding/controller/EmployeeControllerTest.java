package com.sidecarhealth.assignmemt.coding.controller;

import com.sidecarhealth.assignmemt.coding.dao.EmployeeDao;
import com.sidecarhealth.assignmemt.coding.model.Employee;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EmployeeControllerTest {
    @InjectMocks
    EmployeeController controller;
    /*
       Normally, I would create a service layer and avoid accessing dao in controller directly.
       That is to encapsulate the business logic in the service layer.
       But it is probably an overkill in this case.
     */
    @Mock
    EmployeeDao dao;
    @Mock
    Logger logger;
    private static Employee employeeMock;

    @BeforeAll
    static void setupMock() {
        employeeMock = new Employee();
        employeeMock.setId("test id");
        employeeMock.setName("test name");
    }

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get single employee with id")
    public void testGetEmployee() {
        controller.getEmployee(employeeMock.getId());
        verify(dao, times(1)).read(employeeMock.getId());
    }

    @Test
    @DisplayName("Update an employee")
    public void testPutEmployee() {
        when(dao.update(employeeMock)).thenReturn(true);
        controller.putEmployee(employeeMock);

        verify(dao, times(1)).update(employeeMock);
        verify(dao, times(0)).create(employeeMock);
    }

    @Test
    @DisplayName("Should create a new employee when it can't be found")
    public void testUpdateCreateEmployee() {
        when(dao.update(employeeMock)).thenReturn(false);
        controller.putEmployee(employeeMock);

        verify(dao, times(1)).update(employeeMock);
        verify(dao, times(1)).create(employeeMock);
        verify(logger, times(1)).info(anyString());
    }

    @Test
    @DisplayName("Should create employee")
    public void testCreateEmployee() {
        controller.createEmployee(employeeMock);

        verify(dao, times(1)).create(employeeMock);
    }

    @Test
    @DisplayName("Should delete the employee")
    public void testDeleteEmployee() {
        controller.deleteEmployee(employeeMock.getId());

        verify(dao, times(1)).delete(employeeMock.getId());
    }

    @Test
    @DisplayName("Get employee count")
    public void testGetEmployeeCount() {
        when(dao.getEmployeeCount()).thenReturn(0);
        controller.getEmployeeCount();

        verify(dao, times(1)).getEmployeeCount();
    }

    @Test
    @DisplayName("Get all employees")
    public void testGetAllEmployees() {
        when(dao.getAllEmployees()).thenReturn(null);
        controller.getAllEmployee();

        verify(dao, times(1)).getAllEmployees();
    }

}