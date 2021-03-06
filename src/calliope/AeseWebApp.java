/* This file is part of calliope.
 *
 *  calliope is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  calliope is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with calliope.  If not, see <http://www.gnu.org/licenses/>.
 */
package calliope;
import calliope.exception.AeseException;
import calliope.exception.AeseExceptionMessage;
import calliope.handler.AeseHandler;
import calliope.handler.get.AeseGetHandler;
import calliope.handler.put.AesePutHandler;
import calliope.handler.post.AesePostHandler;
import calliope.handler.delete.AeseDeleteHandler;
import calliope.db.Repository;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.ServletConfig;

/**
 * WebApp for Tomcat etc.
 * @author desmond
 */
public class AeseWebApp extends HttpServlet
{
    static final String REPOSITORY = "repository";
    static final String USER = "user";
    static final String PASSWORD = "password";
    static final String DBPORT = "dbport";
    static final String WEBROOT = "webroot";
    private String getParameter( ServletConfig config, String key, 
        String defValue )
    {
        String value = config.getInitParameter( key );
        if ( value == null )
            value = defValue;
        return value;
    }
    /**
     * Open a shared connection
     * @param req the HttpServlet request
     * @throws AeseException 
     */
    private void openConnection( HttpServletRequest req ) throws AeseException
    {
        ServletConfig config = getServletConfig();
        String repoName = getParameter( config, REPOSITORY, "MONGO" );
        //String repoName = getParameter( config, REPOSITORY, "COUCH" );
        Repository repository = Repository.valueOf( repoName );
        String user = getParameter( config, USER, "admin" );
        String password = getParameter( config, PASSWORD, "jabberw0cky" );
        String dbPortValue = getParameter( config, DBPORT, "27017" );
        //String dbPortValue = getParameter( config, DBPORT, "5984" );
        String webRoot = getParameter( config, WEBROOT, "/var/www" );
        int dbPort = Integer.valueOf( dbPortValue );
        String host = req.getServerName();
        int wsPort = req.getServerPort();
        Connector.init( repository, user, password, host, dbPort, wsPort, 
            webRoot );
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, java.io.IOException
    {
        try
        {
            String method = req.getMethod();
            String target = req.getRequestURI();
            // hack to fix %2F bug");
            target = Utils.removePercent2F( target );
            // remove webapp prefix
            if ( target.startsWith("/calliope") )
            {
                target = target.substring( 9 );
                Service.PREFIX = "/calliope";
            }
            //resp.getWriter().println("<html><body><p>uri="+target
            //    +"</p></body></html>");
            AeseHandler handler;
            if ( !Connector.isOpen() )
                openConnection( req );
            if ( method.equals("GET") )
                handler = new AeseGetHandler();
            else if ( method.equals("PUT") )
                handler = new AesePutHandler();
            else if ( method.equals("DELETE") )
                handler = new AeseDeleteHandler();
            else if ( method.equals("POST") )
                handler = new AesePostHandler();
            else
                throw new AeseException("Unknown http method "+method);
            resp.setStatus(HttpServletResponse.SC_OK);
            handler.handle( req, resp, target );
        }
        catch ( AeseException e )
        {
            AeseException he;
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            if ( e instanceof AeseException )
                he = (AeseException) e ;
            else
                he = new AeseException( e );
            resp.setContentType("text/html");
            try 
            {
                resp.getWriter().println(
                    new AeseExceptionMessage(he).toString() );
            }
            catch ( Exception e2 )
            {
                e.printStackTrace( System.out );
            }
        }
    }
}
