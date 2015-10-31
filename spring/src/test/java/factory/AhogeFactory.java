package factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;

/**
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
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LOG.info("invoke method:" + method);
                return target.getAnnotation(Ahoge.class).value();
            }
        };
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[]{target},
                handler);
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
