package listeners;


import model.User;
import repository.Database;
import service.APIHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

public class InitListener implements ServletContextListener {

    public APIHelper apiHelper = new APIHelper();
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        Database.addSomeUsersAndEndorsements();
        try {
            Class.forName("org.sqlite.JDBC");
            Database.addAuthenticatedUser();
            apiHelper.updateProjects();
            apiHelper.updateSkills();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
