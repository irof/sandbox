package trial.entity;

import org.seasar.doma.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "COMPANY_SEQ")
    public  Integer id;

    public String name;
}
