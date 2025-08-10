package me.BaffledWaffle.files;

import me.BaffledWaffle.reports.FileReport;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Presents the file tree node with path, tree root path and children list (if file node is directory).</p>
 * <p>Class keeps file's path, file tree's root path and list of children if file node is directory.</p>
 * <p>It is possible to build file tree with {@link FileTreeUtils}'s class method <b>buildTree</b>.</p>
 *
 * @see FileTreeUtils
 * @author Jevgeni Golosov [BaffledWaffle]
 * @since 1.0
 */
public class FileNode {

    private final Path path; // file absolute path
    private final Path root; // root for calculating relative path in project
    private final boolean isDirectory;
    private List<FileNode> children; // children list

    private FileReport report;

    public FileNode( Path path, Path root, boolean isDirectory ) {
        this.path = path;
        this.root = root;
        this.isDirectory = isDirectory;

        if( isDirectory )
            children = new ArrayList<>();
    }

    // Adds child
    public void addChild( FileNode fileNode ) {
        children.add( fileNode );
    }

    // == GETTERS, SETTERS ==

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

    /**
     * The method returns absolute, normalized path
     * @return absolute, normalized path
     */
    public Path getPath() {
        return path.toAbsolutePath().normalize();
    }

    /**
     * The method returns the name of the file or directory.
     * @return the name of the file or directory
     */
    public String getName() {
        return path.getFileName().toString();
    }

    /**
     * The method returns the name of the file or directory relative to the root of the tree.
     * @return the name of the file or directory relative to the root of the tree.
     */
    public String getRelativeName() {
        return root.relativize( path ).toString().replace( "\\", "/" );
    }

    @Override
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
