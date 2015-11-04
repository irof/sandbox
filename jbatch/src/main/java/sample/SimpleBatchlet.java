package sample;

import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author irof
 */
public class SimpleBatchlet implements javax.batch.api.Batchlet {

    @Override
    public String process() throws Exception {
        // やりたい処理を書く
        // ...
        Logger.getLogger("sample").severe("処理が実行された");

        return "exitStatus";
    }

    @Override
    public void stop() throws Exception {
    }

    public static void main(String[] args) throws Exception {
        // julの出力ログレベルを変えた上で
        System.setProperty("java.util.logging.config.file",
                ClassLoader.getSystemResource("logging.properties").getPath());
        // julからslf4jに流し込む
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        JobOperator operator = BatchRuntime.getJobOperator();

        // XMLを指定してスタートする
        long jobId = operator.start("job-" + SimpleBatchlet.class.getSimpleName(), new Properties());

        // jobId使って何かできる
        // 一旦止めて3秒後に再開するとか
        operator.stop(jobId);
        TimeUnit.SECONDS.sleep(3);
        operator.restart(jobId, new Properties());

        // job再開しても動く前に終了しちゃうのでしばらくmainを止めておく
        TimeUnit.SECONDS.sleep(5);
    }
}
