package filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import controller.Utils;
import repository.Database;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter( "/*" )
public class AuthFilter implements Filter {

    private static final java.util.logging.Logger LOG = Logger.getLogger( AuthFilter.class.getName() );

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer "; // with trailing space to separate token

    private static final int STATUS_CODE_UNAUTHORIZED = 401;
    private static final int STATUS_CODE_FORBIDDEN = 403;

    @Override
    public void init( FilterConfig filterConfig ) {
        LOG.info( "AuthFilter initialized" );
    }

    @Override
    public void doFilter( final ServletRequest servletRequest,
                          final ServletResponse servletResponse,
                          final FilterChain filterChain ) throws IOException, ServletException {

        String path = ((HttpServletRequest) servletRequest).getRequestURI();
        if (
                path.startsWith("/login") ||
                path.startsWith("/register") ||
                ((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")
        ) {
            filterChain.doFilter(servletRequest, servletResponse); // Just continue chain.
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        servletRequest.setCharacterEncoding("UTF-8");

        String jwt = getBearerToken( httpRequest );
        System.out.println(jwt);
        if ( jwt == null || jwt.isEmpty() ) {
            LOG.info("No JWT provided, go on unauthenticated");
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            Utils.sendJSON("{}", httpResponse, STATUS_CODE_UNAUTHORIZED);
        } else {
            try{
                DecodedJWT decodedJwt = Utils.verifyJWT(jwt);
                servletRequest.setAttribute("contextUser", Database.findUserById(decodedJwt.getId()));
                LOG.info( "Logged in using JWT" );
                filterChain.doFilter( servletRequest, servletResponse );
            } catch (JWTVerificationException e) {
                LOG.info("JWT Verify Error " + e.getMessage());
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                Utils.sendJSON("{}", httpResponse, STATUS_CODE_FORBIDDEN);
            } catch (SQLException e) {
                LOG.info("SQLException " + e.getMessage());
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                Utils.sendJSON("{}", httpResponse, STATUS_CODE_FORBIDDEN);
            }
        }
    }

    @Override
    public void destroy() {
        LOG.info( "JwtAuthenticationFilter destroyed" );
    }

    /**
     * Get the bearer token from the HTTP request.
     * The token is in the HTTP request "Authorization" header in the form of: "Bearer [token]"
     */
    private String getBearerToken( HttpServletRequest request ) {
        String authHeader = request.getHeader( AUTH_HEADER_KEY );
        System.out.println("authHeader " + authHeader);
        if ( authHeader != null && authHeader.startsWith( AUTH_HEADER_VALUE_PREFIX ) ) {
            System.out.println("Salam");
            return authHeader.substring( AUTH_HEADER_VALUE_PREFIX.length() );
        }
        return null;
    }
}