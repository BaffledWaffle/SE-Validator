package me.BaffledWaffle.files;

import me.BaffledWaffle.reports.FileReport;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>Static class with utils to work with {@link FileNode} class and objects.</p>
 * <p>This class helps еto work with {@link FileNode} class and objects.</p>
 *
 * @see FileNode
 * @author Jevgeni Golosov [BaffledWaffle]
 * @since 1.0
 */
public class FileTreeUtils {

    /**
     * The method builds a file tree relative to root path.
     * @param root file tree root path.
     * @return {@link FileNode} object with <b>root</b> as file tree root.
     */
    public static FileNode buildTree( Path root ) {

        if ( !Files.isDirectory( root ) )
            throw new IllegalArgumentException( "Failipuu juur (root) peab olema kataloog - " + root );

        return buildTreeRec( root.toAbsolutePath().normalize(), root.toAbsolutePath().normalize());
    }

    /**
     * The helper method for recursively building a file tree.
     * @param curr currently processing file path.
     * @param root file tree root path.
     * @return {@link FileNode} object with root as file tree root.
     */
    private static FileNode buildTreeRec( Path curr, Path root ) {

        FileNode node = new FileNode( curr, root, Files.isDirectory(curr) ); // create current directory node

        if( node.isDirectory() ) {
            // list all files/dirs if it is a directory
            // Sort like:
            // 1. Directories in alphabetic order A -> Z
            // 2. Files in alphabetic order A -> Z
            List<Path> directories = new ArrayList<>();
            List<Path> files = new ArrayList<>();


            try( DirectoryStream<Path> stream = Files.newDirectoryStream( curr ) ) { // read all
                // Separate directories and files
                for ( Path entry : stream ) {
                    if( Files.isDirectory( entry ) )
                        directories.add( entry );
                    else
                        files.add( entry );
                }

                // Sorting by name
                Comparator<Path> byName = Comparator.comparing( p -> p.getFileName().toString().toLowerCase() );
                directories.sort( byName );
                files.sort( byName );

                // Create all child nodes for dirs
                for( Path directory : directories ) {
                    FileNode childNode = buildTreeRec( directory, root ); // check for all children
                    node.addChild( childNode ); // add FileNode with his children to parent
                }

                // Create all files' child nodes
                for( Path file : files ) {
                    FileNode childNode = new FileNode( file, root, false ); // create file's FileNode
                    node.addChild( childNode ); // and add it to parent
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return node;

    }

    /**
     * The method returns the list with all {@link FileNode}s, which extensions (end) is equal to ext
     * @param root file tree root
     * @param ext extensions
     * @return list of file nodes ({@link FileNode}) with extension ext
     */
    public static List<FileNode> listFilesByExtension( FileNode root, String ext ) {

        List<FileNode> files = new ArrayList<>();

        for( FileNode child : root.getChildren() ) {
            if( child.isDirectory() )
                files.addAll( listFilesByExtension( child, ext ) );
            else
                if( child.getName().endsWith( ext ) )
                    files.add( child );
        }

        return files;

    }

    /**
     * The method iterates over all elements of the given tree and links given file reports to the nodes.
     * @param tree given tree.
     * @param indexPathFileReport index Map Path-FileReport.
     */
    public static void linkTreeNodesToFileReports(FileNode tree, Map<Path, FileReport> indexPathFileReport ) {

        for( FileNode child : tree.getChildren() ) {
            if( !child.isDirectory() ) {
                // Is file. Get file tree node's path, find it from index Map and set (link) it to file node.
                Path nodePath = child.getPath();
                FileReport fileReport = indexPathFileReport.get( nodePath );
                child.setReport( fileReport );
            }
            else {
                // Is directory, iterate all files/directories inside.
                linkTreeNodesToFileReports( child, indexPathFileReport );
            }
        }

    }

}
