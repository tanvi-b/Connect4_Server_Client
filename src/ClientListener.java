import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientListener implements Runnable {
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private Connect4Frame frame = null;

    public ClientListener(ObjectInputStream is, ObjectOutputStream os, Connect4Frame frame) {
        this.is = is;
        this.os = os;
        this.frame = frame;
    }

    @Override
    public void run() {
        try {
            while (true) {
                CommandFromServer cfs = (CommandFromServer) is.readObject();
                // Processes the received command
                if (cfs.getCommand() == CommandFromServer.RED_TURN)
                    frame.setTurn('R');
                else if (cfs.getCommand() == CommandFromServer.BLACK_TURN)
                    frame.setTurn('B');
                else if (cfs.getCommand() == CommandFromServer.MOVE) {
                    String data = cfs.getData();
                    int c = data.charAt(0) - '0';
                    int r = data.charAt(1) - '0';
                    frame.makeMove(c, r, data.charAt(2));
                } else if (cfs.getCommand() == CommandFromServer.TIE) {
                    frame.setText("Tie Game");
                } else if (cfs.getCommand() == CommandFromServer.RED_WINS) {
                    if (frame.getPlayer() == 'R')
                        frame.setText("You Win!");
                    else
                        frame.setText("You Lose!");
                } else if (cfs.getCommand() == CommandFromServer.BLACK_WINS) {
                    if (frame.getPlayer() == 'B')
                        frame.setText("You Win!");
                    else
                        frame.setText("You Lose!");
                }
                else if (cfs.getCommand()==CommandFromServer.RESTART_RED)
                {
                    if (frame.getPlayer() == 'R')
                        frame.setText("Waiting for Black to agree to a new game.");
                    else
                        frame.setText("Red is ready. Right click to start a new game.");
                }
                else if (cfs.getCommand()==CommandFromServer.RESTART_BLACK)
                {
                    if (frame.getPlayer() == 'B')
                        frame.setText("Waiting for Red to agree to a new game.");
                    else
                        frame.setText("Black is ready. Right click to start a new game.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//Waiting for ___ to agree to a new game
//___ is ready, right click to start a new game
//___ quit. Shutting down in: 5 4 3 2 1 0