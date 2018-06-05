/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.InfoResponse.FlightSearch;
import entity.ReservationRequest.Reservation;
import entity.User;
import exception.UnknownServerException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author kasper
 */
public class ReservationFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public ReservationFacade() {

    }

    public List<Reservation> getUserReservation(String username) {
        EntityManager em = emf.createEntityManager();

        List<Reservation> userList = new ArrayList();
        Query query;

        query = em.createQuery("SELECT r FROM Reservation r WHERE r.ReserveeName = :username");
        query.setParameter("username", username);
        userList = query.getResultList();

//        System.out.println(query);
        //em.close();
        return userList;
    }
    
    public List<Reservation> getAllReservation() {
        EntityManager em = emf.createEntityManager();

        List<Reservation> userList = new ArrayList();
        Query query;

        query = em.createQuery("SELECT r FROM Reservation r");
        userList = query.getResultList();

//        System.out.println(query);
        //em.close();
        return userList;
    }

    public Reservation addReservation(Reservation reservation) throws UnknownServerException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new UnknownServerException(e.getMessage());
        } finally {
            em.close();
        }
        
        return reservation;
    }
    
    public FlightSearch addSearch(FlightSearch flightSearch) throws UnknownServerException {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(flightSearch);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new UnknownServerException(e.getMessage());
        } finally {
            em.close();
        }
        
        return flightSearch;
    }
    
    public List<FlightSearch> getTopReservation(int topAmount) {
        EntityManager em = emf.createEntityManager();
        
        List<FlightSearch> topList = new ArrayList();
        Query query;
        
        
        // Order BY *something* - possibly fix??
        // Skal groupe DESTINATION og hente dem, der er flest af.
        query = em.createQuery("SELECT f FROM FlightSearch f WHERE f.destination IS NOT NULL GROUP BY f.destination");
        query.setMaxResults(2);
//        query.setMaxResults(topAmount);
        topList = query.getResultList();
        
        for(FlightSearch str : topList) {
            System.out.println(str.getDestination());
        }
        
        return topList;
    }
}
