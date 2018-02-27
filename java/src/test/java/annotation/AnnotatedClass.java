package annotation;

import java.util.Arrays;

@RetentionClassAnnotation
@RetentionNoneAnnotation
@RetentionRuntimeAnnotation
@RetentionSourceAnnotation
public class AnnotatedClass {

    public static void main(String[] args) {
        // RUNTIME以外はリフレクションで取れない
        Arrays.stream(AnnotatedClass.class.getAnnotations())
                .forEach(System.out::println);
    }
}
