package me.BaffledWaffle.report;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTreeBuilder {

    /**
     * The method build file tree relative to the root directory
     * @param root root directory path
     * @return FileNode with all his children
     */
    public static FileNode buildTree( Path root ) throws IOException {
        return buildTreeRec( root.toAbsolutePath().normalize(), root.toAbsolutePath().normalize() );
    }

    /**
     * Recursively build file tree
     */
    private static FileNode buildTreeRec( Path current, Path root ) throws IOException {
        String name = root.relativize(current).toString();
        if( name.isEmpty() ) name = current.getFileName().toString(); // for root

        FileNode node = new FileNode( name, current, Files.isDirectory( current ) );

        if( Files.isDirectory( current ) ) {
            try(DirectoryStream<Path> stream = Files.newDirectoryStream( current )) {
                for( Path child : stream )
                    node.addChild( buildTreeRec( child, root ) );
            }
        }

        return node;
    }

}
