package me.BaffledWaffle.report;

import java.util.ArrayList;
import java.util.List;

public class FileNode {

    private final String name;
    private final boolean isDirectory;
    private final List<FileNode> children = new ArrayList();

    public FileNode( String name, boolean isDirectory ) {
        this.name = name;
        this.isDirectory = isDirectory;
    }

    /**
     * The method adds child node
     */
    public void addChild( FileNode child ) {
        children.add( child );
    }

    // Getters
    public String getName() { return name; }
    public boolean isDirectory() { return isDirectory; }
    public List<FileNode> getChildren() { return children; }
}
