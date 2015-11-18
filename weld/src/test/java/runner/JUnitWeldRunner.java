package runner;

import org.jboss.weld.environment.deployment.WeldDeployment;
import org.jboss.weld.environment.deployment.WeldResourceLoader;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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

    private final Class<?> klass;
    private WeldContainer container;

    public JUnitWeldRunner(Class<?> klass) throws InitializationError {
        super(klass);
        this.klass = klass;
    }

    @Override
    protected Object createTest() throws Exception {
        // Weldのコンテナからテストクラスのインスタンスを貰ってくる。
        // これでテストクラス自体に @Inject とかができるようになる。
        return container.instance().select(klass).get();
    }

    @Override
    protected List<TestRule> classRules() {
        List<TestRule> rules = super.classRules();
        rules.add(new ExternalResource() {

            private Weld weld;

            @Override
            protected void before() throws Throwable {
                // WeldSEに認識させるために beans.xml をコピーする。
                // テストクラス(おそらく classes/test/java)
                copyBeansXML(klass);
                // テストクラスにインジェクションされるクラス(おそらく classes/main/java)
                for (Field field : klass.getDeclaredFields()) {
                    if (!field.isAnnotationPresent(Inject.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    copyBeansXML(field.getType());
                    break;
                }

                weld = new Weld();
                container = weld.initialize();
            }

            private void copyBeansXML(Class<?> clz) throws URISyntaxException, IOException {
                WeldResourceLoader resourceLoader = new WeldResourceLoader();
                // "/META-INF/beans.xml" を指定するためにリソースのURIからパッケージを消す。
                // こんなコトしなくていい気がする。
                String name = clz.getName().replaceAll("\\.", File.separator);
                Path relativePath = Paths.get(name);
                Path absolutePath = Paths.get(resourceLoader.getResource(name + ".class").toURI());
                Path defaultPackage = Paths.get("/").resolve(absolutePath.subpath(0,
                        absolutePath.getNameCount() - relativePath.getNameCount()));
                Path toPath = defaultPackage.resolve(WeldDeployment.BEANS_XML);

                // WeldSEと同じ読み方でbeans.xmlをとってきてコピーする。
                // resources/main/META-INF/beans.xml がとれてくるはず。
                // 空ファイルでいいんだから、コピーしなくていい気がする。というかなんか書いてたらコピーしちゃダメな気がする。
                URL resource = resourceLoader.getResource(WeldDeployment.BEANS_XML);
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
