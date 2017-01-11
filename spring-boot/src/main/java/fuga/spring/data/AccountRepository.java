package fuga.spring.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author irof
 */
public interface AccountRepository extends JpaRepository<AccountEntity, String>, AccountRepositoryCustom {

    AccountEntity findByName(String username);
}
