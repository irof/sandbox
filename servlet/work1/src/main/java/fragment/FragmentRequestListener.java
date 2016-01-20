package fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * @author irof
 */
public class FragmentRequestListener implements ServletRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(FragmentRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        logger.info("requestDestroyed : {}", sre);

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("requestInitialized : {}", sre);
    }
}
