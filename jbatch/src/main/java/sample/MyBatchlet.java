package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

/**
 * @author irof
 */
@Named
public class MyBatchlet implements javax.batch.api.Batchlet {

    private static final Logger logger = LoggerFactory.getLogger(MyBatchlet.class);

    @Override
    public String process() throws Exception {
        // やりたい処理を書く
        // ...
        logger.info("処理が実行された");

        return "exitStatus";
    }

    @Override
    public void stop() throws Exception {
    }
}
