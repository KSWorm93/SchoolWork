package facades;

import entity.Role;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.PasswordHash;

public class SchemaBuilder {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

//        UserFacade facade = new UserFacade();
//        EntityManager em = facade.getEntityManager();
//
//        //Test Users added on startup
//        User user = new User("user", PasswordHash.createHash("test"));
//        user.AddRole(new Role("User", user.getId()));
//
//        User admin = new User("admin", PasswordHash.createHash("test"));
//        admin.AddRole(new Role("Admin", admin.getId()));
//
//        User both = new User("user_admin", PasswordHash.createHash("test"));
//        both.AddRole(new Role("User", both.getId()));
//        both.AddRole(new Role("Admin", both.getId()));
//
//        em.getTransaction().begin();
//        em.persist(user);
//        em.persist(admin);
//        em.persist(both);
//        em.getTransaction().commit();
    }
}
