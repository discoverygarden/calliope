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

package calliope.handler.post.importer;
import calliope.importer.filters.Filter;
import calliope.exception.ImportException;
import calliope.json.JSONDocument;
import calliope.importer.Archive;
/**
 * A repository for plain text files
 * @author desmond
 */
public class StageThreeText extends Stage
{
    Filter filter;
    JSONDocument config;
    /**
     * Create an instance and add files later
     * @param filter the name of the text filter to apply
     * @param dict the name of the dictionary to use for hyphenation
     * @param hhExceptions the hard hyphen exceptions list
     */
    public StageThreeText( String filterName, String dict, String hhExceptions ) 
        throws ImportException
    {
        super();
        try
        {
            if ( filterName.length()>0 )
            {
                char first = filterName.charAt(0);
                first = Character.toUpperCase(first);
                filterName = first + filterName.substring( 1 );
                Class c = Class.forName("calliope.importer.filters."
                    +filterName+"Filter");
                filter = (Filter)c.newInstance();
                if ( filter != null )
                {
                    filter.setDict( dict );
                    filter.setHHExceptions( hhExceptions );
                }
            }
            else
                throw new ImportException( "filter name emtpy" );
        }
        catch ( Exception e )
        {
             
        }
    }
    /**
     * Set the text-specific config for the text filter
     * @param config a json document from the database
     */
    public void setConfig( String config )
    {
        this.config = JSONDocument.internalise( config );
    }
    /**
     * Process the files
     * @param cortex a MVD archive for the plain text output
     * @param corcode an MVD archive for the versioned markup output
     * @return the log output
     */
    @Override
    public String process( Archive cortex, Archive corcode ) throws ImportException
    {
        // load config file
        if ( config == null )
            throw new ImportException( "missing config" );
        filter.configure( config );
        for ( int i=0;i<files.size();i++ )
        {
            String input = files.get(i).toString();
            log.append( filter.convert(input,files.get(i).simpleName(),
                cortex,corcode) );
        }
        return log.toString();
    }
}
