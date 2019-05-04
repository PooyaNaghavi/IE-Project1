package listeners;


import dataLayer.dataMappers.*;
import model.Bid;
import model.User;
import repository.Database;
import service.APIHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InitListener implements ServletContextListener {

    public APIHelper apiHelper = new APIHelper();
    private ScheduledExecutorService scheduler;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            Class.forName("org.sqlite.JDBC");
            Database.setMapper();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            apiHelper.updateSkills();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new MinJob(), 0, 5, TimeUnit.MINUTES);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            Database.addSomeUsersAndEndorsements();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            Database.addAuthenticatedUser();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            apiHelper.updateProjects();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class MinJob implements Runnable {
    public APIHelper apiHelper = new APIHelper();
    @Override
    public void run() {
        try {
            apiHelper.updateProjects();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

