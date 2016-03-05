package core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
public class StandardTest {

    @Test
    public void 文字列の検証() throws Exception {
        String str = "hogedriven";

        // 落ちるのがあればそこで止まるので、ゆるい条件から順にやるのがよい
        assertThat(str)
                .isNotEmpty()
                .hasLineCount(1)
                .hasSameSizeAs("1234driven")
                .hasSize(10)
                .containsIgnoringCase("HOGE")
                .startsWith("hoge")
                .endsWith("en")
                .contains("dri")
                .doesNotContain("fuga")
                .isSubstringOf("the hogedriven method")
                .isEqualToIgnoringWhitespace("   hogedriven   ")
                .containsPattern("^hog...iven$")
                .matches("^hogedr...n$")
                .containsOnlyOnce("g") // "e"とかだと2回出るので落ちる
                .containsSequence("g", "e", "v", "e") // 2つ目の"e"を抜くと"v"の前に"e"があるとかで落ちる
        ;
    }

    @Test
    public void 空文字列の検証() throws Exception {
        String str = "";

        assertThat(str).isEmpty();
        assertThat(str).isNullOrEmpty();
        assertThat(str).contains("");
    }

    @Test
    public void nullの検証() throws Exception {
        String str = null;

        // isEmpty/isNotEmptyはnullを許容しない
        // assertThat(str).isEmpty();

        assertThat(str).isNull();
        assertThat(str).isNullOrEmpty();
    }
}
