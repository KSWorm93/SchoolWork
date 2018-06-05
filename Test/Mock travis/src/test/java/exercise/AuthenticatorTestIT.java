/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise;

import Utils.Mailer;
import exercise.fakedatabase.UserFacadeFake;
import exercise.realdatabase.UserFacadeRealDB;
import static org.mockito.Mockito.mock;

/**
 *
 * @author kasper
 */
public class AuthenticatorTestIT extends AuthenticatorTest {

    @Override
    public Authenticator makeAuthenticator() {
        Mailer mailer = mock(Mailer.class);

        if (System.getenv("TRAVIS") != null) {
            return new Authenticator(new UserFacadeRealDB("pu_mySql_travis_Integration"), mailer);
        } else {
            return new Authenticator(new UserFacadeRealDB("pu_localDB"), mailer);
        }
    }
}
