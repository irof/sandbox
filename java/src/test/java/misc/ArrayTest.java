package misc;

import org.junit.Test;

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
    public void 複数の配列を一つの配列にまとめる_stream() throws Exception {
        String[] actual =
                Stream.of(arr1, arr2)
                        .flatMap(Arrays::stream)
                        .toArray(String[]::new);
        assertThat(actual, is(arrayContaining("a", "b", "c", "d")));
    }
}
