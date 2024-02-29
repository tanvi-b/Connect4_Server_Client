import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientListener implements Runnable
{
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private Connect4Frame frame = null;

    public ClientListener(ObjectInputStream is,
                          ObjectOutputStream os,
                          Connect4Frame frame) {
        this.is = is;
        this.os = os;
        this.frame = frame;
    }

    @Override
    public void run() {
        try
        {
            while (true)
            {
                CommandFromServer cfs = (CommandFromServer) is.readObject();
                //processes the received command
                if (cfs.getCommand()== CommandFromServer.CONNECTED_RED_TURN)
                    frame.setTurn('R');
                else if (cfs.getCommand()== CommandFromServer.CONNECTED_BLACK_TURN)
                    frame.setTurn('B');
                else if (cfs.getCommand() == cfs.MOVE)
                {
                    String data = cfs.getData();
                    int c = data.charAt(0)- '0';
                    int r = data.charAt(1)- '0';
                    frame.makeMove(c,r,data.charAt(2));
                }
                else if (cfs.getCommand()== CommandFromServer.TIE)
                {
                    frame.setText("Tie Game (R to reset)");
                }
                else if (cfs.getCommand()== CommandFromServer.X_WINS)
                {
                    frame.setText("X wins! (R to reset)");
                }
                else if (cfs.getCommand()== CommandFromServer.O_WINS)
                {
                    frame.setText("O wins! (R to reset)");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
