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
                if (cfs.getCommand()== CommandFromServer.RED_TURN)
                    frame.setTurn('R');
                else if (cfs.getCommand()== CommandFromServer.BLACK_TURN)
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
                    frame.setText("Tie Game");
                }
                else if (cfs.getCommand()==CommandFromServer.RED_WINS)
                {
                    if (cfs.getCommand()==CommandFromServer.CONNECTED_AS_RED)
                        frame.setText("You Win!");
                    else
                        frame.setText("You Lose!");
                }
                else if (cfs.getCommand()== CommandFromServer.BLACK_WINS)
                {
                    if (cfs.getCommand()==CommandFromServer.CONNECTED_AS_BLACK)
                        frame.setText("You Win!");
                    else
                        frame.setText("You Lose!");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
