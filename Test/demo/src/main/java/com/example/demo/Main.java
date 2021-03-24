package com.example.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private Main() {
    }

    public static void main(final String[] args) {
        logger.info("Message to log");
        try{
            Object obj = null;
            obj.hashCode();
            throwsException();
        }catch (Exception e){
            logger.error(" Error thrown" ,e ) ;
        }
        String s = "Hello world";
        try
        {
            Integer v = Integer.valueOf(s);
        }
        catch(NumberFormatException e)
        {
            logger.error("error",e);
        }
        try
        {
            Integer i = Integer.valueOf(s);
        }
        catch(NumberFormatException e)
        {
            logger.error("error",e);
        }

    }
    private static void throwsException() {
    }
}

