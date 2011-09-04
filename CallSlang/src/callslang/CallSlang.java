/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callslang;

import java.io.IOException;
import java.util.ArrayList;
import slang4java.*;

/**
 *
 * @author aashiks
 */
public class CallSlang {

    /**
     * @param args the command line arguments
     */
    static void TestFirstScript() throws Exception {
        String a = "PRINTLINE 2*10;" + "\r\n" + "PRINTLINE 10;\r\n PRINT 2*10;\r\n";
        RDParser p = new RDParser(a);
        ArrayList arr = p.Parse();
        for (Object obj : arr) {
            Statement s = (Statement) obj;
            s.Execute(null);
        }
    }
    /// <summary>
    /// 
    /// </summary>

    static void TestSecondScript() throws Exception {
        String a = "PRINTLINE -2*10;" + "\r\n" + "PRINTLINE -10*-1;\r\n PRINT 2*10;\r\n";
        RDParser p = new RDParser(a);
        ArrayList arr = p.Parse();
        for (Object obj : arr) {
            Statement s = (Statement) obj;
            s.Execute(null);
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            TestFirstScript();
            TestSecondScript();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.in.read();
    }
}
