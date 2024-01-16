package jtechlog.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {

    List<Employee> findByNameStartingWith(String namePrefix);

    List<Employee> findByNameStartingWith(String namePrefix, Sort sort);

    Page<Employee> findByNameStartingWith(String namePrefix, Pageable page);

    List<Employee> findByNameStartingWithOrderByNameAsc(String namePrefix);

    @Query("select e from Employee e where length(e.name) = :nameLength")
    List<Employee> findByNameLength(@Param("nameLength") int nameLength);
}
