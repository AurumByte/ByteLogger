import aurumbyte.bytelogger.ByteLogger;
import org.junit.jupiter.api.*;

public class LoggerTest {
  @Test
  private void loggerTestWithoutLoggingToFile(){
    ByteLogger LOGGER = new ByteLogger(false);
    LOGGER.info("Msg info boi");
    LOGGER.warn("Warning, This Logger is too awesome!");
    LOGGER.error("Logger cool with errors too");
    LOGGER.error(new Exception("Exception go brrrr"));
  }
}
