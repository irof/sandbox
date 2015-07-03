package sandbox.misc;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author irof
 */
public class ImagePage extends WebPage {

    public ImagePage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Image("hoge1", new ResourceReference("hoge") {
            @Override
            public IResource getResource() {
                try {
                    URL resource = this.getClass().getClassLoader().getResource("hoge_black.png");
                    Path path = Paths.get(resource.toURI());
                    byte[] bytes = Files.readAllBytes(path);
                    return new ByteArrayResource("application/jpeg", bytes);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }
}
