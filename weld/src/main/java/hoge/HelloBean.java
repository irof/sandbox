package hoge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * @author irof
 */
public class HelloBean {

    private static Logger logger = LoggerFactory.getLogger(HelloBean.class);

    public String hello() {
        logger.info("hello: {}", this);
        return "HELLO, WELD!";
    }

    @PostConstruct
    public void init() {
        logger.info("postConstruct: {}", this);
    }
}
