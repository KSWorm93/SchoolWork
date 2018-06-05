package control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Timer implements ServletContextListener {

    private ScheduledExecutorService schedule;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        schedule = Executors.newSingleThreadScheduledExecutor();
        schedule.scheduleAtFixedRate(new XmlReader(), 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        schedule.shutdownNow();
    }
}
