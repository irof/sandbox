package thread;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author irof
 */
public class ThreadTest {

    @Test
    public void 直接Threadを使用する() throws Exception {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("別スレッドで実行されたよー: " + Thread.currentThread().getName());
            }
        };
        thread.start();
        thread.join();
    }

    @Test
    public void Runnableを使用する() throws Exception {
        Runnable runnable = () -> System.out.println("別スレッドで実行されたよー: " + Thread.currentThread().getName());
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
    }

    @Test
    public void たまにこんなのを見ます() throws Exception {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("別スレッドで実行されないよー？: " + Thread.currentThread().getName());
            }
        };
        // 何をしたいんだ？？
        thread.run();
        // 開始されてないけど別にエラーにもなりません
        thread.join();
    }

    @Test
    public void Executorsの出番だ() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        try (AutoCloseable shutdown = service::shutdown) {
            Runnable runnable = () -> System.out.println("別スレッドで実行されたよー: " + Thread.currentThread().getName());
            service.submit(runnable);
        }
    }

    @Test
    public void CompletableFutureの登場() throws Exception {
        Runnable runnable = () -> System.out.println("別スレッドで実行されたよー: " + Thread.currentThread().getName());
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(runnable);
        completableFuture.join();
    }
}
