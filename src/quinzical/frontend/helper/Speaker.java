package quinzical.frontend.helper;

// Java dependencies.
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.util.stream.Stream;

/**
 * This class is used to speak the text with Festival TTS. It also stores some of the configuration
 * such as speed of speaking which is volatile.
 * @author Shrey Tailor, Jason Wang
 */
public class Speaker {
    // Static field(s).
    private static Speaker speaker;

    // Non-static field(s).
    private double SPEED;
    private Process process;
    private double DEFAULT = 1;
    private String speechString;
    private boolean isChanged = false;
    private ProcessBuilder processBuilder;
    private boolean nzVoicesInstalled = false;

    /**
     * This is the only constructor of the Speaker class.
     */
    public Speaker() {
        if (Files.isDirectory(Paths.get("/usr/share/festival/voices/english/akl_nz_jdt_diphone"))) {
            // Checking whether the NZ voices are installed.
            nzVoicesInstalled = true;
        }
    }

    /**
     * This method uses the singleton pattern to initialize the Speaker object.
     */
    public static Speaker init() {
        if (speaker == null) {
            speaker = new Speaker();
        }
        return speaker;
    }

    /**
     * This method is used to set the speed of the speaker.
     * @param speed the desired speed of the user.
     */
    public void setSpeed(double speed) {
        SPEED = speed;
        isChanged = true;
    }

    /**
     * This method is used to speak the defined sentence. It can be set by using the
     * {@link #setSpeech(String)} method.
     */
    public void speak () {
        // Stopping the current process, before speaking again.
        kill();
        try {
            processBuilder = new ProcessBuilder("festival", "-b", "./.config/festival.scm");
            process = processBuilder.start();
        } catch (IOException error) {
            // This can be ignored because the process will always be found.
        };
    }

    /**
     * This method is used to terminate the current process which is running.
     */
    public void kill() {
        try {
            // Terminating all descending processes.
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
     * This method is used to get the current speed of the Speaker.
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
     * This method is used to set the speech of the Speaker.
     * @param string the speech to speak.
     */
    public void setSpeech(String string) {
        // Removing the extra quotation marks from the given string.
        speechString = string.replace("\"", "").replace("'", "");
        createSchematic();
    }

    /**
     * This method is used to produce a schematic file (.scm) for the phrase which is going to be
     * spoken by the TTS system. Within the contents of the file, we include some essential parameters
     * such as the speed selected by the user.
     */
    private void createSchematic() {
        try {
            Files.deleteIfExists(Paths.get("./.config/festival.scm"));

            // Create the file (and override if already exists).
            File schematicFile = new File("./.config/festival.scm");
            FileWriter fw = new FileWriter(schematicFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // We are only using the New Zealand voice, if the user has it installed.
            if (nzVoicesInstalled) {
                bw.write("(voice_akl_nz_jdt_diphone)\n");
            }

            // Dynamically setting the speaker speed, by checking whether there is a custom speed.
            if (isChanged) {
                bw.write("(Parameter.set 'Duration_Stretch " + 1/SPEED + ")\n");
            } else {
                bw.write("(Parameter.set 'Duration_Stretch " + 1/DEFAULT + ")\n");
            }

            bw.write("(SayText " + "\"" + speechString + "\"" + ")");
            bw.close();
        } catch (IOException error) {
            /*
            This can be ignored, because if the schematic file doesn't exist, then the file will not
            be deleted anyways so it's safe.
             */
        };
    }

    public boolean isChanged() {
        return isChanged;
    }
}