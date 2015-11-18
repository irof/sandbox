package sample;

import org.jberet.se.Main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author irof
 */
public class BatchStarter {

    public static void main(String[] args) throws Exception {
        // 普通に実行する場合はこんな感じにする。
        // JobOperator jobOperator = BatchRuntime.getJobOperator();
        // jobOperator.start("XMLFILENAME", new Properties());

        // weld-seをIDEAで動かすためのおまじない
        Path beansXML = Paths.get("build/classes/main/META-INF/beans.xml");
        if (!beansXML.toFile().exists()) {
            Files.createDirectories(beansXML.getParent());
            Files.createFile(beansXML);
        }

        // けれど、SE環境で前述のように実行するとmainメソッドが即終了するので、
        // 実行完了まで待ち合わせてくれるのを使う。
        /// つまり、mainクラスなんて作ってないで、いきなりこれ呼べばいいね？
        Main.main(new String[]{"myBatches"});
    }
}
