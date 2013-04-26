/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calliope.db;

import calliope.exception.AeseException;
import calliope.json.JSONDocument;
import calliope.json.JSONValue;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.WriteResult;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import java.util.Set;
import java.util.Iterator;
import java.io.InputStream;


/**
 * Database interface for MongoDB
 * @author desmond
 */
public class MongoConnection extends Connection
{
    MongoClient client;
    static int MONGO_PORT = 27017;
    static String DOCID_KEY = "docid";
    /** connection to database */
    DB  db;
    public MongoConnection( 
        String user, String password, String host, 
        int dbPort, int wsPort )
    {
        super( user, password, host, dbPort, wsPort );
    }
    /**
     * Connect to the database
     * @throws Exception 
     */
    private void connect() throws Exception
    {
        if ( db == null )
        {
            MongoClient mongoClient = new MongoClient( host, MONGO_PORT );
            db = mongoClient.getDB("calliope");
            boolean auth = db.authenticate( user, password.toCharArray() );
            if ( !auth )
                throw new AeseException( "MongoDB authentication failed");
        }
    }
    /**
     * Get the collection name from the path
     * @param path the path including the collection name as the first 
     * @return a String or null
     */
    private DBCollection getCollection( String path ) throws AeseException
    {
        String collName = getCollName( path );
        if ( collName != null )
        {
            if ( db.collectionExists(collName) )
            {
                DBCollection coll = db.getCollection( collName );
                if ( coll == null )
                    throw new AeseException( "Unknown collection "+collName );
                else
                    return coll;
            }
        }
        return null;
    }
    /**
     * Get the docID proper from the path
     * @param path the path including the collection name
     * @return the docID stripped of the collection name
     */
    private DBObject getDocQuery( String path )
    {
        String docID = getDocID( path );
        if ( docID != null )
            return new BasicDBObject( DOCID_KEY, docID );
        else
            return null;
    }
    /**
     * Fetch a resource from the server, or try to.
     * @param path the path to the reputed resource
     * @return the response as a string or null if not found
     */
    @Override
    public String getFromDb( String path )
    {
        try
        {
            connect();
            DBCollection coll = getCollection( path );
            DBObject query = getDocQuery( path );
            DBObject obj = coll.findOne( query );
            if ( obj != null )
                return obj.toString();
            else
                throw new AeseException( "failed to find "+path );
        }
        catch ( Exception e )
        {
            e.printStackTrace( System.out );
            return null;
        }
    }
    /**
     * Convert a JSONDocument to a DBObject
     * @param jDoc the json document already parsed from the string
     * @return null if it failed else the doc
     */
    private DBObject jsonDocToDBObject( JSONDocument jDoc )
    {
        BasicDBObject doc = new BasicDBObject();
        Set<String> keys = jDoc.keySet();
        Iterator<String> iter = keys.iterator();
        while ( iter.hasNext() )
        {
            String key = iter.next();
            Object obj = jDoc.get( key );
            if ( obj instanceof JSONDocument )
                doc.append( key, jsonDocToDBObject((JSONDocument)obj) );
            else if ( obj instanceof JSONValue )
                doc.append( key, ((JSONValue)obj).getValue() );
        }
        return doc;
    }
    /**
     * Convert a json string to a DBObject
     * @param json the json document as a string
     * @return null if it failed else the DBObject
     */
    private DBObject jsonToDBObject( String json )
    {
        JSONDocument jDoc = JSONDocument.internalise( json );
        if ( jDoc != null )
            return jsonDocToDBObject( jDoc );
        return null;
    }
    /**
     * PUT a json file to the database
     * @param path the full path of the resource including database
     * @param json the json to put there
     * @return the server response
     */
    @Override
    public String putToDb( String path, String json ) throws AeseException
    {
        try
        {
            DBObject doc = jsonToDBObject(json);
            connect();
            DBCollection coll = getCollection( path );
            DBObject query = getDocQuery( path );
            WriteResult result = coll.update( query, doc, true, true );
            return result.toString();
        }
        catch ( Exception e )
        {
            throw new AeseException( e );
        }
    }
    /**
     * Remove a document from the database
     * @param path the full path of the resource including database
     * @param json the json to put there
     * @return the server response
     */
    @Override
    public String removeFromDb( String path ) throws AeseException
    {
        try
        {
            connect();
            DBCollection coll = getCollection( path );
            DBObject query = getDocQuery( path );
            WriteResult result = coll.remove( query );
            return result.toString();
        }
        catch ( Exception e )
        {
            throw new AeseException( e );
        }
    }
    /**
     * Get an image from the database
     * @param path the path to the corpix
     * @return the image data
     */
    @Override
    public byte[] getImageFromDb( String path )
    {
        try
        {
            DBObject query = getDocQuery( path );
            String collName = getCollName( path );
            GridFS gfs = new GridFS( db, collName );
            GridFSDBFile file = gfs.findOne( query );
            InputStream ins = file.getInputStream();
            long dataLen = file.getLength();
            // this only happens if it is > 2 GB
            if ( dataLen > Integer.MAX_VALUE )
                throw new AeseException( "file too big (size="+dataLen+")" );
            byte[] data = new byte[(int)dataLen];
            int offset = 0;
            while ( ins.available()>0 && offset < dataLen )
            {
                int len = ins.available();
                offset += ins.read( data, offset, len );
            }
            return data;
        }
        catch ( Exception e )
        {
            e.printStackTrace( System.out );
            return null;
        }
    }
    /**
     * Store an image in the database
     * @param path the path to the resource
     * @param data the image data to store
     * @throws AeseException 
     */
    @Override
    public void putImageToDb( String path, byte[] data ) throws AeseException
    {
        DBObject query = getDocQuery( path );
        String collName = getCollName( path );
        GridFS gfs = new GridFS( db, collName );
        GridFSInputFile	file = gfs.createFile( data );
        file.setFilename( getDocID(path) );
        file.save();
    }
    /**
     * Delete an image from the database
     * @param path the full path to the file
     * @throws AeseException 
     */
    @Override
    public void removeImageFromDb( String path ) throws AeseException
    {
        try
        {
            String collName = getCollName( path );
            DBObject query = getDocQuery( path );
            GridFS gfs = new GridFS( db, collName );
            GridFSDBFile file = gfs.findOne( query );
            gfs.remove( file );
        }
        catch ( MongoException e )
        {
            throw new AeseException( e );
        }
    }
}