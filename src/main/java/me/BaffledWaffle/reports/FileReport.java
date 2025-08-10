package me.BaffledWaffle.reports;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReport {

    private final List<Message> errors = new ArrayList<>();
    private final List<Message> warnings = new ArrayList<>();
    private final List<Message> fatalErrors = new ArrayList<>();
    private final List<Message> nonDocumentErrors = new ArrayList<>();

    private final Path filePath;

    public FileReport( Path filePath ) {
        this.filePath = filePath;
    };

    public void addMessage( Message message ) {
        switch( message.getType() ) {
            case ERROR:
                errors.add( message );
                break;
            case WARNING:
                warnings.add( message );
                break;
            case FATAL_ERROR:
                fatalErrors.add( message );
                break;
            case NON_DOCUMENT_ERROR:
                nonDocumentErrors.add( message );
                break;
        }
    }

    public List<Message> getErrors() {
        return errors;
    }

    public List<Message> getWarnings() {
        return warnings;
    }

    public List<Message> getFatalErrors() {
        return fatalErrors;
    }

    public List<Message> getNonDocumentErrors() {
        return nonDocumentErrors;
    }

    public Path getFilePath() {
        return filePath;
    }

}
