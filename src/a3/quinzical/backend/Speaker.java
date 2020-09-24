package a3.quinzical.backend;

// Java dependencies.
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.util.stream.Stream;
import java.io.OutputStreamWriter;

public class Speaker {
    private static Speaker _speaker;

    private double SPEED;
    private double DEFAULT = 1;

    private Process _process;
    private String _speechString;
    private boolean _isChanged = false;
    private ProcessBuilder _processBuilder;

    private Speaker() {}

    public static Speaker init() {
        if (_speaker == null) {
            _speaker = new Speaker();
        }

        return _speaker;
    }

    public void setSpeed(double speed) throws IOException {
        // Throw an exception if speed is out of excepted bounds.
        if (speed < 0.5 || speed > 2.5) {
            throw new IOException();
        }

        // Set the new speed, and we aren't using the default speed anymore.
        SPEED = speed;
        _isChanged = true;
    }

    public void speak() {
        // If there is already a speaking process running, destroy it before speaking again.
        kill();

        String speedCommand;
        if (_isChanged) {
            speedCommand = "(Parameter.set 'Duration_Stretch " + 1/SPEED + ")";
        } else {
            speedCommand = "(Parameter.set 'Duration_Stretch " + 1/DEFAULT + ")";
        }
        String speakingCommand = "(SayText " + "\"" + _speechString + "\"" + ")";

        _processBuilder = new ProcessBuilder("festival");
        try {
            _process = _processBuilder.start();
        } catch (IOException error) {  };

        OutputStream stdin = _process.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        try {
            writer.write(speedCommand);
            writer.write(speakingCommand);
            writer.close();
        } catch (IOException error) {  };
    }

    public void kill() {
        /*
         * This commented code could be used for killing all the descendents of the main process,
         * however it is a little haphazard to do this, because it could be unsafe for the game.
         * Stream<ProcessHandle> descendents = ProcessHandle.current().descendants();
         *
         * Hence a better approach was opted for by only destroying the descendents of the known
         * process that is current speaking. Furthermore, it is enclosed in a try/catch block as
         * when there is no process running and you try to destroy it, it will throw exception.
         */
        try {
            Stream<ProcessHandle> descendents = _process.descendants();
            descendents.filter(ProcessHandle::isAlive).forEach(ph -> {
                ph.destroy();
            });
        } catch (Exception error) {  };
    }

    public void resetSpeed() {
        _isChanged = false;
    }

    public double getSpeed() {
        DecimalFormat df = new DecimalFormat("#.00");

        if (_isChanged) {
            return Double.valueOf(df.format(SPEED));
        }

        return DEFAULT;
    }

    public void setSpeech(String string) {
        _speechString = string;
    }

    public boolean isChanged() {
        return _isChanged;
    }
}