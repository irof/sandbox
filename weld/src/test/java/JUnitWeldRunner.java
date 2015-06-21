import org.jboss.weld.environment.deployment.WeldDeployment;
import org.jboss.weld.environment.deployment.WeldResourceLoader;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @author irof
 */
public class JUnitWeldRunner extends BlockJUnit4ClassRunner {

    private WeldContainer container;

    public JUnitWeldRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Object createTest() throws Exception {
        // Weldのコンテナからテストクラスのインスタンスを貰ってくる。
        // これでテストクラス自体に @Inject とかができるようになる。
        return container.instance().select(getTestClass().getJavaClass()).get();
    }

    @Override
    protected List<TestRule> classRules() {
        List<TestRule> rules = super.classRules();
        rules.add(new ExternalResource() {

            private Weld weld;

            @Override
            protected void before() throws Throwable {
                // WeldSEと同じ読み方でbeans.xmlをとってくる
                WeldResourceLoader resourceLoader = new WeldResourceLoader();
                URL resource = resourceLoader.getResource(WeldDeployment.BEANS_XML);

                // WeldSEに認識させるために beans.xml をコピーする。
                // Marker.java は src/main/java のデフォルトパッケージに置いておく必要あり。。。
                copyBeansXML(resourceLoader, resource, Marker.class);
                // JUnitWeldRunner は src/test/java のデフォルトパッケージに置いておく必要あり。。。
                copyBeansXML(resourceLoader, resource, JUnitWeldRunner.class);

                weld = new Weld();
                container = weld.initialize();
            }

            private void copyBeansXML(WeldResourceLoader resourceLoader, URL resource, Class<?> clz) throws URISyntaxException, IOException {
                URL mainClassURL = resourceLoader.getResource(clz.getName() + ".class");
                Path defaultPackage = Paths.get(mainClassURL.toURI()).getParent();
                Path toPath = defaultPackage.resolve(WeldDeployment.BEANS_XML);

                Files.createDirectories(toPath.getParent());
                Files.copy(Paths.get(resource.toURI()), toPath, StandardCopyOption.REPLACE_EXISTING);
            }

            @Override
            protected void after() {
                weld.shutdown();
            }
        });
        return rules;
    }
}
