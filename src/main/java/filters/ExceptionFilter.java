package filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.BadConditionException;
import exceptions.NotFoundException;
import controller.Utils;
import repository.Database;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "ExceptionFilter")
public class ExceptionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
        }catch(RuntimeException ex) {
            int status = 500;
            if(ex instanceof BadConditionException){
                status = ((BadConditionException) ex).getStatus();
            }else if(ex instanceof NotFoundException){
                status = ((NotFoundException) ex).getStatus();
            }
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String errorJson = ow.writeValueAsString(ex);

            Utils.sendJSON(errorJson, (HttpServletResponse) resp, status);
//            req.setAttribute("message", ex.getMessage());
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
    public void init(FilterConfig config) throws ServletException {

    }
}
