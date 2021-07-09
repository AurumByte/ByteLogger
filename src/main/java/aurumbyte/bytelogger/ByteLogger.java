import java.util.Optional;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.lang.Exception;
import java.util.Arrays;
import java.io.File;
import java.lang.Boolean;

import static aurumbyte.bytelogger.utils.LoggerColors;

public class ByteLogger {
  private boolean fileCreated;
  private Optional<Boolean> overwriteFiles;
  private File logFile;
  private boolean fileLog;
  private Optional<String> logFileName;
  private FileWriter fileWriter;
  private BufferedWriter writer;

  public ByteLogger(Optional<String> logFileName, boolean fileLog, Optional<Boolean> overwriteFiles){
    this.logFileName = logFileName;
    this.fileLog = fileLog;

    if(logFileName.isEmpty()) logFileName = "bytelog.txt";
    if(overwriteFiles.isPresent()) this.overwriteFiles = overwriteFiles;

    logFile = new File(logFileName);

    fileWriter = new FileWriter(logFile);
    writer = new BufferedWriter(fileWriter);
  }

  try {
    if(logFile.createNewFile()){
      fileCreated = true;
    } else {
      fileCreated = false;
    }
  } catch (Exception e) {
    this.error(e);
  }

  public void info(Object msg){
    if(fileLog && fileCreated){
      writer.write("<Info> => " + msg.toString());
      writer.newLine();
      writer.close();
      return;
    } else if (fileLog && !fileCreated && overwriteFiles) {
      writer.write("<Info> => " + msg.toString());
      writer.newLine();
      writer.close();
      return;
    } else {
      this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
    }

    System.out.printf(CYAN + "<Info> => %s%n" + RESET, msg.toString());
  }

  public void warn(Object msg){
    if(fileLog && fileCreated){
      writer.write("<Warning> => " + msg.toString());
      writer.newLine();
      writer.close();
      return;
    } else if (fileLog && !fileCreated && overwriteFiles) {
      writer.write("<Warning> => " + msg.toString());
      writer.newLine();
      writer.close();
      return;
    } else {
      this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
    }

    System.out.printf(YELLOW + "<Warning> => %s%n" + RESET, msg.toString());
  }

  public void error(Object msg){
    if(fileLog && fileCreated){
      writer.write("<Error> => " + msg.toString());
      writer.newLine();
      writer.close();
      return;
    } else if (fileLog && !fileCreated && overwriteFiles) {
      writer.write("<Error> => " + msg.toString());
      writer.newLine();
      writer.close();
      return;
    } else {
      this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
    }

    System.out.printf(RED + "<Error> => %s%n" + RESET, msg.toString());
  }

  public void error(Exception e){
    if(fileLog && fileCreated){
      writer.write("<Error> => " + e.toString());
      writer.newLine();
      writer.write("    <Stack Trace> =>\n" + Arrays.toString(e.getSgetStackTrace()));
      writer.newLine();
      writer.write("    <Cause> => " + e.getCause().toString());
      writer.newLine();
      writer.close();
      return;
    } else if (fileLog && !fileCreated && overwriteFiles) {
      writer.write("<Error> => " + e.toString());
      writer.newLine();
      writer.write("    <Stack Trace> =>\n" + Arrays.toString(e.getSgetStackTrace()));
      writer.newLine();
      writer.write("    <Cause> => " + e.getCause().toString());
      writer.newLine();
      writer.close();
      return;
    } else {
      this.error("File error: Either log file doesn't exist or you haven't provided the permissions to over write existing files");
    }

    System.out.printf(RED + "<Error> => %s%n" + RESET, e.toString());
    System.out.printf(RED + "    <Stack Trace> =>%n %s%n" + RESET, Arrays.toString(e.getStackTrace()));
    System.out.printf(BLUE + "    <Cause> => %s%n" + RESET, e.getCause().toString());
  }

  public void logPermissions(){
    System.out.println("Logger Permissions:");
    System.out.println("    Log File Name : " + logFileName);
    System.out.println("    New Log file created ? : " + (fileCreated ? "Yes" : "No"));
    System.out.println("    Log to file ? : " + (fileLog ? "Yes" : "No"));
    System.out.println("    Overwrite if file already exists ? : " + (overwriteFiles.isPresent() ? overwriteFiles.toString() : "Not specified (False by default)"));
  }
}
