import com.sidecarhealth.assignmemt.coding.CodingApplication;
import com.sidecarhealth.assignmemt.coding.dao.EmployeeDao;
import com.sidecarhealth.assignmemt.coding.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = CodingApplication.class)
@AutoConfigureMockMvc
public class EmployeeEndpointsTest {

    @Autowired
    MockMvc mvc;

    Employee employee;

    final String TEST_EMPLOYEE_ID = "test-id";
    final String TEST_EMPLOYEE_NAME = "test-name";

    @Autowired EmployeeDao dao;

    /**
     * This setup assumes the test is not running in parallel.
     */
    @BeforeEach
    void setup() {
        employee = new Employee();
        employee.setId(TEST_EMPLOYEE_ID);
        employee.setName(TEST_EMPLOYEE_NAME);
        dao.create(employee);
    }

    @AfterEach
    void cleanup() {
        dao.delete(employee.getId());
    }

    @Test
    @DisplayName("get /employee should return employee")
    public void test() throws Exception {
        mvc.perform(get("/employee?id=test-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("test-id")))
                .andExpect(jsonPath("$.name", is("test-name")));
    }
}