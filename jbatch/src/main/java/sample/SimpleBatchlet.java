package sample;

import org.jberet.se.Main;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author irof
 */
@Named
public class SimpleBatchlet implements javax.batch.api.Batchlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SimpleBatchlet.class);

    @Override
    public String process() throws Exception {
        // やりたい処理を書く
        // ...
        logger.info("処理が実行された");

        return "exitStatus";
    }

    @Override
    public void stop() throws Exception {
    }

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
        Main.main(new String[]{"job-" + SimpleBatchlet.class.getSimpleName()});
    }
}
