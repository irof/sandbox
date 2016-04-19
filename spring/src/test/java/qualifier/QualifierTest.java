package qualifier;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link org.springframework.beans.factory.annotation.Qualifier} の素振り。
 *
 * @author irof
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class QualifierTest {

    @Autowired
    @Qualifier("piyo")
    String str1;

    @Test
    public void Qualifierの一致() throws Exception {
        assertThat(str1).isEqualTo("HOGE");
    }

    @Autowired
    @Qualifier("fuga")
    String str2;

    @Test
    public void QualifierでBean名を指定() throws Exception {
        assertThat(str2).isEqualTo("FUGA");
    }

    @Autowired
    @MyQualifier
    String str3;

    @Autowired
    @MyQualifier
    Integer int1;

    @Autowired
    @TypeQualifier(Long.class)
    String typeQualified;

    @Test
    public void カスタムQualifier() throws Exception {
        SoftAssertions soft = new SoftAssertions();
        // 複数に同じQualifierを付けたやつ
        soft.assertThat(str3).isEqualTo("BAR");
        soft.assertThat(int1).isEqualTo(123);

        // Type
        soft.assertThat(typeQualified).isEqualTo("LONG-QUALIFIED");

        soft.assertAll();
    }

/*---------------------------------------
    // どれとも一致しないのでBeanが特定できない
    @Autowired
    String xxxx;
    // 「Qualifierで指定されている名前と同じフィールド名」は解決できない
    @Autowired
    String hogera;
---------------------------------------*/

    @Configuration
    static class Config {

        @Bean
        @Qualifier("piyo")
        public String hoge() {
            return "HOGE";
        }

        @Bean
        @Qualifier("hogera")
        public String fuga() {
            return "FUGA";
        }

        @Bean
        @MyQualifier
        public String bar() {
            return "BAR";
        }

        @Bean
        @MyQualifier
        public Integer baz() {
            return 123;
        }

        @Bean
        @TypeQualifier(Integer.class)
        public String t1() {
            return "INT-QUALIFIED";
        }

        @Bean
        @TypeQualifier(Long.class)
        public String t2() {
            return "LONG-QUALIFIED";
        }
    }

    // カスタムQualifier
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier("foo")
    public @interface MyQualifier {
    }

    // 別の属性を持つカスタムQualifier
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    public @interface TypeQualifier {
        Class<?> value();
    }
}

