package quinzical.frontend.helper;

// Java dependencies.
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.stream.Stream;

/**
 * This class is used to speak the text with Festival TTS. It also stores some of the configuration
 * such as speed of speaking etc.
 * @author Shrey Tailor, Jason Wang
 */
public class Speaker {
    private static Speaker speaker;

    private double SPEED;
    private Process process;
    private double DEFAULT = 1;
    private String speechString;
    private boolean isChanged = false;
    private ProcessBuilder processBuilder;

    /**
     * Getting the Speaker instance, by using Singleton pattern.
     */
    public static Speaker init() {
        if (speaker == null) {
            speaker = new Speaker();
        }
        return speaker;
    }

    /**
     * This method is used to set the speed of the speaker from the "Settings" screen of the game.
     * @param speed the new desired speed.
     */
    public void setSpeed(double speed) {
        SPEED = speed;
        isChanged = true;
    }

    /**
     * This method is used to speak the sentence which is currently in the configuration. It can be
     * set by using the {@link #setSpeech(String)} method. However, note that it stops the current
     * process before start to speak the new line.
     */
    public void speak () {
        kill();

        try {
            processBuilder = new ProcessBuilder("festival", "-b", "./.config/festival.scm");
            process = processBuilder.start();
        } catch (IOException error) {  };
    }

    /**
     * This method is used to stop the Speaker process from speaking.
     */
    public void kill() {
        try {
            // Finding about all the descendents, and deleting them all.
            Stream<ProcessHandle> descendents = process.descendants();
            descendents.filter(ProcessHandle::isAlive).forEach(processHandle -> {
                processHandle.destroy();
            });

            // Destroying the whole process after destroying the descendents.
            process.destroy();
        } catch (Exception error) {  };
    }

    /**
     * This method is used to reset the speed of the Speaker.
     */
    public void resetSpeed() {
        isChanged = false;
    }


    /**
     * This method is used to get the speed of the Speaker.
     * @return double the current speed.
     */
    public double getSpeed() {
        if (isChanged) {
            DecimalFormat df = new DecimalFormat("#.00");
            return Double.parseDouble(df.format(SPEED));
        }

        return DEFAULT;
    }

    /**
     * This method is used to set the speech of the Speaker. We are doing some extra processing to
     * remove the quotation marks in the string, which could potentially cause some issues later.
     * @param string the speech to speak.
     */
    public void setSpeech(String string) {
        speechString = string.replace("\"", "").replace("'", "");
        createSchematic();
    }

    /**
     * This method is a getter to check whether the speed has been changed.
     * @return boolean to check whether the speed has been changed previously.
     */
    public boolean isChanged() {
        return isChanged;
    }

    /**
     * This method is used to produce a schematic file (.scm) for the phrase which is going to be
     * spoken by the Festival TTS system. Within the contents of the file, we include some essential
     * parameters such as the speed selected by the user.
     */
    private void createSchematic() {
        try {
            Files.deleteIfExists(Paths.get("./.config/festival.scm"));

            // Create the file (and override if already exists).
            File schematicFile = new File("./.config/festival.scm");
            FileWriter fw = new FileWriter(schematicFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("(voice_akl_nz_jdt_diphone)\n");

            // Dynamically setting the speaker speed, by checking whether there is a custom speed set.
            if (isChanged) {
                bw.write("(Parameter.set 'Duration_Stretch " + 1/SPEED + ")\n");
            } else {
                bw.write("(Parameter.set 'Duration_Stretch " + 1/DEFAULT + ")\n");
            }

            // Creating the command to say the text.
            bw.write("(SayText " + "\"" + speechString + "\"" + ")");
            bw.close();
        } catch (IOException error) {  };
    }
}