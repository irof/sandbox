package sandbox;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.StringValue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author irof
 */
public class ImageResourceReference extends ResourceReference {

    public ImageResourceReference() {
        super("imageResource");
    }

    @Override
    public IResource getResource() {
        return new DynamicImageResource() {
            @Override
            protected byte[] getImageData(Attributes attributes) {
                try {
                    PageParameters parameters = attributes.getParameters();
                    StringValue key = parameters.get("key");
                    URL resource = this.getClass().getClassLoader().getResource("img/" + key.toString());
                    Path path = Paths.get(resource.toURI());
                    return Files.readAllBytes(path);
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
