import hoge.HelloBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import runner.JUnitWeldRunner;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
@RunWith(JUnitWeldRunner.class)
public class HelloTest {

    @Inject
    HelloBean bean;

    @Test
    public void とりあえずこんな感じで動くよと() throws Exception {
        String hello = bean.hello();
        assertThat(hello, is("HELLO, WELD!"));
    }
}
