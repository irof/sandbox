package gettingstarted.dao;

import gettingstarted.AppConfig;
import gettingstarted.entity.Employee;
import org.seasar.doma.*;

import java.util.List;

@Dao(config = AppConfig.class)
public interface EmployeeDao {

    @Select
    List<Employee> selectAll();

    @Select
    Employee selectById(Integer id);

    @Insert
    int insert(Employee employee);

    @Update
    int update(Employee employee);

    @Delete
    int delete(Employee employee);
}
