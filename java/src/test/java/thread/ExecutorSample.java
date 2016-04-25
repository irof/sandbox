package thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * JSR 176: J2SE 5.0 (Tiger) Release Contents でリリースされた、
 * JSR 166: Concurrency Utilities のサンプルです。
 *
 * JSR 166には {@link java.util.concurrent.atomic.AtomicInteger} などのアトミック変数、
 * {@link java.util.concurrent.Semaphore} などの制御クラス、
 * {@link java.util.concurrent.ConcurrentHashMap} などの並行コレクション、
 * そして {@link java.util.concurrent.Executor} などからなるExecutorフレームワークが含まれます。
 *
 * これらのクラスはJSR 166で追加された {@link java.util.concurrent} パッケージに含まれます。
 *
 * このサンプルでは最後のExecutorフレームワークを紹介します。
 * 「Executorフレームワーク」は公式の記述が見つけられないので、おそらく通称です。
 *
 * @author irof
 * @see <a href="https://jcp.org/en/jsr/detail?id=166">JSR 166: Concurrency Utilities</a>
 */
public class ExecutorSample {

    @Test
    public void Threadの置き換えとして使用する() throws Exception {

        // 実行する処理の記述はThread時代と同じくRunnableを使用しますが、
        // 同時に追加されたCallableも使用できます。
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.printf("Hello, %s.%n", Thread.currentThread().getName());
            }
        };

        // ExecutorインスタンスはExecutorsクラスのファクトリメソッドで生成します。
        // ExecutorServiceはExecutorを継承したインタフェースで、通常はこちらを使用します。
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            executor.execute(runnable);
        } finally {
            // ExecutorServiceを終了させます。
            // ここでは自分でExecutorServiceのインスタンスを生成しているためここで終了させますが、
            // 通常はフレームワークの管理下になるはずなので、終了はフレームワークの仕事だと思います。
            executor.shutdown();
        }

        // ExecutorServiceの終了を待ちます。
        // `ExecutorService#shutdown()` はシャットダウンを開始するだけで、シャットダウンの終了を待ちません。
        // 実行中のタスクなどが完了してから終了するため、シャットダウンには長い時間がかかる可能性があります。
        // そのため次のような待ち受けるメソッドが別途存在します。
        executor.awaitTermination(3, TimeUnit.SECONDS);
    }
}
