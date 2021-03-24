//import java.util.logging.Level;
//import java.util.logging.LogManager;
//import java.util.logging.Logger;
//
//public class GfG {
//    public static void main(String[] args)
//    {
//        DemoLogger obj = new DemoLogger();
//        //obj.makeSomeLog();
//
//        // Generating some log messages through the
//        // function defined above
//        LogManager lgmngr = LogManager.getLogManager();
//
//        // lgmngr now contains a reference to the log manager.
//        Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
//
//        // Getting the global application level logger
//        // from the Java Log Manager
//        log.log(Level.INFO, "This is a log message");
//
//        // Create a log message to be displayed
//        // The message has a level of Info
//    }
//}
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//public class SLF4JExample {
//    public static void main(String[] args) {
//        System.setProperty("slf4j.detectLoggerNameMismatch", "true");
//
//        //Creating the Logger object
//        Logger logger = LoggerFactory.getLogger(Sample.class);
//
//        //Logging the information
//        logger.info("Hi Welcome to Tutorilspoint");
//    }
//}