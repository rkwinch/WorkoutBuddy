package finalproject;

/**
 *
 * Main entry point for WorkoutOfTheDay.  Parses Command Line Arguments
 * into a ProjectOptions, and instantiates a ProjectController with them.
 * @author Robin Ouzts 
 */
public class Main {
    /**
     *
     * @param args The list of CLI arguments.
     */
    public static void main(String args[]) {
        ProjectController controller = new ProjectController();

        ProjectOptions options;
        
        /* If the list of arguments is exactly 2, parse them for graphic
           and audio capability respectively.  Otherwise, default to 
           graphics-on, sound-on.
        */
        if (args.length != 2) { // default to having GUI and audio if incorrect # of 
                                // arguments given
            options = new ProjectOptions("graphical", true);
        } else { // parse commandline arguments for either textual or graphical for the user display
                 // and false or true for audio capability
            options = new ProjectOptions(args[0], Boolean.parseBoolean(args[1]));
        }

        controller.start(options);
    }
}
