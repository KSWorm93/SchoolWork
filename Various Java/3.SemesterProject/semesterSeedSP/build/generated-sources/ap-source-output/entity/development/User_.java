package entity.development;

import entity.ReservationRequest.Reservation;
import entity.Role;
import entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-18T08:40:11")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile ListAttribute<User, Role> roles;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, Reservation> reservations;

}