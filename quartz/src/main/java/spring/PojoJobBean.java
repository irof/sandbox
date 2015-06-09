package spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * @author irof
 */
public class PojoJobBean {

    @Autowired
    CountDownLatch latch;

    private static Logger logger = LoggerFactory.getLogger("spring");

    public void hello() {
        latch.countDown();
        logger.info("Hello, POJO Job: {}", this);
    }
}
