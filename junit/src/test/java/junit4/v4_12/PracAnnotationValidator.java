package junit4.v4_12;

import org.junit.Test;
import org.junit.runners.model.TestClass;
import org.junit.validator.AnnotationValidator;
import org.junit.validator.ValidateWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

/**
 * {@link org.junit.validator.AnnotationValidator} を無理矢理使ってみる。
 *
 * @author irof
 */
@PracAnnotationValidator.MyAnnotation
public class PracAnnotationValidator {

    // コメント外したらAnnotationValidatorで設定した例外が出てくる
    // Object hoge;

    @Test
    public void hoge() throws Exception {
    }

    /**
     * テストクラスがFieldを持っていけないと言う謎のValidator
     */
    public static class NoFieldValidator extends AnnotationValidator {

        @Override
        public List<Exception> validateAnnotatedClass(TestClass testClass) {
            if (testClass.getJavaClass().getDeclaredFields().length > 0) {
                return Collections.singletonList(new Exception("フィールドがあるよ？"));
            }
            return super.validateAnnotatedClass(testClass);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ValidateWith(NoFieldValidator.class)
    @Target(ElementType.TYPE)
    @interface MyAnnotation {
    }
}
