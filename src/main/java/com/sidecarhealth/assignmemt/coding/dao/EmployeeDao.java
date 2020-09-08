package com.sidecarhealth.assignmemt.coding.dao;

import com.sidecarhealth.assignmemt.coding.dao.util.redis.InvalidateEmployeeCount;
import com.sidecarhealth.assignmemt.coding.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sidecarhealth.assignmemt.coding.dao.redis.constant.Constant.EMPLOYEE_COUNT_KEY;

@Transactional
@Repository
public class EmployeeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    StringRedisTemplate redisTemplate;

    /*
       Technical decision - using the AOP to invalidate the employee count.
       The good thing about it is that we can reuse the code easily and make our function more readable.
       The decision is to demonstrate the potential benefit we can get from AOP.
       However, for this particular case, if I were developing on the team, I would just use a private function to reduce complexity.
       It is because when I try to make utility function(or commonly used function) stateless, when possible. In this case, the function is not as bad
       since it is idempotent, but still not stateless. Hence the benefit of using AOP must be significant for me to use it this way here.
     */
    @InvalidateEmployeeCount
    public void create(Employee e) {
        // TODO: SQLIntegrityConstraintViolationException
        entityManager.persist(e);
    }

    public Employee read(String id) {
        return entityManager.find(Employee.class, id);
    }

    public boolean update(Employee updatedEmployee) {
        Employee employee = entityManager.find(Employee.class, updatedEmployee.getId());
        if(employee == null) {
            return false;
        }
        employee.setName(updatedEmployee.getName());
        return true;
    }

    @InvalidateEmployeeCount
    public void delete(String id) {
        Employee e = entityManager.find(Employee.class, id);
        if(e != null) {
            entityManager.remove(e);
        }
    }

    public int getEmployeeCount() {
        String count = this.redisTemplate.opsForValue().get(EMPLOYEE_COUNT_KEY);
        if(count == null) {
            count = String.valueOf(entityManager.createQuery("SELECT count(e) FROM Employee e", Long.class).getSingleResult());
            this.redisTemplate.opsForValue().set(EMPLOYEE_COUNT_KEY, count);
            this.redisTemplate.expire(EMPLOYEE_COUNT_KEY, 2, TimeUnit.MINUTES);
        }
        return Integer.parseInt(count);
    }

    public List<Employee> getAllEmployees() {
        return entityManager.createQuery("SELECT a FROM Employee a", Employee.class).getResultList();
    }
}
