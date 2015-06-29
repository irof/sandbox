package scan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author irof
 */
@Configuration
public class ScannedConfiguration {

    @Bean(name = "scannedStringHoge")
    public String hoge() {
        return "HOGE";
    }
}
