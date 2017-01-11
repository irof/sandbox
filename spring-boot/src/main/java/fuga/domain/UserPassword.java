package fuga.domain;

/**
 * @author irof
 */
public class UserPassword {

    private final String value;

    private UserPassword(String value) {
        this.value = value;
    }

    public static UserPassword of(String value) {
        return new UserPassword(value);
    }

    public String getValue() {
        return value;
    }
}
