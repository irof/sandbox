package thread;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void 別スレッドの結果を取得する() throws Exception {
        // 別スレッドで実行した結果を取得するには、対象スレッドからも呼び出し元からも参照できる領域を使用します。
        // ここでは元スレッドで生成したインスタンスを別スレッドから参照する形で実装します。
        // 他にはデータベースやファイルなどJVMの外に格納する方法などがあります。
        // （staticフィールドも可能ですが、非推奨です。）

        // 値を格納するオブジェクト
        class Storage {
            String value;
        }
        // 別スレッドに参照させるローカル変数
        final Storage storage = new Storage();

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                // 実行したスレッド名を設定させます。
                storage.value = Thread.currentThread().getName();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();

        // Runnableで設定したスレッド名と現在のスレッド名は異なります。
        String name = Thread.currentThread().getName();
        System.out.println("this thread : " + name);
        System.out.println("other thread: " + storage.value);

        assertThat(storage.value).isNotEqualTo(name);
    }
}
