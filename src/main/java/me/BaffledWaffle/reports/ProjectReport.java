package me.BaffledWaffle.reports;

import me.BaffledWaffle.files.FileNode;

/**
 * <p>Presents project report with group name (project's directory name) and tree-like file structure.</p>
 * <p>Class keeps group name and root of file tree (represented by object {@link FileNode}).</p>
 *
 * @see FileNode
 * @author Jevgeni Golosov [BaffledWaffle]
 * @since 1.0
 */
public class ProjectReport {

    private String name; // project's group name
    private FileNode fileTree; // project's file tree root FileNode

    public ProjectReport( String name, FileNode fileTree ) {
        this.name = name;
        this.fileTree = fileTree;
    }

    // == GETTERS, SETTERS ==

    public String getName() {
        return name;
    }

    public FileNode gitFileTree() {
        return this.fileTree;
    }

}
