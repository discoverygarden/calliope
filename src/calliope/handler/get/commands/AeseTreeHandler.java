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
package calliope.handler.get.commands;
import calliope.exception.AeseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import calliope.handler.get.AeseGetHandler;

/**
 * Handle a request for a Newick tree
 * @author desmond
 */
public class AeseTreeHandler extends AeseGetHandler
{
    /**
     * Handle a request for a Newick tree
     * @param request the original request
     * @param urn the original request urn
     * @throws a AeseException if something went wrong
     * @return the Newick tree
     */
    @Override
    public void handle( HttpServletRequest request, 
        HttpServletResponse response, String urn )
        throws AeseException
    {
        throw new AeseException( "unimplemented" );
    }
}
