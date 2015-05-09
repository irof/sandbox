import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pp.Fuga;
import pp.Hoge;
import pp.SomeInterface;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PropertyPlaceholderTest {

    @Autowired
    String hoge;

    @Autowired
    @Qualifier("instance1")
    SomeInterface instance1;

    @Autowired
    @Qualifier("instance2")
    SomeInterface instance2;

    @Test
    public void コンストラクタ引数に使用する() throws Exception {
        assertThat(hoge, is("HOGE"));
    }

    @Test
    public void beanの型に使用する() throws Exception {
        assertThat(instance1, instanceOf(Fuga.class));
    }

    @Test
    public void beanの型に使用する_デフォルト値() throws Exception {
        assertThat(instance2, instanceOf(Hoge.class));
    }
}
