package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import java.util.List;

/**
 * @author irof
 */
@Named
public class MyWriter extends AbstractItemWriter {

    private static final Logger logger = LoggerFactory.getLogger(MyWriter.class);

    @Override
    public void writeItems(List<Object> items) throws Exception {
        logger.info("writeItems:{}", items);
    }
}
