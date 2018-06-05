/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.CurrencyRates;
import entity.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author kasper
 */
public class UserFacadeTest {

    UserFacade userFacade;

    @Before
    public void setUp() {
        userFacade = new UserFacade(Persistence.createEntityManagerFactory("test_pu"));
    }

    @Test
    public void testGetUserByUserId() {
        User user = userFacade.getUserByUserId("1");

        assertNotNull(user);
    }

    @Test
    public void testGetUsers() {
        List<User> users = userFacade.getUsers();

        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUserByUserName() {
        User user = userFacade.getUserByUserName("user");

        assertNotNull(user);
        assertEquals("user", user.getUserName());
    }

    @Test
    public void testAuthenticateUser_valid() throws Exception {
        List<String> roles = userFacade.authenticateUser("user", "test");

        assertFalse(roles.isEmpty());
    }

    @Test
    public void testAuthenticateUser_invalid() throws Exception {
        List<String> roles = userFacade.authenticateUser("user", "testasd");

        assertNull(roles);
    }

    @Test
    public void testCreateUser() throws Exception {
        int usersBefore = userFacade.getUsers().size();
        userFacade.createUser(new User("testUserName", "testPassword"));

        assertEquals(usersBefore + 1, userFacade.getUsers().size());

    }

    @Test
    public void testDeleteUser() {
        userFacade.createUser(new User("testSletUser", "testSletPassword"));

        int usersBefore = userFacade.getUsers().size();
        userFacade.deleteUser(userFacade.getUsers().size());

        assertEquals(usersBefore - 1, userFacade.getUsers().size());
    }

    @Test
    public void testCalculateCurrency() {

        String fromCurrency = "EUR";
        String toCurrency = "CAD";
        String amount = "100";
//        Double result = "143.42016725944438";
        //Rounding result down
        Double result = 143.42;

        Date wrongDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(wrongDate.getTime());

        CurrencyRates from;
        CurrencyRates to;

        from = userFacade.getSingleCurrency("EUR", sqlDate);
        to = userFacade.getSingleCurrency("CAD", sqlDate);

        Double stepA = (Double.parseDouble(amount)) * from.getCurrency();
        Double stepB = stepA / to.getCurrency();

        stepB = Math.round(stepB * 100.0) / 100.0;

        assertEquals(stepB, result);
    }

    @Test
    public void testGetTodaysCurrencies() {
        int rates;
        //There is 33 currencies we get, so if rates ends up to equal 33. the test is succesfull
        rates = userFacade.getTodaysCurrencies().size();

        assertEquals(rates, 33);
    }

}
