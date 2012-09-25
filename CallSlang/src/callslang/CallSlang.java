/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callslang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import slang4java.*;

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
        RDParser pars = null;
        pars = new RDParser(text.toString());
        TModule p = null;
        p = pars.DoParse();
        if (p == null) {
            System.out.println("Parse Process Failed");
            return;
        }
        RuntimeContext f = new RuntimeContext();
        SymbolInfo fp = p.Execute(f);    }

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
