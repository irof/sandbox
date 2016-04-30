package thread;

import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * JSR 337: Java™ SE 8 Release Contents でリリースされた、
 * JSR 335: Lambda ExpressionsのParallelStreamを使用したサンプルです。
 *
 * ParallelStreamにはForkJoinPoolが使用されます。
 * ForkJoinWorkerThreadの外で実行されたParallelStreamは、
 * 実行されたスレッドと ForkJoinPool.commonPool のスレッドで実行されます。
 *
 * @author irof
 * @see <a href="https://jcp.org/en/jsr/detail?id=337">JSR 337: Java™ SE 8 Release Contents</a>
 * @see <a href="https://jcp.org/en/jsr/detail?id=335">JSR 335: Lambda Expressions</a>
 */
public class ParallelStreamSample {

    @Test
    public void sample() throws Exception {
        List<String> collected = IntStream.rangeClosed(1, 5)
                // parallelメソッドを使用すると、各要素が並列に処理されます。
                .parallel()
                .mapToObj(i -> Thread.currentThread().getName())
                .collect(toList());

        // mainスレッドと、ForkJoinPool.commonPoolのスレッドが含まれていることを検証
        assertThat(collected)
                .contains("main")
                .filteredOn(str -> str.startsWith("ForkJoinPool.commonPool-worker-")).isNotEmpty();
    }

    @Test
    public void sampleInForkJoinWorkerThread() throws Exception {
        RecursiveTask<List<String>> task = new RecursiveTask<List<String>>() {

            @Override
            protected List<String> compute() {
                // ForkJoinPoolのThreadの中でparallelに実行します。
                return IntStream.rangeClosed(1, 5)
                        .parallel()
                        .mapToObj(i -> Thread.currentThread().getName())
                        .collect(toList());
            }
        };

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<String> collected = forkJoinPool.invoke(task);

        // ForkJoinPoolの中で実行されているので、すべてForkJoinPoolのスレッド名になっているはずです。
        assertThat(collected)
                .are(new Condition<>(str -> str.startsWith("ForkJoinPool-"), "start with 'ForkJoinPool-'"));

        forkJoinPool.shutdown();
    }
}
