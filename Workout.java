
package workout;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Workout {

    
    public static void main(String[] args) throws FileNotFoundException{
        User Robin = new User();
        Robin.name = "Robin";
        //Robin.userHistory.showTime();
        
        Robin.prevWorkout.add(1);
        Robin.todayWorkout.add(Robin.workoutOption());
        System.out.println("today's workout option:  " + Robin.todayWorkout);
        try
        {
        Robin.save(Robin, Robin.todayWorkout, Robin.prevWorkout);
        }
        catch (FileNotFoundException error)
        {
            System.out.println("Could not save file.");
        }
        //System.out.println(Robin.todayWorkout);
     
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

