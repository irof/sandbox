package gettingstarted.entity;

import org.seasar.doma.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "EMPLOYEE_SEQ")
    public  Integer id;

    public String name;

    public Integer age;

    @Version
    public Integer version;
}
