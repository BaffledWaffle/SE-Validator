package me.BaffledWaffle.validator;

import java.nio.file.Path;
import java.nio.file.Paths;

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
    public static void validateAndGenerateReport( String mode, String dir,  String vnuValidator, String cssValidator, String reportFile ) {

        // -- Paths --
        Path dirPath = getAbsolutePath( dir );
        Path vnuValidatorPath = getAbsolutePath( vnuValidator );
        Path cssValidatorPath = getAbsolutePath( cssValidator );
        if( reportFile == null || reportFile.isEmpty() )
            reportFile = "report.html";
        Path reportFilePath = getAbsolutePath( reportFile );

    }

    /**
     * The method finds file/dir absolute path
     * @return absolute path
     */
    private static Path getAbsolutePath( String pathStr ) {
        Path path = Paths.get( pathStr );
        if( !path.isAbsolute() )
            return path.toAbsolutePath();
        return path;
    }

}
