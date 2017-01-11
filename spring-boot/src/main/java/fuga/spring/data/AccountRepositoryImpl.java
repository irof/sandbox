package fuga.spring.data;

import fuga.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * クラス名は EnableJpaRepositories.repositoryImplementationPostfix 依存。
 *
 * @author irof
 * @see org.springframework.data.jpa.repository.config.EnableJpaRepositories
 */
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void createNewAccount(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setName(account.getName().getValue());
        entity.setPassword(passwordEncoder.encode(account.getPassword().getValue()));
        entity.setMailAddress(account.getMailAddress().getValue());
        entityManager.persist(entity);
    }
}
