package fuga.domain;

import java.time.LocalDateTime;

/**
 * @author irof
 */
public class CreatedDate {

    private final LocalDateTime value;

    private CreatedDate(LocalDateTime value) {
        this.value = value;
    }

    public static CreatedDate of(LocalDateTime value) {
        return new CreatedDate(value);
    }

    public LocalDateTime getValue() {
        return value;
    }

}
