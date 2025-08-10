package me.BaffledWaffle.files;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileTreeUtils {

    public static FileNode buildTree( Path root ) {

        if ( !Files.isDirectory( root ) )
            throw new IllegalArgumentException( "Failipuu juur (root) peab olema kataloog - " + root );

        return buildTreeRec( root.toAbsolutePath().normalize(), root.toAbsolutePath().normalize());
    }

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
                for ( Path entry : stream ) {
                    if( Files.isDirectory( entry ) )
                        directories.add( entry );
                    else
                        files.add( entry );
                }

                Comparator<Path> byName = Comparator.comparing( p -> p.getFileName().toString().toLowerCase() );

                directories.sort( byName );
                files.sort( byName );

                // Create all child nodes for dirs
                for( Path directory : directories ) {
                    FileNode childNode = buildTreeRec( directory, root );
                    node.addChild( childNode );
                }

                // Create all files' child nodes
                for( Path file : files ) {
                    FileNode childNode = new FileNode( file, root, false );
                    node.addChild( childNode );
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return node;

    }

}
