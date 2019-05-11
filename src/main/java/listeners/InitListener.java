package listeners;


import dataLayer.dataMappers.*;
import model.Bid;
import model.Project;
import model.User;
import repository.Database;
import service.APIHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InitListener implements ServletContextListener {

    public APIHelper apiHelper = new APIHelper();
    private ScheduledExecutorService getProjectScheduler;
    private ScheduledExecutorService auctionScheduler;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            Class.forName("org.sqlite.JDBC");
            Database.setMapper();
            apiHelper.updateSkills();
            //Database.addSomeUsersAndEndorsements();
            //Database.addAuthenticatedUser();
            apiHelper.updateProjects();

            getProjectScheduler = Executors.newSingleThreadScheduledExecutor();
            auctionScheduler = Executors.newSingleThreadScheduledExecutor();

            getProjectScheduler.scheduleAtFixedRate(new projectScheduler(), 5, 5, TimeUnit.MINUTES);
            auctionScheduler.scheduleAtFixedRate(new auctionScheduler(), 1, 1, TimeUnit.MINUTES);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class projectScheduler implements Runnable {
    public APIHelper apiHelper = new APIHelper();
    @Override
    public void run() {
        try {
            System.out.println("lkpoasfkjijifasjijsaofijoiasfj");
            apiHelper.updateProjects();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class auctionScheduler implements Runnable {
    @Override
    public void run() {
        try {
            ArrayList<Project> projects = Database.getUnresolvedProjects();
            for (Project project : projects){
                System.out.println(project.getId());
                Database.auction(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

