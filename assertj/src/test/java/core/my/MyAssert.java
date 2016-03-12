package core.my;

import core.MyCollection;
import org.assertj.core.api.AbstractAssert;

/**
 * @author irof
 */
public class MyAssert extends AbstractAssert<MyAssert, MyCollection> {
    MyAssert(MyCollection actual) {
        super(actual, MyAssert.class);
    }

    public MyAssert isEmpty() {
        isNotNull();

        if (!actual.isEmpty()) {
            failWithMessage(actual.toString());
        }

        return this;
    }

    public static MyAssert that(MyCollection actual) {
        return new MyAssert(actual);
    }
}
