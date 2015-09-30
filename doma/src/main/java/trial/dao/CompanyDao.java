package trial.dao;

import org.seasar.doma.*;
import trial.AppConfig;
import trial.entity.Company;

import java.util.List;

@Dao
@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = javax.inject.Inject.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR_PARAMETER, type = javax.inject.Named.class, elements = "\"config\"")
})
public interface CompanyDao {

    @Select
    List<Company> selectAll();
}
