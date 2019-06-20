// BOOL ARRAY FOR VISIBILITY

public class VisibilityBoard
{
  private boolean[][] board;

  public VisibilityBoard(int width, int height, boolean debug)
  {
    board = new boolean[height][width];
    if (debug)
    {
      for(int i = 0; i < width; i++)
      {
        for(int j = 0; j < height; j++)
        {
          board[j][i] = true;
        }
      }
    }
  }

  public void setVisible(int x, int y)
  {
    board[y][x] = true;
  }

  public boolean getVisible(int x, int y)
  {
    return board[y][x];
  }

  public void drone(int x, int y)
  {
    for (int i = -1; i<=1; i++)
    {
      for (int j = -1; j<=1; j++)
      {
        if (x + i >= 0 && x + i < board[0].length && y + j >= 0 && y + j < board.length)
        {
          board[y+j][x+i] = true;
        }
      }
    }
  }

  /*
  public static void main(String[] args)
  {
    VisibilityBoard myboard = new VisibilityBoard(5,5,false);
    myboard.setVisible(3,3);
  }
  */

}
