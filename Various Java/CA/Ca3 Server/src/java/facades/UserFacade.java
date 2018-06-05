package facades;

import deploy.DeploymentConfiguration;
import entity.CurrencyRates;
import entity.Role;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import security.Register;

public class UserFacade {

    //test persistence unit navnet til den originale igen
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    EntityManager em = emf.createEntityManager();

    
    public UserFacade() {

        //Test Users added on startup
//        User user = new User("user", "test");
//        user.AddRole(new Role("User", user.getId()));
//        
//        User admin = new User("admin", "test");
//        admin.AddRole(new Role("Admin", admin.getId()));
//
//        User both = new User("user_admin", "test");
//        both.AddRole(new Role("User", both.getId()));
//        both.AddRole(new Role("Admin", both.getId()));
//
//        em.getTransaction().begin();
//        em.persist(user);
//        em.persist(admin);
//        em.persist(both);
//        em.getTransaction().commit();
//        em.close();
    }
    private EntityManager entityManager = null;

    public UserFacade(EntityManagerFactory factory) {
        this.entityManager = factory.createEntityManager();
    }

    
    
    public EntityManager getEntityManager() {
        return em;
    }

    public User createUser(User user) {
        //Create new users and add them to the DB
        EntityManager em = emf.createEntityManager();

        user.setPassword(user.getPassword());
        user.AddRole(new Role("User", user.getId()));

        if (user == null) {
            System.out.println("User exists");
        }
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return user;
    }

    public User getUserByUserId(String id) {
        Long newId = new Long(id);
        Query query;
        query = em.createQuery("SELECT u FROM User u WHERE u.id = :id");
        query.setParameter("id", newId);

        return (User) query.getSingleResult();
    }

    public User getUserByUserName(String userName) {
        Query query;
        query = em.createQuery("SELECT u FROM User u WHERE u.userName = :userName");
        query.setParameter("userName", userName);

        return (User) query.getSingleResult();
    }

    public List<String> authenticateUser(String userName, String password) {
        User user = getUserByUserName(userName);
        return user != null && user.getPassword().equals(password) ? user.getRoles() : null;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList();
        Query query;

        query = em.createQuery("SELECT u FROM User u");
        userList = query.getResultList();

        return userList;
    }

    public User deleteUser(long id) {
        User removedUser;
        em.getTransaction().begin();
        removedUser = em.find(User.class, id);
        if (removedUser == null) {
            throw new NotFoundException();
        }
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();

        return removedUser;
    }

    public List<CurrencyRates> getTodaysCurrencies() {
        List<CurrencyRates> rates = new ArrayList();
        Date wrongDato = new Date();
        java.sql.Date dato = new java.sql.Date(wrongDato.getTime());

        Query query;
        query = em.createQuery("SELECT cr FROM CurrencyRates cr WHERE cr.dato = :dato");
        query.setParameter("dato", dato);

        return rates = query.getResultList();
    }

    public CurrencyRates getSingleCurrency(String shortDesc, Date dato) {
        Query query;
        query = em.createQuery("SELECT cr FROM CurrencyRates cr WHERE cr.shortDesc = :shortDesc AND cr.dato = :dato");
        query.setParameter("shortDesc", shortDesc);
        query.setParameter("dato", dato);

        CurrencyRates currency;
        return (CurrencyRates) query.getSingleResult();
    }

    public String calculateCurrency(String amount, String fromCurrency, String toCurrency) {
        Date wrongDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(wrongDate.getTime());

        CurrencyRates from;
        CurrencyRates to;

//        Double stepA;
//        Double stepB;
        Double result = 0D;

        if (fromCurrency.equals("DKK") && toCurrency.equals("DKK")) {
            return amount;
        } else if (fromCurrency.equals("DKK")) {
            from = new CurrencyRates(sqlDate, "DKK", "Denmark", 100D);
            to = getSingleCurrency(toCurrency, sqlDate);
        } else if (toCurrency.equals("DKK")) {
            from = getSingleCurrency(fromCurrency, sqlDate);
            to = new CurrencyRates(sqlDate, "DKK", "Denmark", 100D);
        } else {
            from = getSingleCurrency(fromCurrency, sqlDate);
            to = getSingleCurrency(toCurrency, sqlDate);
        }

        System.out.println("Før udregning: " + to.getCurrency());
        Double stepA = (Double.parseDouble(amount)) * from.getCurrency();
        System.out.println("Første udregning: " + stepA);
        Double stepB = stepA / to.getCurrency();
        System.out.println("Anden udregning: " + stepB);

        return stepB.toString();
    }

}
