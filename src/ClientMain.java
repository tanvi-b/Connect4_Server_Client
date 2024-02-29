import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main (String[] args)
    {
        try {
            //create an object for the Connect4 game
            GameData gameData = new GameData();

            //create a connection to server
            Scanner user_input = new Scanner (System.in);
            System.out.println("Enter the IP address of server: ");
            Socket socket = new Socket("127.0.0.1", 8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            // determine if playing as red or black
            CommandFromServer cfs = (CommandFromServer) is.readObject();
            Connect4Frame frame;

            // create the frame based on which player the server says this client is
            if (cfs.getCommand()==CommandFromServer.CONNECTED_AS_RED)
                frame = new Connect4Frame(gameData, os, 'R');
            else
                frame = new Connect4Frame(gameData, os,'B');

            ClientListener cl = new ClientListener (is, os, frame);
            Thread t = new Thread(cl);
            t.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
