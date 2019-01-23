
package workout;

import java.io.IOException;
import java.util.ArrayList;
import java.time.Duration;
import java.util.Scanner;


public class History {

    public ArrayList<Integer> day;
    public ArrayList<Integer> month;
    public ArrayList<Integer> year;
    public long time;
    
    public History() {
        day = new ArrayList<Integer>();
        month = new ArrayList<Integer>();
        year = new ArrayList<Integer>();

    }// end of History constructor

    public void showTime() {
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

    public void printMinutesIfGreaterThanZero(long minutes) 
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

    public void printSecondsIfGreaterThanZero(long seconds) 
    {
        System.out.print(seconds);  
    }//end of printSecondsIfGreaterThanZero fxn

    public void printDeciseconds(long deciseconds) 
    {
        System.out.println("." + deciseconds + "s");
    }
    
    public void printTime(long minutes, long seconds, 
            long deciseconds)
    {
        printMinutesIfGreaterThanZero(minutes);
        printSecondsIfGreaterThanZero(seconds);
        printDeciseconds(deciseconds);
    }// end of printTime fxn

}//end of History Class
