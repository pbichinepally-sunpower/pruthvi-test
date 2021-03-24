import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
    public static void main(String[] args) {
        //Creating the Logger object
        Logger logger = LoggerFactory.getLogger("SampleLogger");
//        org.apache.log4j.Logger.getRootLogger().addAppender(new ConsoleAppender());
//        Logger logger = org.slf4j.LoggerFactory.getLogger(TestBase.class);
        logger.info("Hello World");

        //Logging the information
        logger.info("Hi This is my first SLF4J program");

        try{
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[10]);
            throwsException();
        }catch (Exception e){
            logger.error("error", e);

        }
    }

    private static void throwsException() {
    }
}