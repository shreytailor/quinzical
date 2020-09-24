package a3.quinzical.backend;

// Java dependencies.
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.util.stream.Stream;
import java.io.OutputStreamWriter;


/**
 * This class is created to encapsulate and configure the Speaker which would be used throughout the
 * game. It is important to preserve some of the information within the class such as the current
 * speed, whether it has been changed by the user or not etc. This information is contained with the
 * fields of the class.
 * @author Shrey Tailor, Jason Wang
 */
public class Speaker {
    private static Speaker _speaker;

    private double SPEED;
    private double DEFAULT = 1;

    private Process _process;
    private String _speechString;
    private boolean _isChanged = false;
    private ProcessBuilder _processBuilder;


    // The blank constructor for the singleton object.
    private Speaker() {}


    /*
    This method is used from outside the class to either initialize the singleton for the first time
    ever, or just get the instance of the singleton already created.
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
        String accentCommand = "(voice_akl_nz_jdt_diphone)";

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
        } catch (IOException error) {
            // On our clients computer, we can assume that Festival will always be installed.
        };
    }


    /**
     * This method is used to stop the Speaker process from speaking.
     */
    public void kill() {
        /*
         * This commented code could be used for killing all the descendents of the main process,
         * however it is a little haphazard to do this, because it could be unsafe for the game.
         * Stream<ProcessHandle> descendents = ProcessHandle.current().descendants();
         *
         * Hence a better approach was opted for by only destroying the descendents of the known
         * process that is current speaking.
         */
        try {
            Stream<ProcessHandle> descendents = _process.descendants();
            descendents.filter(ProcessHandle::isAlive).forEach(ph -> {
                ph.destroy();
            });
        } catch (Exception error) {
            /*
            We don't have to deal with the exception because its thrown when the process doesn't exist.
            In this scenario, nothing will go wrong with the usability of the game.
             */
        };
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
     * @return
     */
    public boolean isChanged() {
        return _isChanged;
    }
}