import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Connect4Frame extends JFrame implements MouseListener, WindowListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    // output stream to the server
    ObjectOutputStream os;
    private char framePlayer;
    private char turn = 'R';
    private Timer countdownTimer;
    private char[][] grid;
    private boolean isConnected;

    public Connect4Frame(GameData gameData, ObjectOutputStream os, char player, char[][] grid)
    {
        super("Connect4 Game");
        // sets the attributes
        this.gameData = gameData;
        this.grid = grid;
        this.os = os;
        this.player = player;

        // adds a MouseListener to the Frame
        addMouseListener(this);
        addWindowListener(this);
        isConnected = true;
        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        countdownTimer = new Timer(1000, null);
        countdownTimer.setInitialDelay(0);

        // Set initial frame message
        if(player == 'R')
            text = "Waiting for Black to Connect";

        setSize(800,700);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        if (!isConnected) {
            return;
        }

        // draws the background
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,getWidth(),getHeight());

        // draws the display text to the screen
        g.setColor(Color.BLUE);
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int begin = (800 - textWidth) / 2;
        g.drawString(text, begin, 70);

        // draws the circles to the screen
        int circleWidth = 75;
        int circleHeight = 75;
        int startX = 75;
        int startY = 100;
        int spacingX = 20;
        int spacingY = (getHeight()-75*2-6 * circleHeight)/5;

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                if(gameData.getGrid()[y][x] == ' '){
                    int circleX = startX + x * (circleWidth + spacingX);
                    int circleY = startY + y * (circleHeight + spacingY);
                    g.setColor(Color.white);
                    g.fillOval(circleX, circleY, circleWidth, circleHeight);
                }

                if(gameData.getGrid()[y][x] == 'R'){
                    int circleX = startX + x * (circleWidth + spacingX);
                    int circleY = startY + y * (circleHeight + spacingY);
                    g.setColor(Color.red);
                    g.fillOval(circleX, circleY, circleWidth, circleHeight);
                }

                if(gameData.getGrid()[y][x] == 'B'){
                    int circleX = startX + x * (circleWidth + spacingX);
                    int circleY = startY + y * (circleHeight + spacingY);
                    g.setColor(Color.black);
                    g.fillOval(circleX, circleY, circleWidth, circleHeight);
                }
            }
        }
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public void setTurn(char turn) {
        if(turn==player)
            text = "Your Turn";
        else
        {
            if (turn=='R')
                text = "Red's Turn.";
            else
                text = "Black's Turn.";
        }
        repaint();
    }

    public void reset()
    {
        gameData.reset();
    }


    public GameData getGameData()
    {
        return gameData;
    }

    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            if (e.getButton()==3 && (gameData.tieGame() ||
                                    gameData.rowWin('R') || gameData.columnWin('R') || gameData.diagonalWin('R') ||
                                    gameData.rowWin('B') || gameData.columnWin('B') || gameData.diagonalWin('B')))
            {
                try
                {
                    if (player=='R')
                        os.writeObject(new CommandFromClient(CommandFromClient.RED_RESTART, "" + player));
                    else
                        os.writeObject(new CommandFromClient(CommandFromClient.BLACK_RESTART, "" + player));
                }
                catch (Exception c)
                {
                    c.printStackTrace();
                }
            }
    }

    public void mousePressed(MouseEvent e){
        System.out.println("Testing Purposes: Mouse Pressed");
        for (int i = 0; i<6; i++) {
            for (int j = 0; j<7; j++) {
                if (gameData.getGrid()[i][j]==' ')
                    System.out.print('-');
                else
                    System.out.print(gameData.getGrid()[i][j]);
            }
            System.out.println();
        }
        int x = e.getX();
        int r = -1;
        int c = -1;

        if(x>=75 && x<=150){
            for(int i =5; i>=0; i--){
                if(gameData.getGrid()[i][0] == ' '){
                    r =i;
                    c=0;
                    break;
                }
            }
        }

        if(x>=170 && x<=245){
            for(int i =5; i>=0; i--){
                if(gameData.getGrid()[i][1] == ' '){
                    r =i;
                    c=1;
                    break;
                }
            }
        }

        if(x>=265 && x<=340){
            for(int i =5; i>=0; i--){
                if(gameData.getGrid()[i][2] == ' '){
                    r =i;
                    c=2;
                    break;
                }
            }
        }

        if(x>=360 && x<=435){
            for(int i =5; i>=0; i--){
                if(gameData.getGrid()[i][3] == ' '){
                    r =i;
                    c=3;
                    break;
                }
            }
        }

        if(x>=455 && x<=530){
            for(int i =5; i>=0; i--){
                if(gameData.getGrid()[i][4] == ' '){
                    r =i;
                    c=4;
                    break;
                }
            }
        }

        if(x>=550 && x<=625){
            for(int i =5; i>=0; i--){
                if(gameData.getGrid()[i][5] == ' '){
                    r =i;
                    c=5;
                    break;
                }
            }
        }

        if(x>=645 && x<=720){
            for(int i =5; i>=0; i--){
                if(gameData.getGrid()[i][6] == ' '){
                    r =i;
                    c=6;
                    break;
                }
            }
        }

        if(c!=-1) {
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + c + r + player));
            } catch (Exception y) {
                y.printStackTrace();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) {
        player = player;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent evt)
    {
        if(framePlayer=='P')
        {
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.RED_DISC, "" + player));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
