package me.BaffledWaffle;

import me.BaffledWaffle.files.FileNode;
import me.BaffledWaffle.files.FileTreeUtils;
import me.BaffledWaffle.parsers.NuJsonParser;
import me.BaffledWaffle.reports.FileReport;
import me.BaffledWaffle.reports.LocationRange;
import me.BaffledWaffle.reports.Message;
import me.BaffledWaffle.reports.ProjectReport;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Validator {

    public static List<ProjectReport> getProjectReports( Path projectsDirectory, Path vnuValidator, Path cssValidator ) throws IOException, InterruptedException, URISyntaxException {
        List<ProjectReport> projectReports = new ArrayList<>();

        // Create all projects file trees
        List<FileNode> fileTrees = getFileTrees( projectsDirectory );
        List<FileNode> htmlFiles = new ArrayList<>();

        // Find and save all html files
        for( FileNode fileTree : fileTrees )
            htmlFiles.addAll( FileTreeUtils.listFilesByExtension( fileTree, ".html" ) );

        // Get JSON object from Nu validator
        JSONObject reportsJson = getNuValidatorJson( htmlFiles, vnuValidator );
        List<FileReport> fileReports = getFileReportsFromJson( reportsJson );

        return projectReports;

    }

    private static List<FileReport> getFileReportsFromJson( JSONObject json ) {

        Map<Path, FileReport> indexPathFileReport = new HashMap<>();

        JSONArray messagesJson = json.getJSONArray( "messages" );
        int numberOfMessages = messagesJson.length();

        // Check if we don't have messages (all files is correct)
        if ( numberOfMessages == 0 )
            return null;

        NuJsonParser parser = new NuJsonParser();

        // Parse JSON messages
        for( int i = 0; i < numberOfMessages; i++ ) {
            // Get JSON message
            JSONObject messageJson = messagesJson.getJSONObject( i );
            Path filePath = parser.getFilePath( messageJson );
            Message message = parser.parseJsonMessage( messageJson );

            // Create new key if it doesn't exist
            if( !indexPathFileReport.containsKey( filePath ) )
                indexPathFileReport.put( filePath, new FileReport( filePath ) );

            // Add message to file's report
            indexPathFileReport.get( filePath ).addMessage( message );

        }

        return new ArrayList<>( indexPathFileReport.values() );
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

    /**
     * The method uses vnu.jar to validate files. Returns JSON object of Nu validator response
     * @param filesToValidate list of files that needs to be checked
     * @param vnu - Nu validator jar file (vnu.jar)
     * @return JSON object of Nu validator response
     * @throws IOException
     * @throws InterruptedException
     */
    private static JSONObject getNuValidatorJson( List<FileNode> filesToValidate, Path vnu ) throws IOException, InterruptedException {

        System.out.println( "Kontrollime failid Nu validaatoriga (vnu.jar)..." );

        // Command's base
        Process process = getVnuProcess(filesToValidate, vnu);

        // Read the response
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

            String output = reader.lines().collect(Collectors.joining("\n"));

            // Wait for process end and return JSON
            int exitCode = process.waitFor();
            if( !output.isEmpty() )
                System.out.println( "Saime JSON!" );
            System.out.println( "Protsess lõppes koodiga: " + exitCode );
            return new JSONObject( output );
        }
    }

    /**
     * The helper method builds, starts and returns Process (vnu.jar)
     * @param filesToValidate list of files that needs to be checked
     * @param vnu Nu validator jar file (vnu.jar)
     * @return Process of vnu.jar
     * @throws IOException
     */
    private static Process getVnuProcess(List<FileNode> filesToValidate, Path vnu) throws IOException {
        List<String> command = new ArrayList<>();
        command.add( "java" );
        command.add( "-jar" );
        command.add( vnu.toString() );
        command.add( "--format" );
        command.add( "json" );

        // Add all controllable files
        for( FileNode f : filesToValidate) {
            command.add( f.getPath().toString() );
        }

        // Start process
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        return pb.start();
    }

}
