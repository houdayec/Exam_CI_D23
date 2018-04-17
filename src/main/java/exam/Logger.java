package exam;


import org.apache.log4j.LogManager;

public class Logger {

    private static Logger logger;
    public static org.apache.log4j.Logger log;

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
            log = LogManager.getLogger(Logger.class);
        }
        return logger;
    }





}
