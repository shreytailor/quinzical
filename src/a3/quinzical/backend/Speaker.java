package a3.quinzical.backend;

// Java dependencies.
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Speaker {
    private static Speaker _speaker;

    private int SPEED;
    private int DEFAULT = 175;
    private String _speechString;
    private boolean _isChanged = false;

    private Process _process;
    private ProcessBuilder _processBuilder;

    private Speaker() {}

    public static Speaker init() {
        if (_speaker == null) {
            _speaker = new Speaker();
        }

        return _speaker;
    }

    public void setSpeed(int speed) throws IOException {
        // Throw an exception if speed is out of excepted bounds.
        if (speed < 80 || speed > 450) {
            throw new IOException();
        }

        // Set the new speed, and we aren't using the default speed anymore.
        SPEED = speed;
        _isChanged = true;
    }

    public void resetSpeed() {
        _isChanged = false;
    }

    public int getSpeed() {
        if (_isChanged) {
            return SPEED;
        }

        return DEFAULT;
    }

    public void setSpeech(String string) {
        _speechString = string;
    }

    public boolean isChanged() {
        return _isChanged;
    }

    public void speak() {
        List<String> arguments = new ArrayList<String>();
        arguments.add("espeak");
        arguments.add(_speechString);
        if (_isChanged) {
            arguments.add("-s");
            arguments.add(String.valueOf(SPEED));
        }

        try {
            _processBuilder = new ProcessBuilder(arguments);
            _process = _processBuilder.start();
        } catch (IOException error) {
            // Due to implementation, this will never be thrown.
        }
    }

    public void kill() {
        if (_process != null) {
            _process.destroy();
        }
    }
}