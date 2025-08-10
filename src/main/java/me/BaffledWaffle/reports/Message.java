package me.BaffledWaffle.reports;

public class Message {

    public enum MessageType {
        ERROR,
        WARNING,
        FATAL_ERROR,
        NON_DOCUMENT_ERROR,
    }

    private final MessageType type;

    private LocationRange locationRange;

    private int hiliteStart;
    private int hiliteLength;

    private String header; // "message" in JSON
    private String content; // "extract" in JSON
    private String comment; // comment for non-document-error

    public Message( MessageType type ) {
        this.type = type;
    }

    // == GETTERS, SETTERS ==

    public void setNonDocumentErrorComment( String comment ) {
        this.comment = comment;
    }

    public String getNonDocumentErrorComment() {
        return comment;
    }

    public void setLocationRange( LocationRange locationRange ) {
        this.locationRange = locationRange;
    }

    public void setContentHilite( int hiliteStart, int hiliteLength ) {
        this.hiliteStart = hiliteStart;
        this.hiliteLength = hiliteLength;
    }

    public void setHeader( String headerText ) {
        this.header = headerText;
    }

    public void setContent( String contentText ) {
        this.content = contentText;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }

    public int getHiliteStart() {
        return hiliteStart;
    }

    public int getHiliteLength() {
        return hiliteLength;
    }

    public MessageType getType() {
        return type;
    }

    public LocationRange getLocationRange() {
        return locationRange;
    }

}
