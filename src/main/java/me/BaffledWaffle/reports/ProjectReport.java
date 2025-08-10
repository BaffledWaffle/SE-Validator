package me.BaffledWaffle.reports;

import me.BaffledWaffle.files.FileNode;

public class ProjectReport {

    private String name; // project group's name
    private FileNode fileTree;

    public ProjectReport( String name, FileNode fileTree ) {
        this.name = name;
        this.fileTree = fileTree;
    }

    public String getName() {
        return name;
    }

    public FileNode gitFileTree() {
        return this.fileTree;
    }

}
