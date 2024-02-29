import java.io.Serializable;

public class CommandFromServer implements Serializable
{

    private int command;
    private String data ="";

    // Command list
    public static final int CONNECTED_RED_TURN=0;
    public static final int CONNECTED_BLACK_TURN=1;
    public static final int X_TURN=2;
    public static final int O_TURN=3;
    public static final int MOVE=4;
    public static final int X_WINS=5;
    public static final int O_WINS=6;
    public static final int TIE=7;

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
