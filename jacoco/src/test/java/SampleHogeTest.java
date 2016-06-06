import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
public class SampleHogeTest {

    @Test
    public void iが整数の場合() throws Exception {
        SampleHoge hoge = new SampleHoge();
        String actual = hoge.invoke(1);

        assertThat(actual).isEqualTo("A");
    }

    @Test
    public void lambdaを実行するよ() throws Exception {
        new SampleHoge().lambda();
    }
}