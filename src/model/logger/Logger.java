package model.logger;

import java.io.*;

public class Logger {

    private static final String FILE_PATH_LOG = "./log.txt";
    private static final boolean WRITE_MODE_APPEND = true;

    private String _classToLogName;

    public Logger(String classToLogName) {
        _classToLogName = classToLogName;
    }

    public void log(String logMessage) {
        staticLog(_classToLogName + ": " + logMessage);
    }

    private static synchronized void staticLog(String loggingString) {

        System.out.println(loggingString);

        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH_LOG, WRITE_MODE_APPEND)))) {
            out.println(loggingString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
