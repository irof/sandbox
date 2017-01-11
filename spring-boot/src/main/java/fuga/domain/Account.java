package fuga.domain;

/**
 * @author irof
 */
public class Account {

    private final UserName name;
    private final UserPassword password;

    private Account(UserName name, UserPassword password) {
        this.name = name;
        this.password = password;
    }

    public UserName getName() {
        return name;
    }

    public UserPassword getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String password;

        public Builder username(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Account build() {
            return new Account(UserName.of(name), UserPassword.of(password));
        }
    }
}
