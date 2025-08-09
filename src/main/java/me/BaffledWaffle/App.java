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
        printHelp();
    }

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
