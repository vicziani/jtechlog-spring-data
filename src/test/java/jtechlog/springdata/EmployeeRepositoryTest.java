package jtechlog.springdata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static jtechlog.springdata.HasNameMatcher.hasName;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class EmployeeRepositoryTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void init() {
           employeeRepository.deleteAll();
    }

    @Test
    public void test_findAll_shouldReturnAll() {
        employeeRepository.save(new Employee("John Doe"));

        assertThat(employeeRepository.findAll(), contains(hasName(equalTo("John Doe"))));
    }

    @Test
    public void test_findByNameStartingWith_shouldReturnMatching() {
        employeeRepository.save(new Employee("Jane Doe"));
        employeeRepository.save(new Employee("John Doe"));

        assertThat(employeeRepository.findByNameStartingWith("J"), containsInAnyOrder(hasName(equalTo("Jane Doe")), hasName(equalTo("John Doe"))));
        assertThat(employeeRepository.findByNameStartingWith("Jane"), contains(hasName(equalTo("Jane Doe"))));
    }

    @Test
    public void test_findByNameLength_shouldReturnMatching() {
        employeeRepository.save(new Employee("J"));
        employeeRepository.save(new Employee("Ja"));
        employeeRepository.save(new Employee("Jan"));
        employeeRepository.save(new Employee("Jane"));

        assertThat(employeeRepository.findByNameLength(3), contains(hasName(equalTo("Jan"))));
    }

    @Test
    public void test_findByNameStartingWithAsList_shouldReturnMatching() {
        employeeRepository.save(new Employee("Jane Doe"));
        employeeRepository.save(new Employee("John Doe"));

        assertThat(employeeRepository.findByNameStartingWithAsList("J"), containsInAnyOrder(hasName(equalTo("Jane Doe")), hasName(equalTo("John Doe"))));
        assertThat(employeeRepository.findByNameStartingWithAsList("Jane"), contains(hasName(equalTo("Jane Doe"))));
    }

    @Test
    public void test_findByNameStartingWithOrderByNameAsc_shouldReturnInRightOrder() {
        employeeRepository.save(new Employee("John Doe"));
        employeeRepository.save(new Employee("Jane Doe"));

        assertThat(employeeRepository.findByNameStartingWithOrderByNameAsc("J"), contains(hasName(equalTo("Jane Doe")), hasName(equalTo("John Doe"))));
    }

    @Test
    public void test_findByNameStartingWith_shouldReturnInRightOrder() {
        employeeRepository.save(new Employee("John Doe"));
        employeeRepository.save(new Employee("Jane Doe"));

        assertThat(employeeRepository.findByNameStartingWith("J", new Sort(new Sort.Order(Sort.Direction.ASC, "name"))),
                contains(hasName(equalTo("Jane Doe")), hasName(equalTo("John Doe"))));
    }

    @Test
    public void test_findByNameStartingWith_shouldReturnOnlyPage() {
        for (int i = 10; i < 30; i++) {
            employeeRepository.save(new Employee("John Doe " + i));
        }

        Page page = employeeRepository.findByNameStartingWith("J", new PageRequest(3, 3, new Sort(new Sort.Order(Sort.Direction.ASC, "name"))));
        assertThat((List<Employee>)page.getContent(),
                contains(hasName(equalTo("John Doe 19")), hasName(equalTo("John Doe 20")), hasName(equalTo("John Doe 21"))));
    }
}
