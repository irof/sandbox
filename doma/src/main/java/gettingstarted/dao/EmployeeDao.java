package gettingstarted.dao;

import gettingstarted.AppConfig;
import gettingstarted.entity.Employee;
import org.seasar.doma.*;

import java.util.List;

@Dao(config = AppConfig.class)
@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = javax.inject.Inject.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR_PARAMETER, type = javax.inject.Named.class, elements = "\"config\"")
})
public interface EmployeeDao {

    @Select
    List<Employee> selectAll();

    @Select
    Employee selectById(Integer id);

    @Select
    List<Employee> selectByAge(Integer age);

    @Insert
    int insert(Employee employee);

    @Update
    int update(Employee employee);

    @Delete
    int delete(Employee employee);
}
