package collection;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class IterateWithIndex {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        ListIterator<Integer> iterator = list.listIterator();

        while (iterator.hasNext()) {
            System.out.printf("%d: %s%n", iterator.nextIndex(), iterator.next());
        }
    }
}
