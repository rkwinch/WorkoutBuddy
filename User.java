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
    public History userHistory;
    ArrayList<Integer> todayWorkout = new ArrayList<Integer>();
    ArrayList<Integer> prevWorkout = new ArrayList<Integer>();
    public User(){
        userHistory = new History();
        name = "";
        todayWorkout.clear(); // clears current contents of array list to ensure
                              // it is empty
    }// end of User constructor
    public void save(User name, ArrayList<Integer> todayWorkout, 
            ArrayList<Integer> prevWorkout) throws FileNotFoundException
    {
        for(int i = 0; i < name.todayWorkout.size(); i++)
        {
            name.prevWorkout.add(name.todayWorkout.get(i));
        }
        name.todayWorkout.clear();
        PrintWriter outFile = null;
        try
        {
        outFile = new PrintWriter("WorkoutOutput.txt");
        outFile.print(name.name);
        outFile.print(" " + name.prevWorkout);
        }
        finally
        {
            if(outFile != null)
            {
                outFile.close();
            }
        }
        System.out.println(name.prevWorkout);
    }
    public int workoutOption()
    {
        int option = 0;
        System.out.println("Please select your workout (1,2,3)");
        Scanner scanner = new Scanner(System.in);
        option = scanner.nextInt();
        System.out.println("You chose option " + option);
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
