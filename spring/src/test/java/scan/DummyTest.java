package scan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DummyTest.Configure.class)
public class DummyTest {

    @Autowired
    @Qualifier("stringProvidedByDummyTestConfigure")
    String str;

    @Test
    public void test() throws Exception {
        assert str.equals("STR");
    }

    // このコメントアウトを外すと ComponentScanTest の方が落ちる
    //@Configuration
    static class Configure {
        @Bean
        String stringProvidedByDummyTestConfigure() {
            return "STR";
        }
    }
}
