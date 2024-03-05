import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientListener implements Runnable {
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private Connect4Frame frame;
    private Timer countdownTimer;

    public ClientListener(ObjectInputStream is, ObjectOutputStream os, Connect4Frame frame) {
        this.is = is;
        this.os = os;
        this.frame = frame;
        countdownTimer = new Timer(1000, null);
        countdownTimer.setInitialDelay(0);
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
                else if (cfs.getCommand() == CommandFromServer.DISCONNECT)
                {
                    final int[] countdown = {5};
                    Timer countdownTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            char player = frame.getPlayer();
                            if (player == 'R') {
                                frame.setText("Black quit. Shutting down in: " + countdown[0]);
                                countdown[0]--;
                                if (countdown[0] < 0) {
                                    ((Timer) e.getSource()).stop();
                                    frame.dispose();
                                }
                            }
                            else
                            {
                                frame.setText("Red quit. Shutting down in: " + countdown[0]);
                                countdown[0]--;
                                if (countdown[0] < 0) {
                                    ((Timer) e.getSource()).stop();
                                    frame.dispose();
                                }
                            }
                        }
                    });
                    countdownTimer.setInitialDelay(0);
                    countdownTimer.start();
                }
                else if (cfs.getCommand() == CommandFromServer.RESTART)
                {
                    frame.reset();
                    frame.repaint();
                }
                else if (cfs.getCommand() == CommandFromServer.RED_RESTART)
                {
                    if (frame.getPlayer() == 'R')
                        frame.setText("Waiting for Black to agree to a new game.");
                    else
                        frame.setText("Red is ready. Right click to start a new game.");
                }
                else if (cfs.getCommand() == CommandFromServer.BLACK_RESTART)
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