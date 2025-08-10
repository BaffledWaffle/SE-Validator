package me.BaffledWaffle.reports;

public class LocationRange {

    private final Integer firstLine;
    private final Integer lastLine;
    private final Integer firstColumn;
    private final Integer lastColumn;

    public LocationRange( Integer firstLine, Integer lastLine, Integer firstColumn, Integer lastColumn ) {
        this.firstLine = firstLine;
        this.lastLine = lastLine;
        this.firstColumn = firstColumn;
        this.lastColumn = lastColumn;
    }

    // == GETTERS, SETTERS ==

    public int getFirstLine() {
        return  firstLine;
    }

    public int getLastLine() {
        return lastLine;
    }

    public int getFirstColumn() {
        return firstColumn;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    /**
     * The method returns location range as text (String).
     * Returns text in a different format, depending on the coordinate range.
     * For example: "125:25 -> 130:1" from 125th line's 25th column to 130th line's 1st column.
     * "120:10 -> 120:15" only one line: 120th, from 10th to 15th columns.
     * @return LocationRange representation as String
     */
    public String toString() {

        // Have all 4 coordinates
        if( allNotNull( firstLine, firstColumn, lastLine, lastColumn ) )
            return String.format( "%d:%d -> %d:%d", firstLine, firstColumn, lastLine, lastColumn );

        // Have last (only) line and one-line range
        if( allNotNull( lastLine, firstColumn, lastColumn ) )
            return String.format( "%d:%d -> %d:%d", lastLine, firstColumn, lastLine, lastColumn );

        // Else have only lastLine + lastColumn
        return String.format( "%d:%d", lastLine, lastColumn );

    }

    private boolean allNotNull( Object... values ) {
        for( Object v : values )
            if( v == null ) return false;

        return true;
    }
}
