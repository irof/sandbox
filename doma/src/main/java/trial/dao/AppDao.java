package trial.dao;

import org.seasar.doma.*;
import trial.AppConfig;

@Dao(config = AppConfig.class)
public interface AppDao {

    @Script
    void create();

    @Script
    void drop();
}
