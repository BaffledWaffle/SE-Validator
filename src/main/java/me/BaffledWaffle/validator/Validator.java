package me.BaffledWaffle.validator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Validator {

    public static void validate( String mode, String dir,  String vnuValidator, String cssValidator, String reportFile ) {

        // -- Paths --
        Path dirPath = getAbsolutePath( dir );
        Path vnuValidatorPath = getAbsolutePath( vnuValidator );
        Path cssValidatorPath = getAbsolutePath( cssValidator );
        if( reportFile == null || reportFile.isEmpty() )
            reportFile = "report.html";
        Path reportFilePath = getAbsolutePath( reportFile );

    }

    private static Path getAbsolutePath( String pathStr ) {
        Path path = Paths.get( pathStr );
        if( !path.isAbsolute() )
            return path.toAbsolutePath();
        return path;
    }

}
