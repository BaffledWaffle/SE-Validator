package me.BaffledWaffle.files;

import me.BaffledWaffle.reports.FileReport;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileNode {

    private final Path path; // file absolute path
    private final Path root; // root for calculating relative path in project
    private final boolean isDirectory;
    private List<FileNode> children;

    private FileReport report;

    public FileNode( Path path, Path root, boolean isDirectory ) {
        this.path = path;
        this.root = root;
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

    public String getRelativeName() {
        return root.relativize( path ).toString().replace( "\\", "/" );
    }

    public String toString() {

        if( !isDirectory ) {
            return getRelativeName();
        }

        StringBuilder r = new StringBuilder();
        r.append(getRelativeName()).append("\n");
        for( FileNode child : children )
            r.append( child.toString() ).append( "\n" );

        return r.toString();
    }

}
