package jsr352;

import javax.batch.runtime.BatchRuntime;
import java.util.Properties;

/**
 * JSR352:jBatch をSpring上で動かします。
 * SpringBootは関係無い感じです。
 *
 * @author irof
 */
public class Main {
    public static void main(String[] args) {
        BatchRuntime.getJobOperator().start("jBatchJobs", new Properties());
    }
}
