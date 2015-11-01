package factory;

import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author irof
 */
@Component
public class ComponentBean {

    String method() {
        return this.getClass().getSimpleName().toUpperCase(Locale.ROOT);
    }
}
