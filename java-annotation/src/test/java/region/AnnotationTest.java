package region;

import org.junit.jupiter.api.Test;
import sample.AnnotatedClass;
import sample.annotations.RetentionClassAnnotation;
import sample.annotations.RetentionNoneAnnotation;
import sample.annotations.RetentionRuntimeAnnotation;
import sample.annotations.RetentionSourceAnnotation;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationTest {

    @Test
    void リフレクションで取得できるのはRUNTIMEだけ() {
        Class<AnnotatedClass> clz = AnnotatedClass.class;

        Annotation[] annotations = clz.getAnnotations();

        assertThat(annotations)
                .extracting(Annotation::annotationType)
                .extracting(annotation -> (Class) annotation)
                .contains(RetentionRuntimeAnnotation.class)
                .doesNotContain(RetentionClassAnnotation.class, RetentionNoneAnnotation.class, RetentionSourceAnnotation.class);
    }
}
