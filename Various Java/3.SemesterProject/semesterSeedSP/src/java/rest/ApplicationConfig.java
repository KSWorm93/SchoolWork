package rest;

import java.util.Set;
import javax.ws.rs.core.Application;


@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }

  /**
   * Do not modify addRestResourceClasses() method.
   * It is automatically populated with
   * all resources defined in the project.
   * If required, comment out calling this method in getClasses().
   */
  private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(exception.AlreadyExistExceptionMapper.class);
        resources.add(exception.InvalidDataExceptionMapper.class);
        resources.add(exception.NoFlightsFoundExceptionMapper.class);
        resources.add(exception.NoServerConnectionFoundExceptionMapper.class);
        resources.add(exception.UnknownServerExceptionMapper.class);
        resources.add(rest.Admin.class);
        resources.add(rest.FlightRest.class);
        resources.add(rest.ReservationRest.class);
        resources.add(rest.User.class);
        resources.add(rest.UserSettings.class);
        resources.add(security.JWTAuthenticationFilter.class);
        resources.add(security.Login.class);
        resources.add(security.NotAuthorizedExceptionMapper.class);
        resources.add(security.Register.class);
        resources.add(security.RolesAllowedFilter.class);
  }
  
}
