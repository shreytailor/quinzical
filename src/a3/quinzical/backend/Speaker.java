package a3.quinzical.backend;

// Java dependencies.
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.util.stream.Stream;
import java.io.OutputStreamWriter;

/**
 * This class is used to speak the text with Festival TTS. It also stores some of the configuration
 * such as speed of speaking etc.
 * @author Shrey Tailor, Jason Wang
 */
public class Speaker {
    private static Speaker _speaker;

    private double SPEED;
    private Process _process;
    private double DEFAULT = 1;
    private String _speechString;
    private boolean _isChanged = false;
    private ProcessBuilder _processBuilder;

    /**
     * Getting the Speaker instance, by using singleton pattern.
     */
    public static Speaker init() {
        if (_speaker == null) {
            _speaker = new Speaker();
        }
        return _speaker;
    }

    /**
     * This method is used to set the speed of the speaker from the "Settings" of the game.
     * @param speed the new desired speed
     * @throws IOException this exception is thrown if the desired speed is outside the desired
     * range of 0.5 to 2.5 (inclusive).
     */
    public void setSpeed(double speed) throws IOException {
        if (speed < 0.5 || speed > 2.5) {
            throw new IOException();
        }

        SPEED = speed;
        _isChanged = true;
    }

    /**
     * This method is used to speak the sentence which is currently in the configuration. It can be
     * set by using the {@link #setSpeech(String)} method. However, note that it stops the current
     * process before start to speak the new line.
     */
    public void speak () {
        kill();

        // Using the custom NZ male accent.
        String accentCommand = "(voice_akl_nz_cw_cg_cg)";

        // Dynamically setting the speaker speed, by checking whether there is a custom speed set.
        String speedCommand;
        if (_isChanged) {
            speedCommand = "(Parameter.set 'Duration_Stretch " + 1/SPEED + ")";
        } else {
            speedCommand = "(Parameter.set 'Duration_Stretch " + 1/DEFAULT + ")";
        }

        // Creating the command to say the text.
        String speakingCommand = "(SayText " + "\"" + _speechString + "\"" + ")";


        try {
            _processBuilder = new ProcessBuilder("festival");
            _process = _processBuilder.start();

            OutputStream stdin = _process.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

            // Using all the created commands, and inputting that to the process.
            writer.write(accentCommand);
            writer.write(speedCommand);
            writer.write(speakingCommand);
            writer.close();
        } catch (IOException error) {  };
    }

    /**
     * This method is used to stop the Speaker process from speaking.
     */
    public void kill() {
        try {
            Stream<ProcessHandle> descendents = _process.descendants();
            descendents.filter(ProcessHandle::isAlive).forEach(ph -> {
                ph.destroy();
            });
        } catch (Exception error) {  };
    }

    /**
     * This method is used to reset the speed of the Speaker.
     */
    public void resetSpeed() {
        _isChanged = false;
    }


    /**
     * This method is used to get the speed of the Speaker.
     * @return double the current speed.
     */
    public double getSpeed() {
        if (_isChanged) {
            DecimalFormat df = new DecimalFormat("#.00");
            return Double.valueOf(df.format(SPEED));
        }

        return DEFAULT;
    }

    /**
     * This method is used to set the speech of the Speaker.
     * @param string the speech to speak.
     */
    public void setSpeech(String string) {
        _speechString = string.replace("\"", "").replace("'", "");
    }

    /**
     * This method is a getter to check whether the speed has been changed.
     * @return boolean to check whether the speed has been changed previously.
     */
    public boolean isChanged() {
        return _isChanged;
    }
}