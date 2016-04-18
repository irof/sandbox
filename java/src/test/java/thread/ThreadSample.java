package thread;

import org.junit.Test;

/**
 * Java SE 1.4までの {@link Thread} を使用したマルチスレッドプログラミングサンプルです。
 * 別スレッドで "Hello, {スレッド名}." を出力します。
 *
 * 注意: 最近のJavaで {@link Thread} は使いません。
 *
 * @author irof
 */
public class ThreadSample {

    @Test
    public void Threadを継承する() throws Exception {
        // Thread は別スレッドで処理を実行するための、シンプルかつ基本的な手段です。
        // スレッドと処理が一対一となり、処理ごとに新しいスレッドが生成されます。

        // `Thread#run()` メソッドをオーバーライドして実行したい処理を記述します。
        Thread thread = new Thread() {

            @Override
            public void run() {
                System.out.printf("Hello, %s.%n", Thread.currentThread().getName());
            }
        };

        // start() メソッドで実行します。
        // 新しいスレッドはこのタイミングで生成されます。
        thread.start();

        // スレッドが終了するのを待ちます。
        thread.join();
    }

    @Test
    public void Runnableを実装する() throws Exception {
        // Thread を継承することで、別スレッドで処理が実行できましたが
        // しかしながら、前述の方法は推奨されていません。
        // 単に `Thread#run()` メソッドのみをオーバーライドするならば、
        // Runnable インタフェースを使用します。

        // Runnable は別スレッドに実行させたい処理のみを実装するインタフェースです。
        // `Runnable#run()` メソッドを実装し、実行したい処理を記述します。
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.printf("Hello, %s.%n", Thread.currentThread().getName());
            }
        };

        // Runnable インスタンスを処理するには、Threadインスタンスに渡します。
        Thread thread = new Thread(runnable);

        // 実行や終了の待ち方は Thread を継承する場合と同じです。
        thread.start();
        thread.join();

        // 蛇足: Thread クラスも Runnable インタフェースを実装していたりする。
    }
}
