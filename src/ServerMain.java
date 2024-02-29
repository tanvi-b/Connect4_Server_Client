import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//red is x
//black is o

public class ServerMain
{
    public static void main(String[] args)
    {
        try
        {
            // creates a socket that allows connections on port 8001
            ServerSocket serverSocket = new ServerSocket(8001);

            // allow Red (X) to connect and build streams to / from Red
            Socket xCon = serverSocket.accept();
            ObjectOutputStream xos = new ObjectOutputStream(xCon.getOutputStream());
            ObjectInputStream xis = new ObjectInputStream(xCon.getInputStream());

            // Lets the client know they are the Red player
            xos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_RED,null));
            System.out.println("Red has Connected.");

            // Creates a Thread to listen to the Red client
            ServersListener sl = new ServersListener(xis,xos,'R');
            Thread t = new Thread(sl);
            t.start();

            // allow Black (O) to connect and build streams to / from Black
            Socket oCon = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(oCon.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(oCon.getInputStream());

            // Lets the client know they are the Black player
            oos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_BLACK,null));
            System.out.println("Black has Connected.");

            // Creates a Thread to listen to the Black client
            sl = new ServersListener(ois,oos,'B');
            t = new Thread(sl);
            t.start();

            xos.writeObject(new CommandFromServer(CommandFromServer.RED_TURN,null));
            oos.writeObject(new CommandFromServer(CommandFromServer.RED_TURN,null));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}