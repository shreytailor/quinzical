package a3.quinzical.backend;

// Java dependencies.
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Speaker {
    private static Speaker _speaker;

    private int SPEED;
    private int DEFAULT = 175;
    private String _speechString;
    private boolean _isChanged = false;

    private long _pid;
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
        kill();

        try {
            _processBuilder = new ProcessBuilder("/bin/bash", "-c", "echo " + _speechString + " | festival --tts");
            _process = _processBuilder.start();
            _pid = _process.pid();
        } catch (IOException error) {
            System.out.println(error);
        };
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
}