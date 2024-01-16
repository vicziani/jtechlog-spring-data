package jtechlog.springdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeRepositoryTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void init() {
        employeeRepository.deleteAll();
    }

    @Test
    void findAll_shouldReturnAll() {
        employeeRepository.save(new Employee("John Doe"));

        assertThat(employeeRepository.findAll())
                .extracting(Employee::getName)
                .containsExactly("John Doe");
    }

    @Test
    void findByNameStartingWith_shouldReturnMatching() {
        employeeRepository.save(new Employee("Jane Doe"));
        employeeRepository.save(new Employee("John Doe"));


        assertThat(employeeRepository.findByNameStartingWith("J"))
                .extracting(Employee::getName)
                .containsExactlyInAnyOrder("Jane Doe", "John Doe");


        assertThat(employeeRepository.findByNameStartingWith("Jane"))
                .extracting(Employee::getName)
                .containsExactly("Jane Doe");
    }

    @Test
    void findByNameLength_shouldReturnMatching() {
        employeeRepository.save(new Employee("J"));
        employeeRepository.save(new Employee("Ja"));
        employeeRepository.save(new Employee("Jan"));
        employeeRepository.save(new Employee("Jane"));

        assertThat(employeeRepository.findByNameLength(3))
                .extracting(Employee::getName)
                .containsExactly("Jan");
    }

    @Test
    void findByNameStartingWithAsList_shouldReturnMatching() {
        employeeRepository.save(new Employee("Jane Doe"));
        employeeRepository.save(new Employee("John Doe"));


        assertThat(employeeRepository.findByNameStartingWithAsList("J"))
                .extracting(Employee::getName)
                .containsExactlyInAnyOrder("Jane Doe", "John Doe");

        assertThat(employeeRepository.findByNameStartingWithAsList("Jane"))
                .extracting(Employee::getName)
                .containsExactly("Jane Doe");
    }

    @Test
    void findByNameStartingWithOrderByNameAsc_shouldReturnInRightOrder() {
        employeeRepository.save(new Employee("John Doe"));
        employeeRepository.save(new Employee("Jane Doe"));

        assertThat(employeeRepository.findByNameStartingWithOrderByNameAsc("J"))
                .extracting(Employee::getName)
                .containsExactly("Jane Doe", "John Doe");
    }

    @Test
    void findByNameStartingWith_shouldReturnInRightOrder() {
        employeeRepository.save(new Employee("John Doe"));
        employeeRepository.save(new Employee("Jane Doe"));

        assertThat(employeeRepository.findByNameStartingWith("J", Sort.by(new Sort.Order(Sort.Direction.ASC, "name"))))
                .extracting(Employee::getName)
                .containsExactly("Jane Doe", "John Doe");
    }

    @Test
    void findByNameStartingWith_shouldReturnOnlyPage() {
        for (int i = 10; i < 30; i++) {
            employeeRepository.save(new Employee("John Doe " + i));
        }

        Page<Employee> page = employeeRepository.findByNameStartingWith("J",
                PageRequest.of(3, 3,
                        Sort.by(new Sort.Order(Sort.Direction.ASC, "name"))));
        assertThat(page.getContent())
                .extracting(Employee::getName)
                .containsExactly("John Doe 19", "John Doe 20", "John Doe 21");
    }
}
