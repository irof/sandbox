package collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class ArrayTest {

    String[] arr1 = {"a", "b"};
    String[] arr2 = {"c", "d"};

    @Test
    public void 複数の配列を一つの配列にまとめる_arrays() throws Exception {
        String[] actual = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, actual, arr1.length, arr2.length);

        assertThat(actual, is(arrayContaining("a", "b", "c", "d")));
    }

    @Test
    public void 複数の配列を一つの配列にまとめる_stream_flatMap() throws Exception {
        String[] actual =
                Stream.of(arr1, arr2)
                        .flatMap(Arrays::stream)
                        .toArray(String[]::new);
        assertThat(actual, is(arrayContaining("a", "b", "c", "d")));
    }

    @Test
    public void 複数の配列を一つの配列にまとめる_stream_concat() throws Exception {
        String[] actual =
                Stream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
                        .toArray(String[]::new);
        assertThat(actual, is(arrayContaining("a", "b", "c", "d")));
    }

    @Test
    public void 複数の配列を一つの配列にまとめる_list() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(arr1));
        list.addAll(Arrays.asList(arr2));
        String[] actual = list.toArray(new String[list.size()]);

        assertThat(actual, is(arrayContaining("a", "b", "c", "d")));
    }

}
