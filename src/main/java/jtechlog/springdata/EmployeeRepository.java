package jtechlog.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends CrudRepository<Employee, Long>, EmployeeRepositoryCustom {

    Iterable<Employee> findByNameStartingWith(String namePrefix);

    Iterable<Employee> findByNameStartingWith(String namePrefix, Sort sort);

    Page<Employee> findByNameStartingWith(String namePrefix, Pageable page);

    Iterable<Employee> findByNameStartingWithOrderByNameAsc(String namePrefix);

    @Query("select e from Employee e where length(e.name) = :nameLength")
    Iterable<Employee> findByNameLength(@Param("nameLength") int nameLength);
}
