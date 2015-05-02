package pracswing.v4_12;

import org.junit.Test;
import org.junit.validator.ValidateWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.Assume.assumeThat;

/**
 * {@link org.junit.validator.AnnotationValidator} を無理矢理使ってみる。
 *
 * @author irof
 */
@MyAnnotation
public class PracAnnotationValidator {

    // コメント外したらAnnotationValidatorで設定した例外が出てくる
    // Object hoge;

    @Test
    public void hoge() throws Exception {
    }
}

@Retention(RetentionPolicy.RUNTIME)
@ValidateWith(NoFieldValidator.class)
@Target(ElementType.TYPE)
@interface MyAnnotation {
}

