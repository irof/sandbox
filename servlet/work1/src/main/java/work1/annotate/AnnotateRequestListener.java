package work1.annotate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * @author irof
 */
@WebListener
public class AnnotateRequestListener implements ServletRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(AnnotateRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        logger.info("requestDestroyed : {}", sre);

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("requestInitialized : {}", sre);
    }
}
