package gettingstarted.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
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
        injector = Guice.createInjector();
        try {
            List<com.google.inject.Module> modules = Collections.synchronizedList(new ArrayList<>());

            List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Module.class);
            methods.stream().map(this::invokeStatic)
                    .collect(() -> modules, List::add, List::addAll);
            if (modules.isEmpty()) {
                logger.warning("Moduleなしで実行します。");
            }
            injector = Guice.createInjector(modules);
        } catch (Exception e) {
            throw new AssertionError("guice configuration error.", e);
        }

        super.run(notifier);
    }

    com.google.inject.Module invokeStatic(FrameworkMethod method) {
        try {
            return (com.google.inject.Module) method.getMethod().invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("@Module は引数なしのstaticメソッドに指定してくださいませ。");
        }
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
