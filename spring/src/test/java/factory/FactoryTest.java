package factory;

import factory.bean.ComponentBean;
import factory.bean.Fuga;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
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

    @Test
    public void 普通のコンポーネントスキャンで登録したやつ() throws Exception {
        ComponentBean componentBean = context.getBean(ComponentBean.class);
        assertThat(componentBean.method(), is("COMPONENTBEAN"));
    }

    @Test
    public void インタフェースのProxyを登録したやつ() throws Exception {
        Fuga fuga = context.getBean(Fuga.class);
        assertThat(fuga.fuga(), is("FUgeRA"));
    }

    @Configuration
    static class Config {

        @Bean
        public BeanDefinitionRegistryPostProcessor componentScan() {
            return new CustomScanProcessor();
        }

        @Bean
        public BeanDefinitionRegistryPostProcessor ahogeScan() {
            return new AhogeScanProcessor();
        }
    }
}
