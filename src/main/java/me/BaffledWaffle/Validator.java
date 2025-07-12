package me.BaffledWaffle;

import me.BaffledWaffle.report.FileNode;
import me.BaffledWaffle.report.FileTreeBuilder;
import me.BaffledWaffle.report.Report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator class. Contains static methods for validation and generating report file
 */
public class Validator {

    /**
     * Validates HTML/CSS files for project(s) and generates HTML report file
     * @param mode validating mode (one/some projects)
     * @param dir directory with project(s)
     * @param vnuValidator Nu validator .jar path
     * @param cssValidator CSS validator .jar path
     * @param reportFile Report file path (report.html by default)
     */
    public static void validateAndGenerateReport( String mode, String dir,  String vnuValidator, String cssValidator, String reportFile ) throws IOException {

        // -- Paths --
        Path dirPath = getAbsolutePathIfExists( dir );
        Path vnuValidatorPath = getAbsolutePathIfExists( vnuValidator );
        Path cssValidatorPath = getAbsolutePathIfExists( cssValidator );
        if( reportFile == null || reportFile.isEmpty() )
            reportFile = "report.html";
        Path reportFilePath = getAbsolutePath( reportFile );

        List<Report> reports = new ArrayList<>();

        if( mode.equals( "o" ) ) {
            Report report = getReport( dirPath, vnuValidatorPath, cssValidatorPath );
            reports.add( report );
        } else if ( mode.equals( "s" ) ) {
            reports = getReports( dirPath, vnuValidatorPath, cssValidatorPath );
        }

    }

    /**
     * The method returns one report by project directory
     * @param dirPath project directory
     * @param vnuPath Nu validator jar file path
     * @param cssPath CSS validator jar file path
     * @return single project's report
     */
    private static Report getReport( Path dirPath, Path vnuPath, Path cssPath) {
        return null;
    }

    /**
     * The method returns reports by directory with projects
     * @param dirPath project directory
     * @param vnuPath Nu validator jar file path
     * @param cssPath CSS validator jar file path
     * @return all projects' reports
     */
    private static List<Report> getReports( Path dirPath, Path vnuPath, Path cssPath ) throws IOException {

        return null;
    }

    /**
     * The method finds file/dir absolute path. throws NoSuchFileException if file or directory doesn't exist
     * @return absolute path
     */
    private static Path getAbsolutePathIfExists( String pathStr ) throws NoSuchFileException {
        Path path = getAbsolutePath( pathStr );

        if( !Files.exists( path ) )
            throw new NoSuchFileException( "Sellist faili/kausta ei eksisteeri! - " + path);

        return path;
    }


    /**
     * The method finds file/dir absolute path
     * @return absolute path
     */
    private static Path getAbsolutePath( String pathStr ) {

        Path path = Paths.get( pathStr );
        if( !path.isAbsolute() )
            path = path.toAbsolutePath().normalize();

        return path;
    }

}
