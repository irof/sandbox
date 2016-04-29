package thread;

import junit.framework.TestCase;

/**
 * Java SE 1.4までの {@link Thread} を使用したマルチスレッドプログラミングサンプルです。
 *
 * 注意: 最近のJavaで {@link Thread} は直接使用するものではなく、
 * Java標準ライブラリやフレームワークの内部で使用するものになっています。
 *
 * このサンプルはJava SE 1.4の文法で記述しているつもりです。
 *
 * @author irof
 */
public class ThreadSample extends TestCase {

    public void test_Threadを継承する() throws Exception {
        // Thread は別スレッドで処理を実行するための、シンプルかつ基本的な手段です。
        // スレッドと処理が一対一となり、処理ごとに新しいスレッドが生成されます。

        // `Thread#run()` メソッドをオーバーライドして実行したい処理を記述します。
        Thread thread = new Thread() {

            public void run() {
                System.out.println("Hello, " + Thread.currentThread().getName() + ".");
            }
        };

        // startメソッドを呼び出すと、runメソッドが新規スレッドで実行されます。
        thread.start();

        // スレッドが終了するのを待ちます。
        thread.join();
    }

    public void test_Runnableを実装する() throws Exception {
        // Thread を継承することで、別スレッドで処理が実行できましたが
        // しかしながら、前述の方法は推奨されていません。
        // 単に `Thread#run()` メソッドのみをオーバーライドするならば、
        // Runnable インタフェースを使用します。

        // Runnable は別スレッドに実行させたい処理のみを実装するインタフェースです。
        // `Runnable#run()` メソッドを実装し、実行したい処理を記述します。
        Runnable runnable = new Runnable() {

            public void run() {
                System.out.println("Hello, " + Thread.currentThread().getName() + ".");
            }
        };

        // Runnable インスタンスを処理するには、Threadインスタンスに渡します。
        Thread thread = new Thread(runnable);

        // 実行や終了の待ち方は Thread を継承する場合と同じです。
        thread.start();
        thread.join();

        // 蛇足: Thread クラスも Runnable インタフェースを実装していたりする。
    }

    public void test_別スレッドの結果を取得する() throws Exception {
        // 別スレッドで実行した結果を取得するには、対象スレッドからも呼び出し元からも参照できる領域を使用します。
        // ここでは元スレッドで生成したインスタンスを別スレッドから参照する形で実装します。
        // 他にはデータベースやファイルなどJVMの外に格納する方法などがあります。
        // （staticフィールドでも実現できますが、安全に使用するのは難しいのでお勧めできません。）

        // 値を格納するオブジェクト
        class Storage {
            String value;
        }
        // 別スレッドに参照させるローカル変数
        final Storage storage = new Storage();

        Runnable runnable = new Runnable() {

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
        assertFalse(name.equals(storage.value));

        System.out.println("this thread : " + name);
        System.out.println("other thread: " + storage.value);
    }
}
