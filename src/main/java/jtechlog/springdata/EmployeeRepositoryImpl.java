package jtechlog.springdata;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityManager;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Employee> findByNameStartingWithAsList(String namePrefix) {
        return entityManager.createQuery("select e from Employee e where e.name like :namePrefix", Employee.class)
                .setParameter("namePrefix", namePrefix + "%").getResultList();
    }
}
