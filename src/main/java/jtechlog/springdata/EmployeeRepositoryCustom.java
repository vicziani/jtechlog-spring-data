package jtechlog.springdata;

import java.util.List;

public interface EmployeeRepositoryCustom {

    List<Employee> findByNameStartingWithAsList(String namePrefix);
}
