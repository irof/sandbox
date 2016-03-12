package core.my;

import core.MyCollection;
import org.assertj.core.description.Description;

public class MyDescription extends Description {

    private final MyCollection actual;

    public MyDescription(MyCollection actual) {
        this.actual = actual;
    }

    @Override
    public String value() {
        // ここで頑張れば何かしらが出る
        return actual.toString();
    }
}