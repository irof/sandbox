package trial.dao;

import org.seasar.doma.*;
import trial.AppConfig;
import trial.entity.Company;
import trial.entity.CompanyName;

import java.util.List;
import java.util.Optional;

@Dao
@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = javax.inject.Inject.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR_PARAMETER, type = javax.inject.Named.class, elements = "\"config\"")
})
public interface CompanyDao {

    @Select
    List<Company> selectAll();

    @Select
    Optional<Company> findByName(CompanyName name);
}
