
package workout;

import java.io.IOException;
import java.util.ArrayList;
import java.time.Duration;
import java.util.Date;
import java.util.Scanner;


public class Utilities {

    public static ArrayList<String> dateHistory;
    public static long time;
    
    
    public Utilities() {
        
    }// end of History constructor

    public static void showTime() {
        String input;
        System.out.println("Please hit Enter to start the time.");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        long initialTime = System.nanoTime();
        long elapsedTime;
        long deciseconds;
        long seconds;
        long minutes;
        long prevDeciseconds = -1;
        while (input.equals("")) 
        {
            time = System.nanoTime();
            elapsedTime = time - initialTime;
            Duration convertedDuration = Duration.ofNanos(elapsedTime);
            deciseconds = ((long) (convertedDuration.toMillis()) - 
                    (long) (convertedDuration.getSeconds()) * 1000) / 100;
            seconds = (long) convertedDuration.getSeconds() - 
                    (long) convertedDuration.toMinutes() * 60;
            minutes = (long) convertedDuration.toMinutes();
            if(deciseconds == prevDeciseconds)
            {
                System.out.print("");
            }
            else
            {
                printTime(minutes, seconds, deciseconds);
            }
            try 
            {
                if (System.in.available() > 0) 
                {
                    input = scanner.nextLine();
                    System.out.println("Elapsed time is:  ");
                    printTime(minutes, seconds, deciseconds);
                    break;
                }
            } 
            catch (IOException error) 
            {
                System.out.println(error.getMessage());
            }
            prevDeciseconds = deciseconds;
        }
    }//end of showTime fxn

    public static void printMinutesIfGreaterThanZero(long minutes) 
    {
        if (minutes == 0) 
        {
            System.out.print("");
        } 
        else 
        {
            System.out.print(minutes + "min ");
        }
    }//end of printMinutesIfGreaterThanZero fxn

    public static void printSecondsIfGreaterThanZero(long seconds) 
    {
        System.out.print(seconds);  
    }//end of printSecondsIfGreaterThanZero fxn

    public static void printDeciseconds(long deciseconds) 
    {
        System.out.println("." + deciseconds + "s");
    }
    
    public static void printTime(long minutes, long seconds, 
            long deciseconds)
    {
        printMinutesIfGreaterThanZero(minutes);
        printSecondsIfGreaterThanZero(seconds);
        printDeciseconds(deciseconds);
    }// end of printTime fxn
    
    public static String stringDate()
    {
        Date date = new Date();
        String currentDate = String.format("%tm%<td%<tY", date);
        return currentDate;
    }

}//end of History Class
