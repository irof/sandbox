package factory.scan;

import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author irof
 */
@Component
public class ComponentBean {

    public String method() {
        return this.getClass().getSimpleName().toUpperCase(Locale.ROOT);
    }
}
