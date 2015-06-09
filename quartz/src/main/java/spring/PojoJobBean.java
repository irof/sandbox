package spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author irof
 */
public class PojoJobBean {

    private static Logger logger = LoggerFactory.getLogger("spring");

    public void hello() {
        logger.info("Hello, POJO Job: {}", this);
    }
}
