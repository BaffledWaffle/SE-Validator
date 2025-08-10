package me.BaffledWaffle;

import me.BaffledWaffle.files.FileNode;
import me.BaffledWaffle.files.FileTreeUtils;
import me.BaffledWaffle.reports.ProjectReport;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Validator {

    public static List<ProjectReport> getProjectReports(Path projectsDirectory ) {
        List<ProjectReport> projectReports = new ArrayList<>();

        // Create all projects file trees
        List<FileNode> fileTrees = new ArrayList<>();

        return null;

    }

    /**
     * The method builds all projects' trees which are in directory with projects (in alphabetic order A -> Z)
     * @param projectsDirectory directory where is all projects in separate directory
     * @return List of file trees
     */
    private static List<FileNode> getFileTrees( Path projectsDirectory ) {

        List<FileNode> fileTrees = new ArrayList<>();

        try(DirectoryStream<Path> stream = Files.newDirectoryStream( projectsDirectory ) ) {
            List<Path> projectPaths = new ArrayList<>();

            // Get all paths
            for( Path entry : stream ) {
                projectPaths.add( entry );
            }

            // Sort all paths
            Comparator<Path> byName = Comparator.comparing( p -> p.getFileName().toString().toLowerCase() );
            projectPaths.sort( byName );

            // Build tree for every project
            for( Path projectPath : projectPaths ) {
                FileNode projectTree = FileTreeUtils.buildTree( projectPath );
                fileTrees.add( projectTree );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileTrees;
    }

}
