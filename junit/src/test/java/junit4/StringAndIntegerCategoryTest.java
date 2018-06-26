package junit4;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category({String.class, Integer.class})
public class StringAndIntegerCategoryTest {
    @Test
    public void success() { }
}
