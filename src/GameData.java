public class GameData {
    private char[][] grid = {{' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                            {' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                            {' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' '}};
    private boolean gameOver = false;
    public char[][] getGrid() {return grid;}
    public void reset()
    {
        grid = new char[6][7];
        for (int r=0; r<grid.length; r++)
            for (int c=0; c<grid[0].length; c++)
                grid[r][c]= ' ';
    }

    public boolean isGameOver() {
        return gameOver;
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
            if (grid[r][column]!=' ')
                filledSpots++;
        }
        if (filledSpots==6)
            return true;
        else
            return false;
    }

    public boolean columnWin(int player)
    {
        int r = grid.length-1;
        while (r>grid.length-4)
        {
            for(int c = 0; c < grid[0].length; c++)
            {
                if (grid[r][c]==player && grid[r-1][c]==player && grid[r-2][c]==player && grid[r-3][c]==player) {
                    gameOver = true;
                    return true;
                }
            }
            r--;
        }
        return false;
    }

    public boolean rowWin(int player)
    {
        int c = 0;
        while (c<grid[0].length-3)
        {
            for(int r = 0; r < grid.length; r++)
            {
                if (grid[r][c]==player && grid[r][c+1]==player && grid[r][c+2]==player && grid[r][c+3]==player) {
                    gameOver = true;
                    return true;
                }
            }
            c++;
        }
        return false;
    }

    public boolean diagonalWin(int player)
    {
        for (int r = grid.length-1; r>grid.length-4; r--)
        {
            for(int c = 0; c<grid[0].length-3; c++)
            {
                if (grid[r][c]==player
                        && grid[r-1][c+1]==player
                        && grid[r-2][c+2]==player
                        && grid[r-3][c+3]==player) {
                    gameOver = true;
                    return true;
                }
            }
        }

        for (int r = grid.length-1; r>grid.length-4; r--)
        {
            for(int c = grid[0].length-1; c>grid[0].length-5; c--)
            {
                if (grid[r][c]==player && grid[r-1][c-1]==player && grid[r-2][c-2]==player && grid[r-3][c-3]==player) {
                    gameOver = true;
                    return true;
                }
            }
        }
        return false;
    }
}

//    public boolean isCat()
//    {
//        if(grid[0][0] !=' ' && grid[0][1] !=' ' && grid[0][2] !=' '
//                && grid[1][0] !=' ' && grid[1][1] !=' ' && grid[1][2] !=' '
//                && grid[2][0] !=' ' && grid[2][1] !=' ' && grid[2][2] !=' '
//                && !isWinner('X') && !isWinner('O'))
//            return true;
//        else
//            return false;
//    }