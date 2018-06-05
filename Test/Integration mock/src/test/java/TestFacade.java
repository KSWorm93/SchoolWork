/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entity.Facade;
import entity.Project;
import entity.ProjectUser;
import exceptions.ProjectNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Kasper
 */
public class TestFacade {

    EntityManagerFactory emf;
    EntityManager em;
    Facade facade;

    public TestFacade() {
    }

    @Before
    public void setUp() {
        emf = mock(EntityManagerFactory.class);
        em = mock(EntityManager.class);
        facade = mock(Facade.class);
    }

    @After
    public void tearDown() {
        emf.close();
        em.close();
    }

    @Test
    public void testFindUser() {
        ProjectUser user = new ProjectUser();

        long id = 1;
        user.setId(id);
        user.setEmail("test@tester.dk");
        user.setUserName("testUserName");

        when(emf.createEntityManager()).thenReturn(em);
        when(facade.getEM()).thenReturn(em);
        when(em.find(ProjectUser.class, id)).thenReturn(user);
        when(facade.findUser(id)).thenReturn(user);

        ProjectUser returnUser = facade.findUser(id);
        assertEquals(user, returnUser);
    }

    @Test
    public void testFindProject() throws ProjectNotFoundException {
        Project project = new Project();

        long id = 1;
        project.setId(id);
        project.setDescription("testProjectDescription");
        project.setName("testProjectName");

        when(emf.createEntityManager()).thenReturn(em);
        when(facade.getEM()).thenReturn(em);
        when(em.find(Project.class, id)).thenReturn(project);
        when(facade.findProject(id)).thenReturn(project);

        Project found = facade.findProject(id);
        assertEquals(project, found);
    }
}
