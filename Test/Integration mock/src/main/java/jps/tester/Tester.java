/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jps.tester;

import entity.ProjectUser;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author sofus
 */
public class Tester {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        Persistence.generateSchema("PU", null);
        
        ProjectUser pu = new ProjectUser();
        pu.setUserName("sofus");
        pu.setEmail("sal@ca.dk");
        pu.setCreated(new Date());
        em.getTransaction().begin();
        em.persist(pu);
        em.getTransaction().commit();
        
    }
    
}
