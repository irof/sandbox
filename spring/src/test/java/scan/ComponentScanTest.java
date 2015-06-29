package scan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ComponentScanTest.Config.class)
public class ComponentScanTest {

    @Autowired
    Bar bar;

    @Autowired
    String str;

    /** component-scan で登録されるほう */
    @Autowired
    Foo foo;

    /** ComponentScanTest.Config で登録したほう */
    @Autowired
    Foo foofoo;

    @Test
    public void scannedComponent() throws Exception {
        assert str.equals("HOGE");
    }

    @Test
    public void bar() throws Exception {
        assert bar != null;
    }

    @Test
    public void foo() throws Exception {
        // 別のインスタンスだよー
        assert foo != foofoo;
    }

    // このクラス自体に @Configuration を設定すれば @ContextConfiguration に
    // 引数を指定しなくても動くのだけれど、 @Configuration をつけると @ComponentScan の対象にもなる。
    // ということで component-scan 対象になるパッケージにテストクラスを置くならば、
    // @Configuration を使用した設定の簡略化は避けて、 @ContextConfiguration で指定する。
    @ComponentScan(value = "scan")
    static class Config {
        @Bean
        Foo foofoo() {
            return new Foo();
        }
    }
}
