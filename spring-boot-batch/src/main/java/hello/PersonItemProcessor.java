package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;

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
