import hoge.HelloBean;
import org.jboss.weld.environment.deployment.WeldDeployment;
import org.jboss.weld.environment.deployment.WeldResourceLoader;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class HelloTest {

    @BeforeClass
    public static void setup() throws Exception {
        // WeldSEと同じ読み方でbeans.xmlをとってくる
        WeldResourceLoader resourceLoader = new WeldResourceLoader();
        URL resource = resourceLoader.getResource(WeldDeployment.BEANS_XML);
        // WeldSEがクラスを認識するために beans.xml が存在するべき場所
        URL mainClassURL = resourceLoader.getResource(Marker.class.getName() + ".class");
        Path defaultPackage = Paths.get(mainClassURL.toURI()).getParent();
        Path toPath = defaultPackage.resolve(WeldDeployment.BEANS_XML);

        Files.createDirectories(toPath.getParent());
        Files.copy(Paths.get(resource.toURI()), toPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void とりあえずこんな感じで動くよと() throws Exception {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        HelloBean bean = container.instance().select(HelloBean.class).get();

        String hello = bean.hello();
        assertThat(hello, is("HELLO, WELD!"));

        weld.shutdown();
    }
}
