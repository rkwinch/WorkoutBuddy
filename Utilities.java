
package workout;

import java.io.IOException;
import java.util.ArrayList;
import java.time.Duration;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;


public class Utilities {

    public static ArrayList<String> dateHistory;
    public static long time;
    
    
    public Utilities() {
        
    }// end of Utilities constructor

    public static void ShowTime() {
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
                PrintTime(minutes, seconds, deciseconds);
            }
            try 
            {
                if (System.in.available() > 0) 
                {
                    input = scanner.nextLine();
                    System.out.println("Elapsed time is:  ");
                    PrintTime(minutes, seconds, deciseconds);
                    break;
                }
            } 
            catch (IOException error) 
            {
                System.out.println(error.getMessage());
            }
            prevDeciseconds = deciseconds;
        }
    }//end of ShowTime fxn

    public static void PrintMinutesIfGreaterThanZero(long minutes) 
    {
        if (minutes == 0) 
        {
            System.out.print("");
        } 
        else 
        {
            System.out.print(minutes + "min ");
        }
    }//end of PrintMinutesIfGreaterThanZero fxn

    public static void PrintSecondsIfGreaterThanZero(long seconds) 
    {
        System.out.print(seconds);  
    }//end of printSecondsIfGreaterThanZero fxn

    public static void PrintDeciseconds(long deciseconds) 
    {
        System.out.println("." + deciseconds + "s");
    }
    
    public static void PrintTime(long minutes, long seconds, 
            long deciseconds)
    {
        PrintMinutesIfGreaterThanZero(minutes);
        PrintSecondsIfGreaterThanZero(seconds);
        PrintDeciseconds(deciseconds);
    }// end of PrintTime fxn
    
    public static String StringDate()
    {
        Date date = new Date();
        String currentDate = String.format("%tm%<td%<tY", date);
        return currentDate;
    }
    
    public static int GenerateRandomNumberOneToTwo()
    {
      Random rand = new Random();
      int randomNumber = rand.nextInt(2) + 1; // to get # between 1-2
      return randomNumber;
    }
    
    public static int GenerateRandomNumberOneToFour()
    {
        Random rand = new Random();
        int randomNumber = rand.nextInt(4) + 1; // to get # between 1-4
        return randomNumber;
    }

}//end of Utilities Class
