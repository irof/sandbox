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
public interface AppDao {

    @Script
    void create();

    @Script
    void drop();
}
