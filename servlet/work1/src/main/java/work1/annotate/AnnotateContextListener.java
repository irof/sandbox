package work1.annotate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author irof
 */
@WebListener
public class AnnotateContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AnnotateContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("contextInitialized: {}", sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("contextDestroyed: {}", sce);
    }
}
