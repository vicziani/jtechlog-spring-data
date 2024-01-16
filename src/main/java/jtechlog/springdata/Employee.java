package jtechlog.springdata;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "employees")
@NoArgsConstructor
@ToString
public class Employee {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="emp_name")
    private String name;

    public Employee(String name) {
        this.name = name;
    }

}
