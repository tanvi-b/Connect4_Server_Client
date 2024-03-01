import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class Connect4Frame extends JFrame implements MouseListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    // output stream to the server
    ObjectOutputStream os;

    public Connect4Frame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("Connect4 Game");
        // sets the attributes
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        // adds a MouseListener to the Frame
        addMouseListener(this);
        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        int spacingX = (getWidth()-75*2-7 * circleWidth)/6;
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

        // draws the player moves to the screen
        g.setFont(new Font("Times New Roman",Font.BOLD,70));
        for(int r=0; r<gameData.getGrid().length; r++)
            for(int c=0; c<gameData.getGrid().length; c++)
                g.drawString(""+gameData.getGrid()[r][c],c*133+42,r*133+150);
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

    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e){
        System.out.println("Testing Purposes: Mouse Pressed");
        int x = e.getX();
        int spacingX = (getWidth()-75*2-7 * 75)/6;
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

        if(x>=150+spacingX && x<=225+spacingX){
            for(int i =5; i>=0; i++){
                if(gameData.getGrid()[i][1] == ' '){
                    r =i;
                    c=1;
                    break;
                }
            }
        }

        if(x>=225+spacingX && x<=300+spacingX){
            for(int i =5; i>=0; i++){
                if(gameData.getGrid()[i][2] == ' '){
                    r =i;
                    c=2;
                    break;
                }
            }
        }

        if(x>=300+spacingX && x<=375+spacingX){
            for(int i =5; i>=0; i++){
                if(gameData.getGrid()[i][3] == ' '){
                    r =i;
                    c=3;
                    break;
                }
            }
        }

        if(x>=375+spacingX && x<=450+spacingX){
            for(int i =5; i>=0; i++){
                if(gameData.getGrid()[i][4] == ' '){
                    r =i;
                    c=4;
                    break;
                }
            }
        }

        if(x>=450+spacingX && x<=525+spacingX){
            for(int i =5; i>=0; i++){
                if(gameData.getGrid()[i][5] == ' '){
                    r =i;
                    c=5;
                    break;
                }
            }
        }

        if(x>=525+spacingX && x<=600+spacingX){
            for(int i =5; i>=0; i++){
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
}
