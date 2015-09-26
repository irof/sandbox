package gettingstarted.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author irof
 */
public class GuiceTestRunner extends BlockJUnit4ClassRunner {

    private static Logger logger = Logger.getLogger(GuiceTestRunner.class.getName());
    private Injector injector;

    public GuiceTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Module.class);
            if (methods.isEmpty()) {
                logger.warning("@Moduleメソッドがないです");
                injector = Guice.createInjector();
            } else {
                Method method = methods.get(0).getMethod();
                AbstractModule module = (AbstractModule) method.invoke(null);
                injector = Guice.createInjector(module);
            }
        } catch (Exception e) {
            throw new AssertionError("guice configuration error.", e);
        }

        super.run(notifier);
    }

    @Override
    protected Object createTest() throws Exception {
        Class<?> javaClass = getTestClass().getJavaClass();
        return injector.getInstance(javaClass);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Module {
    }
}
