public class ChainOfResponsibilityDesignPattern {

    public static void main(String[] args) {
        LogProcessor logProcessor = new InfoLogProcessor(new DebugLogProcessor(new ErrorLogProcessor(null)));

        logProcessor.log(LogProcessor.DEBUG, "exception happens");
        logProcessor.log(LogProcessor.DEBUG, "need to debug this");
        logProcessor.log(LogProcessor.ERROR, "just for info");
    }
}

// Example Usage: ATM/ Vending Machine, Design logger
// Chain of Responsibility pattern is used to achieve loosely coupled applications. The client request is passed to chain of objects to process them. Then the object in the chain decides themselves on who will process the next request or do we need to process the next request or not.



// Amazon Question: Design Logging System
abstract class LogProcessor{

    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    LogProcessor nextLogProcessor;

    LogProcessor(LogProcessor nextLogProcessor){
        this.nextLogProcessor = nextLogProcessor;
    }

    public void log(int logLevel, String message){

        if(nextLogProcessor!=null){
            nextLogProcessor.log(logLevel,message);
        }
    }
}

class InfoLogProcessor extends LogProcessor{
    LogProcessor nextLogProcessor;

    InfoLogProcessor(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    @Override
    public void log(int logLevel, String message) {
        if(logLevel==INFO){
            System.out.println("INFO" + message);
        } else{
            super.log(logLevel,message);
        }
    }
}

class DebugLogProcessor extends LogProcessor {
    LogProcessor nextLogProcessor;

    DebugLogProcessor(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    @Override
    public void log(int logLevel, String message) {
        if(logLevel==DEBUG){
            System.out.println("DEBUG" + message);
        }else{
            super.log(logLevel,message);
        }
    }

}

class ErrorLogProcessor extends LogProcessor {
    LogProcessor nextLogProcessor;

    ErrorLogProcessor(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    @Override
    public void log(int logLevel, String message) {
        if(logLevel==ERROR){
            System.out.println("ERROR" + message);
        }else{
            super.log(logLevel,message);
        }
    }

}


