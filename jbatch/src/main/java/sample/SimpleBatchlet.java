package sample;

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
        // julのログを出したいが依存は追加したくないし-Dで書くのも嫌なの
        System.setProperty("java.util.logging.config.file",
                ClassLoader.getSystemResource("logging.properties").getPath());

        JobOperator operator = BatchRuntime.getJobOperator();

        // XMLを指定してスタートする
        long jobId = operator.start("job-" + SimpleBatchlet.class.getSimpleName(), new Properties());

        // jobId使って何かできる
        // 一旦止めて3秒後に再開するとか
        operator.stop(jobId);
        TimeUnit.SECONDS.sleep(3);
        operator.restart(jobId, new Properties());

        // バッチが完了してもプロセス終了までしばらく待たされる
    }
}
