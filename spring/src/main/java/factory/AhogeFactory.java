package factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 指定されたインタフェースのプロキシインスタンスを作る子。
 *
 * @author irof
 */
public class AhogeFactory<T> implements FactoryBean<T> {

    private static final Log LOG = LogFactory.getLog(AhogeFactory.class);

    private final Class<T> target;

    public AhogeFactory(Class<T> target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[]{target},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        LOG.info("invoke method:" + method);
                        // @Ahoge アノテーションに設定されている値をメソッドの戻り値とする。
                        // なのでこの実装だと、戻り値はStringにしかできない。
                        // 実用的には、戻り値型に応じて処理を振り分ける感じになる。
                        return target.getAnnotation(Ahoge.class).value();
                    }
                });
    }

    @Override
    public Class<?> getObjectType() {
        return target;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
