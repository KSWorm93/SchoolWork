package facades;

import deploy.DeploymentConfiguration;
import entity.ApiLinks;
import entity.Role;
import entity.User;
import exception.UnknownServerException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import security.PasswordHash;

public class UserFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public UserFacade() {

    }

    public User createUser(User user) {
        EntityManager em = emf.createEntityManager();

        try {

            Role role = em.find(Role.class, "User");
            User userNew = new User(user.getUserName(), PasswordHash.createHash(user.getPassword()));
            userNew.AddRole(role);
            try {
                em.getTransaction().begin();
                em.merge(userNew);
                em.getTransaction().commit();
            } catch (Exception e) {
                throw new UnknownServerException(e.getMessage());
            } finally {
                em.close();
            }

        } catch (Exception ex) {
            System.out.println("Exception caught while registrering user: " + ex.getMessage());
        }

        return user;
    }

    public User getUserByUserId(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, userName);
            if (user == null) {
                return null;
            }
            try {
                boolean authenticated = PasswordHash.validatePassword(password, user.getPassword());
                return authenticated ? user.getRolesAsStrings() : null;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        } finally {
            em.close();
        }

    }

    public User editUserInfo(User user) {
        EntityManager em = emf.createEntityManager();
        User oldUser = null;
        try {
            oldUser = getUserByUserId(user.getUserName());
            if (oldUser.getUserName().equals(user.getUserName())) {
                try {
                    em.getTransaction().begin();
                    oldUser.setPassword(PasswordHash.createHash(user.getPassword()));
                    em.merge(oldUser);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    em.close();
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return oldUser;
    }
}
