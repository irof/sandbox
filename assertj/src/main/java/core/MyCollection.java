package core;

import java.util.Arrays;
import java.util.List;

/**
 * ファーストクラスコレクションのイメージ
 *
 * @author irof
 */
public class MyCollection {

    private final List<MyItem> items;

    public MyCollection(MyItem... items) {
        this.items = Arrays.asList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
