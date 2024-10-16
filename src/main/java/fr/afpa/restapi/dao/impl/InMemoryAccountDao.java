package fr.afpa.restapi.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.afpa.restapi.dao.AccountDao;
import fr.afpa.restapi.model.Account;

/**
 * Une implémentation de {@link AccountDao} basée sur un
 * {@link java.util.HashMap}
 *
 * TODO annoter cette classe de façon à en faire un "bean". Quelle est
 * l'annotation à utiliser dans ce cas de figure ? Pour vous aider, lisez
 * l'article suivant ->
 * https://www.axopen.com/blog/2019/02/java-spring-les-beans/
 */
@Component
public class InMemoryAccountDao implements AccountDao {

    /**
     * Table de hachage permettant de stocker les objets de {@link Account}
     */
    private Map<Long, Account> accountMap = new HashMap<>();
    private long idSequence = 1L;

    public void createAccountTest() {

        Account account = new Account(3L, "Ben", "Toto", "toto@gmail.com", LocalDate.of(2000, 2, 11),
                LocalDate.now().atStartOfDay(), BigDecimal.valueOf(12200.0));

        accountMap.put(account.getId(), account);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accountMap.values());
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    @Override
    public Account save(Account account) {
        if (account.getId() == null) {
            account.setId(idSequence++);
        }
        accountMap.put(account.getId(), account);
        return account;
    }

    @Override
    public void delete(Account account) {
        accountMap.remove(account.getId());
    }

    public void clear() {
        accountMap.clear();
        idSequence = 1L;
    }

    @Override
    public Account update(Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
