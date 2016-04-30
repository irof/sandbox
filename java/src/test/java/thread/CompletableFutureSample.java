package thread;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * JSR 337: Java™ SE 8 Release Contents でリリースされた、
 * JEP 155: Concurrency Updates の {@link CompletableFuture} を使用したサンプルです。
 *
 * @author irof
 * @see <a href="https://jcp.org/en/jsr/detail?id=337">JSR 337: Java™ SE 8 Release Contents</a>
 * @see <a href="https://jcp.org/en/jsr/detail?id=166">JSR 166: Concurrency Utilities</a>
 * @see <a href="http://openjdk.java.net/jeps/155">JEP 155: Concurrency Updates</a>
 * @see <a href="http://gee.cs.oswego.edu/dl/concurrency-interest/index.html">Concurrency JSR-166 Interest Site</a>
 */
public class CompletableFutureSample {

    @Test
    public void sample() throws Exception {
        // CompletableFutureは名前の通りFutureの拡張です。
        // Future(1.5)とForkJoinPool(1.7)、Lambda式(1.8)により実現されており、集大成とも言えます。
        // これまでは別スレッドで実行した結果を受けて処理したい場合、呼び出し元スレッドが待ち受ける必要がありました。
        // （ThreadSampleやExecutorSampleを参照してください。）
        // CompletableFutureは後続処理を主にLambda式を使用して登録するプログラミングスタイルになります。

        // CompletableFutureのsupplyAsyncメソッドを使用してインスタンスを生成します。
        CompletableFuture.supplyAsync(() -> Thread.currentThread().getName())
                // 前の処理の後に実行される処理を記述します。
                .thenApplyAsync(name -> "Hello, " + name + ".")
                .thenAcceptAsync(System.out::println);

        // CompletableFutureを処理するExecutorは引数で指定でき、
        // 省略した場合は `ForkJoinPool.commonPool()` が使用されます。
        // なので、前述の処理の終了は次の形で待てます。
        ForkJoinPool.commonPool().awaitTermination(3, TimeUnit.SECONDS);

        // CompletableFutureを使用する場合、戻り値を呼び出し元スレッドで待ち受けなくてもよくなります。
        // もちろんCompletableFutureもFutureなので、普通にgetメソッドなどで結果を待って取得できますが、
        // 結果に対する処理もCompletableFutureで済ませるのが基本でしょう。
        // もし戻り値が必要なら、おそらくCompletableFutureを使用する必要がありません。
    }
}
