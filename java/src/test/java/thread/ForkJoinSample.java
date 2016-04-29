package thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JSR 336: Java™ SE 7 Release Contents でリリースされた、
 * JSR 166: Concurrency Utilities のアップデート（JSR 166y）のうち、
 * Fork/Join Frameworkのサンプルです。
 *
 * JSR 336のConcurrency and collections updates (jsr166y) で追加された代表的なクラスとして、
 * {@link java.util.concurrent} パッケージの以下が挙げられています。
 *
 * <ul>
 * <li>{@link java.util.concurrent.ForkJoinPool}</li>
 * <li>{@link java.util.concurrent.Phaser}</li>
 * <li>{@link java.util.concurrent.TransferQueue}</li>
 * <li>{@link java.util.concurrent.ConcurrentLinkedDeque}</li>
 * <li>{@link java.util.concurrent.ThreadLocalRandom}</li>
 * </ul>
 *
 * Fork/Join Frameworkを単純に使用する際に目に触れるのはForkJoinPoolです。
 * Fork/Join Frameworkの基本的な使い方は「ForkJoinPoolにForkJoinTaskを渡す」です。
 * forkやjoin、そして処理の本体はForkJoinTaskを拡張して実装します。
 *
 * このサンプルはJava SE 7の文法で記述しているつもりです。
 *
 * @author irof
 * @see <a href="https://jcp.org/en/jsr/detail?id=336">JSR 336: Java™ SE 7 Release Contents</a>
 * @see <a href="https://jcp.org/en/jsr/detail?id=166">JSR 166: Concurrency Utilities</a>
 */
public class ForkJoinSample {

    @Test
    public void サンプル() throws Exception {
        // ForkJoinTaskを用意します。
        // 通常、ForkJoinTaskを直接拡張せず、RecursiveActionかRecursiveTaskを拡張します。
        // 今回は値を返すのでRecursiveTaskを使用します。
        class MyTask extends RecursiveTask<List<String>> {

            private final List<?> list;

            private MyTask(List<?> list) {
                this.list = list;
            }

            @Override
            protected List<String> compute() {
                // タスクをFork/Joinするか、そのまま実行するかを判断します。
                if (list.size() < 2) {
                    // 十分に小さければそのまま実行します。
                    return computeDirectly();
                }

                int halfSize = list.size() / 2;

                // forkすると非同期で実行されます。
                MyTask taskA = new MyTask(list.subList(0, halfSize));
                taskA.fork();

                // computeは同期で実行されます。
                MyTask taskB = new MyTask(list.subList(halfSize, list.size()));
                List<String> resultB = taskB.compute();

                // forkした結果をjoinで取得します。
                List<String> resultA = taskA.join();

                // 結果をマージします。
                ArrayList<String> result = new ArrayList<>();
                result.addAll(resultA);
                result.addAll(resultB);
                return result;
            }

            private List<String> computeDirectly() {
                // 実行されたスレッド名を返します。
                String name = Thread.currentThread().getName();
                return Collections.singletonList(name);
            }
        }

        // 5件の任意のリスト
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        MyTask task = new MyTask(list);

        // ForkJoinPoolのinvokeにForkJoinTaskを渡すと、
        // ForkJoinPoolの持つスレッドで実行されます。
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<String> result = forkJoinPool.invoke(task);

        // ForkJoinTaskが実行されたすべてのスレッド名と現在のスレッド名は異なります。
        String name = Thread.currentThread().getName();
        assertThat(result).doesNotContain(name);

        System.out.println("this thread : " + name);
        System.out.println("other threads: " + result);
    }
}
