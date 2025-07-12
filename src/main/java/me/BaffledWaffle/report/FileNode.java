package me.BaffledWaffle.report;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileNode {

    private final String name;
    private final Path absolutePath;
    private final boolean isDirectory;
    private final List<FileNode> children = new ArrayList<>();

    public FileNode( String name, Path absolutePath, boolean isDirectory ) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.isDirectory = isDirectory;
    }

    /**
     * The method adds child node
     */
    public void addChild( FileNode child ) {
        children.add( child );
    }

    /**
     * Finds and returns all found HTML files absolute paths in FileNode and his children
     * @return found HTML files absolute paths
     */
    public List<Path> getHtmlAbsolutePaths() {
        return getExtensionFilesAbsolutePaths( ".html" );
    }

    /**
     * Finds and returns all files with given extension in FileNode and his children
     * @param extension file extension
     * @return all files with given extension
     */
    private List<Path> getExtensionFilesAbsolutePaths( String extension ) {
        List<Path> paths = new ArrayList<>();

        if( Files.isRegularFile( absolutePath) ) {
            String filename = absolutePath.getFileName().toString().toLowerCase();
            if( filename.endsWith( extension ) )
                paths.add( absolutePath );
        }

        for( FileNode child : children ) {
            paths.addAll( child.getExtensionFilesAbsolutePaths( extension ) );
        }

        return paths;
    }

    // Getters
    public String getName() { return name; }
    public boolean isDirectory() { return isDirectory; }
    public List<FileNode> getChildren() { return children; }
}
