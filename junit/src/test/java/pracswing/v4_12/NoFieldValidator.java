package pracswing.v4_12;

import org.junit.runners.model.TestClass;
import org.junit.validator.AnnotationValidator;

import java.util.Collections;
import java.util.List;

/**
 * テストクラスがFieldを持っていけないと言う謎のValidator
 *
 * @see PracAnnotationValidator
 */
public class NoFieldValidator extends AnnotationValidator {

    @Override
    public List<Exception> validateAnnotatedClass(TestClass testClass) {
        if (testClass.getJavaClass().getDeclaredFields().length > 0) {
            return Collections.singletonList(new Exception("フィールドがあるよ？"));
        }
        return super.validateAnnotatedClass(testClass);
    }
}