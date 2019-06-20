// PRINTING OUT VISIBLE REPRESENTATION OF THE BOARD


/*
To Do:
x/ intake int 2d array of boats, boolean 2d array of shots, boolean 2d array of visibility
x/ print grid visualization
x/ widen for one-digit numbers to allow for two-digit numbers

Bugs/Notes:

*/

public class Visualize
{
  private BattleboatsBoard boats;
  private ShotsBoard shots;
  private VisibilityBoard visibility;

  public Visualize(BattleboatsBoard inboats, ShotsBoard inshots, VisibilityBoard invisibility)
  {
    boats = inboats;
    shots = inshots;
    visibility = invisibility;
  }

  public static String symbolize(int boat, boolean shot, boolean vis)
  {
    if (boat != 0 && shot && vis)
    {
      return "x"; // hit
    }

    if (boat == 0 && shot && vis)
    {
      return "o"; // miss
    }

    if (boat != 0 && !shot && vis)
    {
      return "!"; // sighting
    }

    if (boat == 0 && !shot && vis) // no boat no shot yes vis
    {
      return "%"; // saw nothing on recon
    }

    return "?"; // unknown
  }

  public void visualize()
  {
    System.out.println();
    System.out.println("Key:");
    System.out.println("[x] is a hit, [o] is a miss");
    System.out.println("[!] is a sighting of a ship");
    System.out.println("[%] is sighting of the empty sea");
    System.out.println();
    System.out.print("    ");

    // horizontal column labels (x)
    for (int i = 0; i < boats.getWidth(); i++)
    {
      if (i < 10)
      {
        System.out.print(" "); // allowing for more room for double-digit labels
      }
      System.out.print(i + " ");
    }
    System.out.println();

    // print border as wide as column labels
    System.out.print("----");
    for (int i = 0; i < boats.getWidth(); i++)
    {
      System.out.print("---");
    }
    System.out.println();

    // print the row labels (y), border, and symbols
    for (int i = 0; i < boats.getHeight(); i++) // i is the counter for y
    {
      if (i < 10)
      {
        System.out.print(" "); // beginnings of special case for double-digit labels
      }
      System.out.print(i + "| ");
      for (int j = 0; j < boats.getWidth(); j++) // j is the counter for x
      {
        System.out.print(" " + symbolize(boats.shoot(j, i), shots.shotQuery(j, i), visibility.getVisible(j, i)) + " ");
      }
      System.out.println();
    }

  }
}
