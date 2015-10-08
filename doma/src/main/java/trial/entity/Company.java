package trial.entity;

import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * 会社。
 * エンティティクラスのサンプルです。
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "COMPANY_SEQ")
    public Integer id;

    public CompanyName name;

    public PhoneNumber phoneNumber;
}
