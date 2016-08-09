package core.v1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Fileの検証。
 *
 * Pathを使った場合の同等の検証も2.0で追加されている。
 * 2.4でファイルと比較する hasSameContentAs が追加されている。
 *
 * @author irof
 * @version 1.6.0
 */
public class FileAssertionTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void test() throws Exception {
        File file = temp.newFile("hoge.csv.txt");
        Files.write(file.toPath(), "abc".getBytes());

        assertThat(file)
                .hasParent(temp.getRoot())
                .hasExtension("txt")
                .hasName("hoge.csv.txt")
                // 1.6.0以前からあるやつ
                .exists()
                .hasContent("abc");
    }
}
