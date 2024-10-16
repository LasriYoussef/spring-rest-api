package fr.afpa.restapi.web.controller;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.restapi.dao.AccountDao;
import fr.afpa.restapi.dao.impl.InMemoryAccountDao;
import fr.afpa.restapi.model.Account;

/**
 * TODO ajouter la/les annotations nécessaires pour faire de
 * "AccountRestController" un contrôleur de REST API
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {
    
    private final AccountDao accountDao;
    
    /**
     * TODO implémenter un constructeur
     */
    /** TODO injecter {@link AccountDao} en dépendance par injection via
    * constructeur Plus d'informations ->
    * https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
    */
    @Autowired
    public AccountRestController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    
    /**
     * TODO implémenter une méthode qui traite les requêtes GET et qui renvoie
     * une liste de comptes
     */
    @GetMapping
    List<Account> getAll() {
    ((InMemoryAccountDao)accountDao).createAccountTest();
        List<Account> accounts = accountDao.findAll();
        return accounts;
    }
    /**
     * TODO implémenter une méthode qui traite les requêtes GET avec un
     * identifiant "variable de chemin" et qui retourne les informations du
     * compte associé Plus d'informations sur les variables de chemin ->
     * https://www.baeldung.com/spring-pathvariable
     */
   @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountDao.findById(id);
        
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * TODO implémenter une méthode qui traite les requêtes POST Cette méthode
     * doit recevoir les informations d'un compte en tant que "request body",
     * elle doit sauvegarder le compte en mémoire et retourner ses informations
     * (en json) Tutoriel intéressant ->
     * https://stackabuse.com/get-http-post-body-in-spring/ Le serveur devrai
     * retourner un code http de succès (201 Created)
     *
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountDao.save(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
    /**
     * TODO implémenter une méthode qui traite les requêtes PUT
     */
       @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        Optional<Account> accountData = accountDao.findById(id);

        if (accountData.isPresent()) {
            Account account = accountData.get();
            account.setLastName(accountDetails.getLastName());
            account.setBalance(accountDetails.getBalance());
            Account updatedAccount = accountDao.save(account);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * TODO implémenter une méthode qui traite les requêtes DELETE L'identifiant
     * du compte devra être passé en "variable de chemin" (ou "path variable")
     * Dans le cas d'un suppression effectuée avec succès, le serveur doit
     * retourner un status http 204 (No content)
     */
       @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long id) {
        Optional<Account> account = accountDao.findById(id);

        if (account.isPresent()) {
            accountDao.delete(account.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
