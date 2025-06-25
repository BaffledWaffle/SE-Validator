package me.BaffledWaffle;

import me.BaffledWaffle.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Application main class.
 */
public class App  {

    /**
     * Application's main method. Gets and checks flags
     * @param args console arguments
     */
    public static void main( String[] args ) {

        try {
            Map<String, String> arguments = new HashMap<>();
            String mode = "";

            if( args.length == 0 ) {
                printHelp();
                return;
            }

            // Get flags values
            for( int i = 0; i < args.length; i++ ) {
                String arg  = args[i];
                if( arg.equals( "-o" ) || arg.equals( "-s" ) ) {
                    mode = arg.substring(1);
                } else if ( arg.startsWith( "-" ) ) {
                    if( i+1 < args.length && !args[i+1].startsWith( "-" ) ) {
                        arguments.put( arg, args[i+1] );
                        i++;
                    } else {
                        throw new IllegalArgumentException( "Lipp " + arg + " vajab väärtust!" );
                    }
                }
            }

            String dirPath = arguments.get( "-d" );
            String vnuPath = arguments.get( "-vnu" );
            String cssPath = arguments.get( "-css" );
            String reportPath = arguments.get( "-out" );

            if( mode.isEmpty() )
                throw new IllegalArgumentException( "Valideerimise viis pole määratud!" );

            if( dirPath == null || vnuPath == null || cssPath == null )
                throw new IllegalArgumentException( "Kohustuslik arguments on määramata!" );


            Validator.validateAndGenerateReport( mode, dirPath, vnuPath, cssPath, reportPath );

        } catch ( IllegalArgumentException e ) {
            System.out.println( e.getMessage() );
            printHelp();
        }
    }

    /**
     * The method prints usage info
     */
    private static void printHelp() {
        System.out.println("Kasutamine: java -jar SE-Validator.jar [-o | -s] -d <projekti(de) kaust> -vnu <vnu.jar> -css <css-validator.jar> [-out <report.html>]");
        System.out.println("    [-o | -s]                Valideerimise viis");
        System.out.println("                                -o - üks projekt");
        System.out.println("                                -s - mitu projekti korraga");
        System.out.println("    -d <kaust>               Kaust projekti(de)ga");
        System.out.println("    -vnu <failinimi.jar>     Nu validaatrori failinimi");
        System.out.println("    -css <failinimi.jar>     CSS validaatori failinimi");
        System.out.println("    -out <failinimi.html>    (valik.) tulemuse faili failitee (vaikimisi report.html)");
    }
}
