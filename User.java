/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workout;
import java.util.*;
import java.io.*;
/**
 *
 * @author Robin
 */
public class User{

    public String name;
    public ArrayList<Integer> workoutOptionHistory;
    public ArrayList<String> dateHistory;
    
    public User(){ 
        name = "";
        workoutOptionHistory = new ArrayList<Integer>();
        dateHistory = new ArrayList<String>();
    }// end of User constructor
    
    public void save() throws FileNotFoundException
    {
        PrintWriter outFile = null;
        try
        {
        outFile = new PrintWriter("WorkoutOutput.txt");
        outFile.print(name);
        outFile.print(" " + workoutOptionHistory);
        outFile.print(" " + dateHistory);
        }
        finally
        {
            if(outFile != null)
            {
                outFile.close();
            }
        }
        //System.out.println(name.prevWorkout);
    }
    
    
    public int workoutOption()
    {
        int option = 0;
        System.out.println("Please select your workout (1,2,3)");
        Scanner scanner = new Scanner(System.in);
        option = scanner.nextInt();
        //System.out.println("You chose option " + option);
        while(option != 1 && option != 2 && option != 3)
        {
            System.out.println("You did not select a valid option");
            System.out.println("Please select your workout (1,2,3)");
            option = scanner.nextInt();
            System.out.println("You chose option " + option);
        }
        return option;
    }
    
}
