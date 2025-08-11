package me.BaffledWaffle.parsers;

import me.BaffledWaffle.reports.LocationRange;
import me.BaffledWaffle.reports.Message;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for parsing Nu validator (vnu.jar) response JSON
 *
 * @author Jevgeni Golosov [BaffledWaffle]
 * @since 1.0
 *
 */
public class NuJsonParser {


    public NuJsonParser() {}

    /**
     * The method parses given JSON object and returns it as {@link Message} object.
     * @param messageJson JSON object.
     * @return parsed JSON as {@link Message} object.
     */
    public Message parseJsonMessage(JSONObject messageJson ) {
        // 1. Check type/subtype and create message object
        Message.MessageType type = getType( messageJson );
        Message message = new Message( type );

        // 2. Get location range
        LocationRange locationRange = getLocationRange( messageJson );

        // 3. Get Header (message) and Content (extract)
        String header = getHeader( messageJson );
        String content = getContent( messageJson );

        // 5. Get hilite
        int hiliteStart = getHiliteStart( messageJson );
        int hiliteLength = getHiliteLength( messageJson );

        // 6. Create message
        message.setHeader( header );
        message.setContent( content );
        message.setLocationRange( locationRange );
        message.setContentHilite( hiliteStart, hiliteLength );

        return message;
    }

    /**
     * The method finds and returns file URL as Path using helper method.
     * @param messageJson JSON Object
     * @return URL as Path
     */
    public Path getFilePath( JSONObject messageJson ) {
        return getUrl( messageJson );
    }

    /**
     * The method parses JSON object and returns file URL as Path.
     * @param messageJson JSON object.
     * @return URL as Path.
     */
    private Path getUrl(JSONObject messageJson ) {
        String url = messageJson.optString( "url", null );
        if( url == null )
            throw new ParserException( "URL is null. Message: " + messageJson );

        Path path;
        try {
            path = Paths.get( new URI( url ) ).toAbsolutePath().normalize();
        } catch ( URISyntaxException e ) {
            throw new ParserException( "URI parsing exception: " + e.getMessage() );
        }

        return path;
    }

    /**
     * The method parses JSON object and returns message's type.
     * @param messageJson JSON object.
     * @return message type.
     */
    private Message.MessageType getType( JSONObject messageJson ) {

        String type = messageJson.optString( "type", null );

        // check for exceptions
        if( type == null ) {
            throw new ParserException( "Type is null. Message: " + messageJson );
        }

        String subType = messageJson.optString( "subType", null );

        if ( type.equals( "info" ) && subType.equals( "warning" ) )
            return Message.MessageType.WARNING;
        else if ( type.equals( "error" ) && subType == null )
            return Message.MessageType.ERROR;
        else if ( type.equals( "error" ) && subType.equals( "fatal" ) )
            return Message.MessageType.FATAL_ERROR;
        else if ( type.equals( "non-document-error" ) && subType != null ) {
            System.out.println( "File: " + getFilePath( messageJson ) );
            System.out.println( "NonDocumentError: " + subType );
            System.out.println();
            return Message.MessageType.NON_DOCUMENT_ERROR;
        }

        return null;
    }

    /**
     * The method parses JSON object and returns location range as {@link LocationRange}.
     * @param messageJson JSON object.
     * @return location range as {@link LocationRange}.
     */
    private LocationRange getLocationRange( JSONObject messageJson ) {
        String firstLineStr = messageJson.optString( "firstLine", null );
        Integer firstLine = ( firstLineStr == null ) ? null : Integer.valueOf( firstLineStr );

        String lastLineStr = messageJson.optString( "lastLine", null );
        Integer lastLine = ( lastLineStr == null ) ? null : Integer.valueOf( lastLineStr );

        String firstColumnStr = messageJson.optString( "firstColumn", null );
        Integer firstColumn = ( firstColumnStr == null ) ? null : Integer.valueOf( firstColumnStr );

        String lastColumnStr = messageJson.optString( "lastColumn", null );
        Integer lastColumn = ( lastColumnStr == null ) ? null : Integer.valueOf( lastColumnStr );

        return new LocationRange( firstLine, lastLine, firstColumn, lastColumn );
    }

    /**
     * The method parses JSON object and returns message's header (message) as {@link String}.
     * @param messageJson JSON object.
     * @return header as {@link String}.
     */
    private String getHeader( JSONObject messageJson ) {
        return messageJson.optString( "message", null );
    }

    /**
     * The method parses JSON object and returns message's content (extract) as {@link String}.
     * @param messageJson JSON object.
     * @return content as {@link String}.
     */
    private String getContent( JSONObject messageJson ) {
        return messageJson.optString( "extract", null );
    }

    /**
     * The method parses JSON object and returns extract hilite start point as integer.
     * @param messageJson JSON object.
     * @return extract hilite start point as integer.
     */
    private int getHiliteStart( JSONObject messageJson ) {
        return messageJson.optInt( "hiliteStart", 0 );
    }

    /**
     * The method parses JSON object and returns extract hilite length as integer.
     * @param messageJson JSON object.
     * @return hilite length as integer.
     */
    private int getHiliteLength( JSONObject messageJson ) {
        return messageJson.optInt( "hiliteLength", 0 );
    }

}
