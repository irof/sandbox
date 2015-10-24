package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.Locale;

/**
 * バッチの本体処理とも言えるもの。
 * バッチのシンプルなStepは、 {@link ItemReader#read()} の戻り値が {@link ItemProcessor#process(Object)} に渡され、
 * さらにその戻り値が {@link ItemWriter#write(List)} に渡される形で構成される。
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(Person person) throws Exception {
        String firstName = person.getFirstName().toUpperCase(Locale.ROOT);
        String lastName = person.getLastName().toUpperCase(Locale.ROOT);

        Person transformed = new Person(firstName, lastName);

        logger.info("Converting ({}) into ({})", person, transformed);

        return transformed;
    }
}
