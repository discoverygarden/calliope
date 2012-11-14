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
package hritserver.constants;

/**
 * Parameters passed in HTTP requests
 * @author desmond
 */
public class Params 
{
    /** passed-in form param base name for corcodes */
    public final static String CORCODE = "CORCODE";
    /** passed-in form param base name for styles */
    public final static String STYLE = "STYLE";
    /** passed-in form param base name for formats */
    public final static String FORMAT = "FORMAT";
    /** the contents of an XML recipe file */
    public final static String RECIPE = "RECIPE";
    /** an XML file uploaded for stripping/importing */
    public final static String XML = "XML";
    /** an extra short name (for compare) */
    public final static String SHORTNAME = "SHORTNAME";
    /** an extra groups path for compare */
    public final static String GROUPS = "GROUPS";
    /** name of list dropdowns etc */
    public final static String NAME = "NAME";
    /** calling name of javascript function */
    public final static String FUNCTION = "FUNCTION";
    /** ID for currently chosen document */
    public final static String DOC_ID = "DOC_ID";
    /** language for uploads */
    public final static String LANGUAGE = "LANGUAGE";
    /** author for uploads */
    public final static String AUTHOR = "AUTHOR";
    /** work for uploads */
    public final static String WORK = "WORK";
    /** section for uploads */
    public final static String SECTION = "SECTION";
     /** subsection for uploads */
    public final static String SUBSECTION = "SUBSECTION";
    /** mvd version+groups for version 1 */
    public final static String VERSION1 = "version1";
    /** mvd version+groups for version 1 */
    public final static String VERSION2 = "version2";
    /** chosen plain text filter  */
    public final static String FILTER = "FILTER";
    /** chosen splitter config  */
    public final static String SPLITTER = "splitter";
    /** chosen stripper config  */
    public final static String STRIPPER = "stripper";
    /** chosen plain text filter config  */
    public final static String TEXT = "text";
    /** offset into a version */
    public final static String OFFSET = "OFFSET";
    /** length of a range in the given version */
    public final static String LENGTH = "LENGTH";
    /** hide merged versions in a table */
    public final static String HIDE_MERGED = "HIDE_MERGED";
    /** compact versions where possible in a table */
    public final static String COMPACT = "COMPACT";
    /** expand differences to whole words in table */
    public final static String WHOLE_WORDS = "WHOLE_WORDS";
    /** choose only some versions for comparison */
    public final static String SOME_VERSIONS = "SOME_VERSIONS";
    /** set of selected versions if not ALL */
    public final static String SELECTED_VERSIONS = "SELECTED_VERSIONS";
    /** prefix for short version (value=long version name)*/
    public final static String SHORT_VERSION = "VERSION_";
    /** ID of long name string (to facilitate dynamic replacement */
    public final static String LONG_NAME_ID = "LONG_NAME_ID";
    /** kind of differences to generate */
    public final static String DIFF_KIND = "diff_kind";
    /** first merge id for table alignment */
    public final static String FIRSTID = "FIRSTID";
}
