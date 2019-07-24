package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoggerState {

    private static File log = null;
    private static PrintWriter pw = null;
    private static FileWriter fileWriter = null;
    private static String toWrite = "My Log File:\n";

    public static void writeFile(String s) {
        if(log == null) {
            log = new File("src/server/src/main/resources/static/thisLog.log");
            try {
                fileWriter = new FileWriter(log);
                pw = new PrintWriter(fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        toWrite += s + "\n";
    }
    public static void close() {
        pw.print(toWrite);
        pw.close();
    }
}
