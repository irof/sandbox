package fuga.domain;

/**
 * @author irof
 */
public class MailAddress {
    private final String value;

    private MailAddress(String value) {
        this.value = value;
    }

    public static MailAddress of(String value) {
        return new MailAddress(value);
    }

    public String getValue() {
        return value;
    }
}
