package gettingstarted.dao;

import gettingstarted.AppConfig;
import gettingstarted.entity.Employee;
import org.seasar.doma.*;

import java.util.List;

@Dao(config = AppConfig.class)
public interface AppDao {

    @Script
    void create();

    @Script
    void drop();
}
