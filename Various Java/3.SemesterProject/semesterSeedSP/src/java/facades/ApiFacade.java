package facades;

import deploy.DeploymentConfiguration;
import entity.ApiLinks;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ApiFacade {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    public ApiFacade() {
    }
    
    public void insertUrlApi(List<String> urlList) {
      EntityManager em = emf.createEntityManager();
      ApiLinks api;
      
      try {
          for (String url : urlList) {
            
          api = new ApiLinks();
          api.setUrl(url);
          
          em.getTransaction().begin();
          em.persist(api);
          em.getTransaction().commit();
        }
      } catch (Exception e) {
          System.out.println("test: " + e.getMessage());
      } finally {
          em.close();
      }
  }
  
    public List<ApiLinks> getApiLinks() {
        EntityManager em = emf.createEntityManager();
        List<ApiLinks> apiList = new ArrayList();
        Query query;
        
        try {
            query = em.createQuery("SELECT a FROM ApiLinks a");
            apiList = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return apiList;
    }
}
