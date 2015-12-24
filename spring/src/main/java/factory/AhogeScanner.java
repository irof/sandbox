package factory;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 指定パッケージをスキャンして {@link Ahoge} アノテーションの付与されたインタフェースをBean登録し、
 * {@link AhogeFactory} を使用してインスタンス化するようにSpringにお願いする子。
 *
 * @author irof
 */
public class AhogeScanner extends ClassPathBeanDefinitionScanner {

    public AhogeScanner(BeanDefinitionRegistry registry) {
        // デフォルトのFilter( @Component とか読むやつ)は使わない
        super(registry, false);

        // @Ahoge をスキャン対象にする
        addIncludeFilter(new AnnotationTypeFilter(Ahoge.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // 元は具象クラスのみ。インタフェースを対象にしたいので上書きする
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            // インタフェースのままだとBean生成できないので定義を上書きする
            // コンストラクタ引数にインタフェース(ex.Fuga)を与えるようにして、
            // BeanClassを FactoryBean の AhogeFactory にすげ替える。
            // これで Spring が AhogeFactory を使ってインスタンスを作ってくれるようになる。
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.setBeanClass(AhogeFactory.class);
        }

        return beanDefinitionHolders;
    }
}
