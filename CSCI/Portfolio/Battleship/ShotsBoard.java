// KEEPING TRACK OF WHERE THERE HAVE BEEN SHOTS

public class ShotsBoard
{
  private boolean[][] board;

  public ShotsBoard(int width, int height)
  {
    board = new boolean[height][width];
  }

  public boolean shotQuery(int x, int y)
  {
    return board[y][x];
  }

  public void shoot(int x, int y)
  {
    board[y][x] = true;
  }

  /*
  public static void main(String[] args)
  {
    ShotsBoard myboard = new ShotsBoard(5,5);
    myboard.shoot(3,3);
  }
  */

}
