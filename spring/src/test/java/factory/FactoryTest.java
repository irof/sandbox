package factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
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
    public void FactoryBeanで登録したやつ() throws Exception {
        Hoge hoge = context.getBean(Hoge.class);
        assertThat(hoge.hoge(), is("HOGE"));
    }

    @Test
    public void 普通のコンポーネントスキャンで登録したやつ() throws Exception {
        ComponentBean componentBean = context.getBean(ComponentBean.class);
        assertThat(componentBean.method(), is("COMPONENTBEAN"));
    }

    @Configuration
    static class Config {

        @Bean
        public FactoryBean<Hoge> hoge() {
            return new AhogeFactory<Hoge>(Hoge.class);
        }

        @Bean
        public BeanDefinitionRegistryPostProcessor componentScan() {
            return new BeanDefinitionRegistryPostProcessor() {

                @Override
                public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                }

                @Override
                public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
                    scanner.scan("factory");
                }
            };
        }
    }
}
