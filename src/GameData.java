public class GameData {
    private char[][] grid = {{' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                         {' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' '}};
    public char[][] getGrid() {return grid;}
    public void reset()
    {
        grid = new char[6][7];
        for (int r=0; r<grid.length; r++)
            for (int c=0; c<grid[0].length; c++)
                grid[r][c]= ' ';
    }

    public boolean tieGame ()
    {
        int filledColumns = 0;
        for (int c = 0; c<grid[0].length; c++)
        {
            if (columnFull(c)==true)
                filledColumns++;
        }
        return (filledColumns==grid[0].length);
    }

    public boolean columnFull (int column)
    {
        int filledSpots = 0;
        for(int r = 0; r < grid.length; r++)
        {
            if (grid[r][column]==RED || board[r][column]==BLACK)
                filledSpots++;
        }
        if (filledSpots==6)
            return true;
        else
            return false;
    }

    public boolean isWinner(char letter)
    {
        if((grid[0][0] ==letter && grid[0][1] ==letter && grid[0][2] ==letter)
                || (grid[1][0] ==letter && grid[1][1] ==letter && grid[1][2] ==letter)
                || (grid[2][0] ==letter && grid[2][1] ==letter && grid[2][2] ==letter)

                || (grid[0][0] ==letter && grid[1][0] ==letter && grid[2][0] ==letter)
                || (grid[0][1] ==letter && grid[1][1] ==letter && grid[2][1] ==letter)
                || (grid[0][2] ==letter && grid[1][2] ==letter && grid[2][2] ==letter)

                || (grid[0][0] ==letter && grid[1][1] ==letter && grid[2][2] ==letter)
                || (grid[0][2] ==letter && grid[1][1] ==letter && grid[2][0] ==letter))
            return true;
        else
            return false;
    }
}
