package collection;

import org.junit.Test;

import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void test() throws Exception {
        Stream.of("abc", "aaa1", "aaa2", "aaa3", "aaa4", "aaa5", "aaa6")
                //.parallel()
                .map(str -> {
                    System.out.println("toUpperCase:" + str);
                    return str.toUpperCase();
                })
                .filter(str -> {
                    System.out.println("filter:" + str);
                    return str.length() == 4;
                })
                .findAny()
                .ifPresent(System.out::println);
    }
}
