package factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FactoryTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    Hoge hoge;

    @Test
    public void とりあえずFactoryBeanに作らせる() throws Exception {
        assertThat(hoge.hoge(), is("HOGE"));
    }

    @Configuration
    static class Config {

        @Bean
        public FactoryBean<Hoge> hoge() {
            return new AhogeFactory<Hoge>(Hoge.class);
        }
    }
}
