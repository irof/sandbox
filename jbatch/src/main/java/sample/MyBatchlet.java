package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author irof
 */
@Named
public class MyBatchlet extends AbstractBatchlet {

    private static final Logger logger = LoggerFactory.getLogger(MyBatchlet.class);

    @Inject
    @BatchProperty
    private String message;

    @Override
    public String process() throws Exception {
        // やりたい処理を書く
        // ...
        logger.info("ばっちれっとです！ メッセージ:{}", message);

        return "exitStatus";
    }
}
