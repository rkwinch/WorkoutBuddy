
package workout;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;


public class Workout {

    
    public static void main(String[] args) throws FileNotFoundException{
        
        User Robin = new User();
        Robin.name = "Robin";
        //Robin.userHistory.showTime();
        Robin.dateHistory.add(Utilities.stringDate());
        Robin.dateHistory.add("11012019");
        Robin.workoutOptionHistory.add(1);
        Robin.workoutOptionHistory.add(Robin.workoutOption());
        System.out.println("workout option history:  " + Robin.workoutOptionHistory);
        System.out.println("dates:  " + Robin.dateHistory);
        try
        {
        Robin.save();
        }
        catch (FileNotFoundException error)
        {
            System.out.println("Could not save file.");
        } 
    }
    
    public static void option1()
    {
        //use later!!
    }
    public static void option2()
    {
        //use later!!
    }
    public static void option3()
    {
        //use later!!
    }
}

