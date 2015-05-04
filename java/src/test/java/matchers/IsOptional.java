package matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;

/**
 * @author irof
 */
public class IsOptional<T> extends TypeSafeMatcher<Optional<T>> {

    private Optional<T> value;

    public IsOptional(T value) {
        this.value = Optional.ofNullable(value);
    }

    @Factory
    public static <T> IsOptional<T> isValue(T value) {
        return new IsOptional<>(value);
    }

    @Factory
    public static <T> IsOptional<T> isEmpty() {
        return new IsOptional<>(null);
    }

    @Override
    protected boolean matchesSafely(Optional<T> item) {
        return value.equals(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(value);
    }
}
