package finalproject;

/**
 *
 * A simple class comprised mostly of data to represent if the project
 * should utilize audio and which type of View should be used.  Generally
 * intended to be parsed from CLI arguments, but not limited to such.
 * @author Robin Ouzts 
 */
public class ProjectOptions {
    /**
     *
     * A flag indicating if audio should be enabled (true=yes).
     */
    private final Boolean audio_enabled; // flag for audio capability
    
    /**
     *
     * A flag indicating if graphics are to be utilized (true=yes).
     */
    private final Boolean graphics_enabled; // flag for GUI interface

    /**
     *
     * Constructs a ProjectOptions.
     * @param graphics_type A string representing the type of graphics
     * to be used.  Currently only "graphical" is supported; otherwise
     * defaults graphics_enabled to false.
     * @param audio_enabled A Boolean for representing if audio is
     * enabled.
     */
    ProjectOptions(String graphics_type, Boolean audio_enabled) {
        graphics_enabled = "graphical".equals(graphics_type); // will have GUI
                     //if user entered "graphical" or if the arguments weren't equal to 2 in number
        this.audio_enabled = audio_enabled;
    }

    /**
     *
     * Getter for audio_enabled.
     * @return The value of audio_enabled.
     */
    Boolean getAudioEnabled() {
        return audio_enabled;
    }
    
    /**
     *
     * Getter for the display type.
     * @return A string of "graphical" if graphics are enabled; "textual"
     * otherwise.
     */
    String getDisplayType() {
        if (graphics_enabled) {
            return "graphical";
        }
        return "textual";
    }
}
