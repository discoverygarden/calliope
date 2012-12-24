/* This file is part of hritserver.
 *
 *  hritserver is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  hritserver is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with hritserver.  If not, see <http://www.gnu.org/licenses/>.
 */

package hritserver.importer.filters;
import hritserver.importer.Archive;
import hritserver.exception.ImportException;
import hritserver.exception.HritException;
import hritserver.json.JSONDocument;

/**
 * Specify how filters interact with the outside world
 * @author desmond
 */
public abstract class Filter 
{
    public Filter()
    {
    }
    /**
     * Get the raw name of this filter e.g. "play"
     * @return the filter name
     * @throws HritException 
     */
    public String getName() throws HritException
    {
        String className = this.getClass().getSimpleName();
        int pos = className.indexOf("Filter");
        if ( pos != -1 )
            return className.substring(0,pos);
        else
            throw new HritException("invalid class name: "+className);
    }
    public abstract void configure( JSONDocument config );
    /**
     * Short description of this filter
     * @return a string
     */
    public abstract String getDescription();
    /**
     * Subclasses should override this
     * @param input the input text for conversion
     * @param name the name of the new version
     * @param cortex the cortex archive to save split text in
     * @param corcode the corcode archive to save split markup in
     * @return the log output
     */
    public abstract String convert( String input, String name, Archive cortex, 
        Archive corcode ) throws ImportException;
}
