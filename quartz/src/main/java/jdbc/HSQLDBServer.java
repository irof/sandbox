package jdbc;

import org.flywaydb.core.Flyway;
import org.hsqldb.server.Server;

import java.io.PrintStream;
import java.util.concurrent.Executors;

/**
 * HSQLDBを起動して、Quartzに必要なテーブルをFlyway使って作る子。
 *
 * @author irof
 */
public class HSQLDBServer {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out) {
            @Override
            public void write(byte[] buf, int off, int len) {
                if (new String(buf).contains("Startup sequence completed")) {
                    // 起動したらマイグレーションさせる。
                    // Server#start で起動して待ち受けるほうがいいんだろうけど、
                    // 起動の仕方がよくわからなかったので Server#main で起動させることにした。
                    // その場合、起動完了に差し込む方法がこれくらいしか思いつかない。
                    // なお、Serverと同じスレッドから接続しようとしても、接続の待ち受けとぶつかってアウト。
                    // （普通に ServerSocket#accept をハンドルしているので。）
                    Executors.newSingleThreadExecutor().submit(() -> {
                        Flyway flyway = new Flyway();
                        flyway.setDataSource("jdbc:hsqldb:hsql://localhost/", "SA", null);
                        flyway.setLocations("jdbc/hsqldb");
                        flyway.migrate();
                    });
                }

                // 出力はそのまま変えない。
                super.write(buf, off, len);
            }
        });

        Server.main(new String[]{"-database", ".hsqldb/jdbcjobstore"});
    }
}
