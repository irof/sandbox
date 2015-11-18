package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

/**
 * @author irof
 */
@Named
public class MyProcessor implements ItemProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MyProcessor.class);

    @Override
    public Object processItem(Object item) throws Exception {
        logger.info("processItem: {}" , item);
        return null;
    }
}
