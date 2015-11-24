package xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BeanDefineTest {

    @Autowired
    @Qualifier("hoge")
    Object obj;

    @Test
    public void test() throws Exception {
        assertThat(obj, is(instanceOf(Fuga.class)));
    }

    public static class Hoge implements InitializingBean {
        private static final Logger logger = LoggerFactory.getLogger(Hoge.class);
        @Autowired
        Piyo piyo;

        @Override
        public void afterPropertiesSet() throws Exception {
            logger.error("HOGEHOGEHOGE");
        }
    }

    public static class Fuga implements InitializingBean {
        private static final Logger logger = LoggerFactory.getLogger(Fuga.class);

        @Override
        public void afterPropertiesSet() throws Exception {
            logger.error("FUGAFUGAFUGAFUGA");
        }
    }

    @Component
    static class Piyo implements InitializingBean {
        private static final Logger logger = LoggerFactory.getLogger(Piyo.class);

        @Override
        public void afterPropertiesSet() throws Exception {
            logger.error("PIYOYOYOYO");
        }
    }
}

