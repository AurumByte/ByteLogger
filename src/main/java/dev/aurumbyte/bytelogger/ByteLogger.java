package dev.aurumbyte.bytelogger;

import dev.aurumbyte.bytelogger.utils.Level;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static dev.aurumbyte.bytelogger.utils.LoggerColors.*;

public class ByteLogger {
    private boolean fileCreated;
    private Optional<Boolean> overwriteFiles;
    private File logFile;
    private boolean fileLog;
    private Optional<String> logFileName;
    private FileWriter fileWriter;
    private BufferedWriter writer;
    private Level level;

    public ByteLogger(Optional<String> logFileName, boolean fileLog, Level defaultLevel, Optional<Boolean> overwriteFiles){
        this.logFileName = logFileName;
        this.fileLog = fileLog;
        this.level = defaultLevel;

        if(logFileName.isEmpty()) logFileName = Optional.of("bytelog.txt");
        if(overwriteFiles.isPresent()) this.overwriteFiles = overwriteFiles;
        if(overwriteFiles.isEmpty()) overwriteFiles = Optional.of(false);

        logFile = new File(String.valueOf(logFileName));

        try {
            if(logFile.createNewFile()){
                fileCreated = true;
            } else {
                fileCreated = false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try {
            fileWriter = new FileWriter(logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer = new BufferedWriter(fileWriter);
    }

    public void info(Object msg) throws IOException {
        if(level.equals(Level.OFF)){
            return;
        }

        if(fileLog && fileCreated){
            writer.write("<Info> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else if (fileLog && !fileCreated && overwriteFiles.isPresent() && overwriteFiles.equals(true)) {
            writer.write("<Info> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else {
            this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
        }

        System.out.printf(CYAN + "<Info> => %s%n" + RESET, msg.toString());
    }

    public void warn(Object msg) throws IOException {
        if(level.equals(Level.OFF)){
            return;
        }

        if(fileLog && fileCreated){
            writer.write("<Warning> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else if (fileLog && !fileCreated && overwriteFiles.isPresent() && overwriteFiles.equals(true)) {
            writer.write("<Warning> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else {
            this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
        }

        System.out.printf(YELLOW + "<Warning> => %s%n" + RESET, msg.toString());
    }

    public void error(Object msg) throws IOException {
        if(level.equals(Level.OFF)){
            return;
        }

        if(fileLog && fileCreated){
            writer.write("<Error> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else if (fileLog && !fileCreated && overwriteFiles.isPresent() && overwriteFiles.equals(true)) {
            writer.write("<Error> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else {
            this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
        }

        System.out.printf(RED + "<Error> => %s%n" + RESET, msg.toString());
    }

    public void error(Exception e) throws IOException {
        if(level.equals(Level.OFF)){
            return;
        }

        if(fileLog && fileCreated){
            writer.write("<Error> => " + e.toString());
            writer.newLine();
        } else if (fileLog && !fileCreated && overwriteFiles.isPresent() && overwriteFiles.equals(true)) {
            writer.write("<Error> => " + e.toString());
            writer.newLine();
        } else {
            this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
        }

        if((level.equals(Level.DEBUG) || level.equals(Level.TRACE))){
            writer.write("    <Stack Trace> =>\n" + Arrays.toString(e.getStackTrace()));
            writer.newLine();
            writer.write("    <Cause> => " + e.getCause().toString());
            writer.newLine();
            writer.close();
            return;
        }

        System.out.printf(RED + "<Error> => %s%n" + RESET, e.toString());
        System.out.printf(RED + "    <Stack Trace> =>%n %s%n" + RESET, Arrays.toString(e.getStackTrace()));
        System.out.printf(BLUE + "    <Cause> => %s%n" + RESET, e.getCause().toString());
    }

    public void fatal(Object msg) throws IOException {
        if(level.equals(Level.OFF)){
            return;
        }

        if(fileLog && fileCreated){
            writer.write("<Fatal Error> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else if (fileLog && !fileCreated && overwriteFiles.isPresent() && overwriteFiles.equals(true)) {
            writer.write("<Fatal Error> => " + msg.toString());
            writer.newLine();
            writer.close();
            return;
        } else {
            this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
        }

        System.out.printf(RED + "<Error> => %s%n" + RESET, msg.toString());
    }

    public void fatal(Exception e) throws IOException {
        if(level.equals(Level.OFF)){
            return;
        }

        if(fileLog && fileCreated){
            writer.write("<Fatal Error> => " + e.toString());
            writer.newLine();
        } else if (fileLog && !fileCreated && overwriteFiles.isPresent() && overwriteFiles.equals(true)) {
            writer.write("<Fatal Error> => " + e.toString());
            writer.newLine();
        } else {
            this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
        }

        if((level.equals(Level.DEBUG) || level.equals(Level.TRACE))){
            writer.write("    <Stack Trace> =>\n" + Arrays.toString(e.getStackTrace()));
            writer.newLine();
            writer.write("    <Cause> => " + e.getCause().toString());
            writer.newLine();
            writer.close();
            return;
        }


        System.out.printf(RED + "<Fatal Error> => %s%n" + RESET, e.toString());
        System.out.printf(RED + "    <Stack Trace> =>%n %s%n" + RESET, Arrays.toString(e.getStackTrace()));
        System.out.printf(BLUE + "    <Cause> => %s%n" + RESET, e.getCause().toString());
    }


    public void logPermissions(){
        System.out.println("Logger Permissions:");
        System.out.println("    ");
        System.out.println("    Log File Name : " + logFileName);
        System.out.println("    New Log file created ? : " + (fileCreated ? "Yes" : "No"));
        System.out.println("    Log to file ? : " + (fileLog ? "Yes" : "No"));
        System.out.println("    Overwrite if file already exists ? : " + (overwriteFiles.isPresent() ? overwriteFiles.toString() : "Not specified (False by default)"));
    }

    public void runAs(Level level){
        level = level;
    }
}