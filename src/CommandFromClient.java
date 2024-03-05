import java.io.Serializable;

public class CommandFromClient implements Serializable
{
    //The command being sent to the server
    private int command;
    //Text data for the command
    private String data = "";

    public static final int MOVE    =0;
    public static final int RED_RESTART =1;
    public static final int BLACK_RESTART =2;
    public static final int RED_DISC = 3;

    public CommandFromClient(int command, String data) {
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