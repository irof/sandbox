package trial.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import trial.AppConfig;
import trial.entity.Company;

import java.util.List;

@Dao(config = AppConfig.class)
public interface CompanyDao {

    @Select
    List<Company> selectAll();
}
