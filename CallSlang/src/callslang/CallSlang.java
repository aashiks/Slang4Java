/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callslang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import slang4java.compilationunits.TModule;
import slang4java.contexts.RuntimeContext;
import slang4java.generators.JSGenerator;
import slang4java.lexer.RDParser;
import slang4java.metainfo.SymbolInfo;

/**
 *
 * @author aashiks
 */
public class CallSlang {

    static void TestFileScript(String filename) throws FileNotFoundException, Exception {
        if (filename == null) {
            return;
        }
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = new Scanner(new FileInputStream(filename));
        try {
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine() + NL);
            }
        } finally {
            scanner.close();
        }


        //---------------- Creates the Parser Object
        // With Program text as argument 
        RDParser pars = new RDParser(text.toString());
        TModule p = null;
        p = pars.DoParse();
        if (p == null) {
            System.out.println("Parse Process Failed");
            return;
        }
        JSGenerator js = new JSGenerator();
        
        String jsString = p.Generate(js);
        System.out.print(jsString);
        // Okay the program has been parsed, which means its executable.
        
        RuntimeContext f = new RuntimeContext(p);
        SymbolInfo fp = p.Execute(f, null)  ;
    }

    public static void main(String[] args) throws IOException {
        try {
            if (args == null
                    || args.length != 1) {
                System.out.println("CallSlang <scriptname>\n");
                return;

            }
            TestFileScript(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
                 System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.in.read();
    }
}
