package me.BaffledWaffle.files;

import me.BaffledWaffle.reports.FileReport;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileNode {

    private final Path path; // file absolute path
    private final boolean isDirectory;
    private List<FileNode> children;

    private FileReport report;

    public FileNode( Path path, boolean isDirectory ) {
        this.path = path;
        this.isDirectory = isDirectory;

        if( isDirectory )
            children = new ArrayList<>();
    }

    public void addChild( FileNode fileNode ) {
        children.add( fileNode );
    }

    public void setReport( FileReport report ) {
        this.report = report;
    }

    public FileReport getReport() {
        return report;
    }

    public List<FileNode> getChildren() {
        return children;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public Path getPath() {
        return path.toAbsolutePath().normalize();
    }

    public String getName() {
        return path.getFileName().toString();
    }

}
