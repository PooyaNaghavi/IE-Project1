package filters;

import exceptions.NotFoundException;
import repository.Database;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.xml.crypto.Data;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("contextUser", Database.findUserById("1"));
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
