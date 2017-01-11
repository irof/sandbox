package fuga.domain;

import java.time.LocalDateTime;

/**
 * @author irof
 */
public class Account {

    private final UserName name;
    private final UserPassword password;
    private final MailAddress mailAddress;
    private final CreatedDate createdDate;

    private Account(UserName name, UserPassword password, MailAddress mailAddress, CreatedDate createdDate) {
        this.name = name;
        this.password = password;
        this.mailAddress = mailAddress;
        this.createdDate = createdDate;
    }

    public UserName getName() {
        return name;
    }

    public UserPassword getPassword() {
        return password;
    }

    public MailAddress getMailAddress() {
        return mailAddress;
    }

    public CreatedDate getCreatedDate() {
        return createdDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String password;
        private String mailAddress;
        private LocalDateTime createdDate;

        public Builder username(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder mailAddress(String mailAddress) {
            this.mailAddress = mailAddress;
            return this;
        }
        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Account build() {
            return new Account(
                    UserName.of(name),
                    UserPassword.of(password),
                    MailAddress.of(mailAddress),
                    CreatedDate.of(createdDate));
        }
    }
}
