
package workout;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;



public class Workout {

    
    public static void main(String[] args) throws FileNotFoundException{
        
        User Robin = new User();
        Robin.name = "Robin";
//        Utilities.ShowTime();
//        Robin.dateHistory.add(Utilities.StringDate());
//        Robin.dateHistory.add("11012019");
//        Robin.workoutOptionHistory.add(1);
//        Robin.workoutOptionHistory.add(Robin.WorkoutOption());
//        System.out.println("workout option history:  " + Robin.workoutOptionHistory);
//        System.out.println("dates:  " + Robin.dateHistory);
        try
        {
        Robin.Save();
        }
        catch (FileNotFoundException error)
        {
            System.out.println("Could not save file.");
        }
        for(int i = 0; i < 100; i++)
        {
        //System.out.println("1-2:  " + Utilities.generateRandomNumberOneToTwo());
        //System.out.println("1-4:  " + Utilities.generateRandomNumberOneToFour());
        //EasyLowerRandomSelection();
        //EasyUpperRandomSelection();
        //EasyCardioRandomSelection();
        //AdvancedCardioRandomSelection();
        //AdvancedUpperRandomSelection();
        //AdvancedLowerRandomSelection();
        }
    }
    
    public static void EasyCardioRandomSelection()
    {
        int selection = Utilities.GenerateRandomNumberOneToTwo();
        switch(selection)
        {
            case 1:
                System.out.println("Time to do SIT-UPS!");
                break;
            case 2:
                System.out.println("Let's do JUMPING JACKS!");
                break;
            default:
                //throw invalid selection...to be implemented later
        }
    }
    public static void EasyUpperRandomSelection()
    {
        int selection = Utilities.GenerateRandomNumberOneToTwo();
        switch(selection)
        {
            case 1:
                System.out.println("Time to do PUSH-UPS!");
                break;
            case 2:
                System.out.println("Let's do PLANKS!");
                break;
            default:
                //throw invalid selection...to be implemented later
        }    }
    public static void EasyLowerRandomSelection()
    {
        int selection = Utilities.GenerateRandomNumberOneToTwo();
        switch(selection)
        {
            case 1:
                System.out.println("Time to do LUNGES!");
                break;
            case 2:
                System.out.println("Let's do SQUATS!");
                break;
            default:
                //throw invalid selection...to be implemented later
        }
    }
    public static void AdvancedCardioRandomSelection()
    {
        int selection = Utilities.GenerateRandomNumberOneToFour();
        switch(selection)
        {
            case 1:
                System.out.println("Time to do SIT-UPS!");
                break;
            case 2:
                System.out.println("Let's do JUMPING JACKS!");
                break;
            case 3:
                System.out.println("Time to do HIGH KNEES!");
                break;
            case 4:
                System.out.println("Let's do BURPEES!");
                break;
            default:
                //throw invalid selection...to be implemented later
        }
    }
    public static void AdvancedLowerRandomSelection()
    {
        int selection = Utilities.GenerateRandomNumberOneToFour();
        switch(selection)
        {
            case 1:
                System.out.println("Time to do SQUATS!");
                break;
            case 2:
                System.out.println("Let's do LUNGES!");
                break;
            case 3:
                System.out.println("Time to do SQUAT JUMPS!");
                break;
            case 4:
                System.out.println("Let's do BEAR CRAWLS!");
                break;
            default:
                //throw invalid selection...to be implemented later
        }
    }
    public static void AdvancedUpperRandomSelection()
    {
        int selection = Utilities.GenerateRandomNumberOneToFour();
        switch(selection)
        {
            case 1:
                System.out.println("Time to do WIDE GRIP PUSH-UPS!");
                break;
            case 2:
                System.out.println("Let's do CLOSE GRIP PUSH-UPS!");
                break;
            case 3:
                System.out.println("Time to do PLANKS!");
                break;
            case 4:
                System.out.println("Let's do HAND RELEASE PUSH-UPS!");
                break;
            default:
                //throw invalid selection...to be implemented later
        }
    }

} // end of Workout Class

