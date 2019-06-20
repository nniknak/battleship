// A MAKING AND MAINTAINING A INT ARRAY OF RANDOMLY PLACED CONTIGUOUS BOAT LOCATIONS

/*
To Do:
x/ construct INT array of the input size
  x/ check validity of input
  x/ calculate number of boats based on input size
x/ place boats
  x/ validity function: return boolean
    x/ check if in bounds
    x/ check if already boated
  x/ random tip generator
  x/ boat placer
    x/ generate direction and axis
x/ end up with board of ints
x/ make it talk to boat coordinates
  x/ keep track of how many hits per boat


Bugs/Notes:

*/

public class BattleboatsBoard
{
  private int[][] board;
  private int boats;
  private int boatcount;
  private int width;
  private int height;

  public BattleboatsBoard(int x, int y)
  {
    width = x;
    height = y;
    if (width > 12 || height > 12 || width < 3 || height < 3)
    {
      // validate input
      throw new IllegalArgumentException("Width and height should be between 3 and 12.");
    }
    board = new int[height][width];

    // calculate number of boats based on input size
    int smallside = Math.min(width, height);
    switch (smallside)
    {
      case 3: boats = 1;
        break;
      case 4:
      case 5: boats = 2;
        break;
      case 6:
      case 7: boats = 3;
        break;
      case 8:
      case 9: boats = 4;
        break;
      case 10:
      case 11:
      case 12: boats = 6;
        break;
    }

    // place boats
    for (int i = 1; i <= boats; i++)
    {
      placeBoat(i);
    }
  }

  public boolean isValid(int x, int y)
  {
    // check if coordinate's in bounds
    if (x < 0 || x >= width || y < 0 || y >= height)
    {
      return false;
    }

    // check if there's already a boat there (zero means no boat)
    else
    {
      if (board[y][x]==0)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
  }

  public void placeBoat(int i)
  {
    // initialize tip
    int a;
    int b;

    // initialize direction
    int d;

    boolean unplaced = true;
    do
    {
      do
      {
      // random tip generator
      a = (int)Math.floor(Math.random() * width);
      b = (int)Math.floor(Math.random() * height);
      }
      while (!isValid(a, b));

      // generate direction
      if (Math.random()<0.5)
      {
        d = -1;
      } else
      {
        d = 1;
      }

      // decide axis and check validity of that option
      if (Math.random()<0.5)
      {
        // check validity on all coordinates in HORIZONTAL direction
        if (isValid(a+d,b) && isValid(a+d+d,b))
        {
          unplaced = false;
          board[b][a] = i;
          board[b][a+d] = i;
          board[b][a+d+d] = i;
        }
      } else
      {
        // check validity on all coordinates in VERTICAL direction
        if (isValid(a,b+d) && isValid(a,b+d+d))
        {
          unplaced = false;
          board[b][a] = i;
          board[b+d][a] = i;
          board[b+d+d][a] = i;
        }
      }
    } while (unplaced);
  }

  public int boatCount()
  {
    return boats;
  }


  public int shoot(int x, int y)
  {
    return board[y][x];
  }

  public int getHeight()
  {
    return height;
  }

  public int getWidth()
  {
    return width;
  }
  /*
  public static void main(String[] args)
  {
    // this is just for testing -- eventually this main will go away?
    BattleboatsBoard myboard = new BattleboatsBoard(10, 10);

    // test to see if the number of true's is correct with expected after generation of board
    int expected = 5*3;
    int actual = 0;
    for (int i = 0; i < 10; i++)
    {
      for (int j = 0; j < 10; j++)
      {
        if(myboard.shoot(i, j)!=0)
        {
          actual++;
        }
      }
    }
    System.out.println(expected == actual);

  }
  */
}
