package core;

import core.my.MyAssert;
import core.my.MyDescription;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 独自のオブジェクトを検証した時に失敗メッセージがわかりづらい件
 *
 * @author irof
 */
@Ignore("テスト失敗時の振る舞いのテストなのです")
public class MyCollectionTest {

    MyItem hogekun = new MyItem(new MyId(1), new MyName("hogekun"));
    MyItem fugachan = new MyItem(new MyId(2), new MyName("fugachan"));
    MyCollection myCollection = new MyCollection(hogekun, fugachan);

    @Test
    public void Descriptionでがんばる() throws Exception {
        assertThat(myCollection.isEmpty())
                .as(new MyDescription(myCollection))
                .isTrue();
    }

    @Test
    public void Assertでがんばる() throws Exception {
        MyAssert.that(myCollection).isEmpty();
    }
}
