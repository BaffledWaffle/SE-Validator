package me.BaffledWaffle;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Program main class. Parses user input
 */
public class App
{

    // values by default
    private static Path input = Paths.get( "./projects" );
    private static Path output = Paths.get( "./report.html" );
    private static Path vnuValidator = Paths.get( "./vnu.jar" );
    private static Path cssValidator = Paths.get( "./css-validator.jar" );


    public static void main( String[] args )
    {

        // Args processing
        for( int i = 0; i < args.length; i++ ) {
            String arg = args[i];

            // help
            if( arg.equals( "--help" ) || arg.equals( "-h" ) || arg.equals( "help" ) ) {
                printHelp();
                return;
            }

            // Options
            // 1. arg is not on last position
            // 2. argument is flag - starts with hyphen
            // 3. next argument is not a flag
            if( i+1 < args.length && arg.startsWith( "-" ) && !args[ i+1 ].startsWith( "-" ) ) {

                String value = args[ i+1 ]; // flag value
                setValue( arg, value ); // rewrite default value
                i++; // go to the next args

            } else {
                System.out.println( "Lipp " + arg + " vajab väärtust." );
                System.out.println();
                printHelp();
            }

        }

        // Normalize all values
        normalizeValues();

    }

    /**
     * Normalizes all default/user values: brings to normalized absolute path
     */
    private static void normalizeValues() {
        input = input.toAbsolutePath().normalize();
        output = output.toAbsolutePath().normalize();
        vnuValidator = vnuValidator.toAbsolutePath().normalize();
        cssValidator = cssValidator.toAbsolutePath().normalize();
    }

    /**
     * Sets static methods values depending on flag value.
     * @param flag - flag
     * @param value - given flag value
     */
    private static void setValue( String flag, String value ) {
        switch (flag) {
            case "-i":
                input = Paths.get(value);
                break;
            case "-o":
                output = Paths.get(value);
                break;
            case "-vnu":
                vnuValidator = Paths.get(value);
                break;
            case "-css":
                cssValidator = Paths.get(value);
                break;
            default:
                System.out.println("Tundmatu lipp: " + flag);
                printHelp();
                break;
        }
    }

    /**
     * Prints help info
     */
    private static void printHelp() {
        System.out.println( "KASUTAMINE" );
        System.out.println( "   java -jar SE-Validator.jar [OPTS]" );
        System.out.println();
        System.out.println( "OPTSIOONID" );
        System.out.println( "   -i         [i]nput - määrab kataloogi valideeritavate projektidega (vaikimisi: " + input + ")" );
        System.out.println( "   -o         [o]utput - määrab aruande faili nimi (vaikimisi: " + output + ")" );
        System.out.println( "   -vnu       [vnu].jar - määrab Nu validaatori JAR faili (vaikimisi : " + vnuValidator + ")" );
        System.out.println( "   -css       [css]-validator.jar - määrab CSS validaatori faili (vaikimisi: " + cssValidator + ")" );
        System.out.println();
        System.out.println( "P.S." );
        System.out.println( "   Täpsem teave kasutamise ja valideerijate allalaadimise kohta on leitav aadressil: https://github.com/BaffledWaffle/SE-Validator"  );
    }
}
