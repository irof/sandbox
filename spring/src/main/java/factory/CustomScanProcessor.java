package factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * {@link @ComponentScan} が使われる場合と結果的におなじようなことをする。
 *
 * 実際は {@link org.springframework.context.annotation.ConfigurationClassPostProcessor},
 * {@link org.springframework.context.annotation.ConfigurationClassParser},
 * {@link org.springframework.context.annotation.ComponentScanAnnotationParser}
 * を経由して {@link ClassPathBeanDefinitionScanner} が使われている。
 *
 * @author irof
 */
public class CustomScanProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.scan("factory.bean");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // なにもしない
    }
}
