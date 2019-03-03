package listeners;


import model.User;
import repository.Database;
import service.APIHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.crypto.Data;
import java.io.IOException;

public class InitListener implements ServletContextListener {

    public APIHelper apiHelper = new APIHelper();
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        Database.addSomeUsersAndEndorsements();
        Database.addAuthenticatedUser();
        try {
            apiHelper.updateProjects();
            apiHelper.updateSkills();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
