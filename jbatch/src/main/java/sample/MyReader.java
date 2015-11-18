package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author irof
 */
@Named
public class MyReader extends AbstractItemReader {

    private static final Logger logger = LoggerFactory.getLogger(MyReader.class);

    LinkedList stack = new LinkedList(Collections.singleton("hoge"));

    @Override
    public Object readItem() throws Exception {
        logger.info("readItem{}", stack);
        if (stack.isEmpty()) {
            return null;
        }
        return stack.pop();
    }
}
