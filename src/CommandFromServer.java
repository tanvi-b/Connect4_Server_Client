import java.io.Serializable;

public class CommandFromServer implements Serializable
{

    private int command;
    private String data ="";

    // Command list
    public static final int CONNECTED_AS_RED=0;
    public static final int CONNECTED_AS_BLACK=1;
    public static final int RED_TURN=2;
    public static final int BLACK_TURN=3;
    public static final int MOVE=4;
    public static final int RED_WINS=5;
    public static final int BLACK_WINS=6;
    public static final int TIE=7;

    public static final int RESTART_RED =8;

    public static final int RESTART_BLACK =9;

    public CommandFromServer(int command, String data) {
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }
}
