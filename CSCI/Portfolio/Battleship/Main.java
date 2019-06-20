// PLAY THROUGH THE TURNS

/*
To Do:
x/ game setup
  x/ print instructions
  x/ intake board setup parameters
  x/ make BattleboatsBoard
  x/ make ShotsBoard
  x/ make VisibilityBoard
  x/ start turn counter
  x/ start shots counter
  x/ start sunkboat counter
x/ run turns until game won (do-while loop checking for sunkboat count)
  x/ show board (by sending boat board, shot board, and visibility board to visualize class), turn count, shot count
  x/ intake x,y location OR drone (show turn counter when asking)
      x/ if drone:
        x/ change visibility
        x/ up turn counter 4
      x/ if fire:
        x/ if miss:
          x/ print miss
          x/ change shots, visibility
          x/ up turn counter 1
        x/ if penalty:
          x/ print penalty
          x/ up turn counter 2
        x/ if hit:
          x/ change shots, visibility
          x/ DO SOMETHING WITH A LIST OF HIT BOATS
          x/ up turn counter 1
          x/ if sunk:
            x/ print sunk
            x/ up sunkboat count

Bugs/Notes:
-/ could print out "turn skipped."

*/

import java.util.Scanner;

public class Main
{
  public static void main(String[] args)
  {
    // welcome
    System.out.println("\n            Welcome to Battleboats!\n");
    System.out.println("                  |    |    |");
    System.out.println("                 )_)  )_)  )_)");
    System.out.println("                )___))___))___)  ");
    System.out.println("               )____)____)_____) ");
    System.out.println("           --_____|____|____|______-- ");
    System.out.println("        -----|                   /-------- ");
    System.out.println("          ^^^^^ ^^^^^^^^^^^^^^^^^^^^^ ");
    System.out.println("        ^^^^      ^^^^     ^^^    ^^  ");
    System.out.println("             ^^^^      ^^^");

    // print instructions
    System.out.println(" Battleboat is a probability-based board game");
    System.out.println("that challenges the user to locate enemy boats");
    System.out.println(" hidden on a rectangular grid. The purpose of ");
    System.out.println(" the game is to locate and destroy every enemy ");
    System.out.println("     boat in the least number of guesses.");
    System.out.println();
    System.out.println("You will determine the height and width of the board,");
    System.out.println("then either fire upon the sea or send a drone out to");
    System.out.println("            to spy on the enemy.");
    System.out.println("        All boats have a length of 3,");
    System.out.println(" and drones can tell you about a 3x3 board segment.");
    System.out.println("However, drones take four turns to return, and you can't");
    System.out.println("      make any shots while they are away.");
    System.out.println();
    System.out.println("     Additionally, shooting off the board");
    System.out.println("    or shooting where you have already shot");
    System.out.println("  will result in a penalty and a skipped turn.");
    System.out.println();

    // set up boat board
    System.out.print("Enter the height that you want the board: ");
    Scanner h = new Scanner(System.in);
    int height = h.nextInt();

    System.out.print("Enter the width that you want the board: ");
    Scanner w = new Scanner(System.in);
    int width = w.nextInt();

    BattleboatsBoard board = new BattleboatsBoard(width, height); // double check later to make sure that the board doesn't have flipped dimensions
    System.out.println("You have " + board.boatCount() + " enemy boats to sink.");

    // make shots board
    ShotsBoard sboard = new ShotsBoard(width, height);

    // make visibility board -- first check for debug mode
    System.out.print("Will you be playing in debug mode? (y/n) ");
    Scanner a = new Scanner(System.in);
    String debug = a.next();
    VisibilityBoard vboard = new VisibilityBoard(width, height, debug.equals("y")); // default is not debug mode if there is any funky input

    // start turn counter
    int turns = 1;

    // start shots counter
    int shots = 0;

    // start sunkboats counter
    int sunkboats = 0;

    // start list of boats and their hitlist -- indexed by which boat
    int[] hitlist = new int[board.boatCount()+1];

    do
    {
    // show board, turn count
    Visualize vision = new Visualize(board, sboard, vboard);
    vision.visualize();
    System.out.println();
    System.out.println("============\n** TURN " + turns + " **\n============");

    // start action
    System.out.println();
    System.out.println("You have " + (board.boatCount()-sunkboats) + " enemy boats left to sink.");
    System.out.print("Fire or Send Drone? (f/d) ");
    Scanner b = new Scanner(System.in);
    String action = b.next();
    if(action.equals("d"))
    {
      // get drone coordinates
      System.out.print("Enter Drone Coordinates (x y): ");
      Scanner c = new Scanner(System.in);
      int drone_x = c.nextInt();
      int drone_y = c.nextInt();

      // change visibility
      vboard.drone(drone_x, drone_y);

      // up turn counter
      turns += 4;
    }

    else // let firing be the default if there's any funky input
    {
      // get shot coordinates
      System.out.print("Enter Shot Coordinates (x y): ");
      Scanner d = new Scanner(System.in);
      int shot_x = d.nextInt();
      int shot_y = d.nextInt();

      // if already attacked or out of bounds: print penalty
      if (shot_x >= width || shot_y >= height)
      {
        System.out.println("\n*************\n|* penalty *| \n*************");
        turns += 2;
        shots += 1;
      }

      else if(sboard.shotQuery(shot_x, shot_y))
      {
        System.out.println("\n*************\n|* penalty *| \n*************");
        turns += 2;
        shots += 1;
      }

      // else if not shot & no boat, print miss, update shots/visibility
      else if (!sboard.shotQuery(shot_x, shot_y) && board.shoot(shot_x, shot_y)==0)
      {
        sboard.shoot(shot_x, shot_y);
        vboard.setVisible(shot_x, shot_y);
        System.out.println("\n**********\n|* miss *| \n**********");
        shots += 1;
        turns += 1;
      }

      // else if not shot & yes boat: print hit, update shots/visibility
      else if (!sboard.shotQuery(shot_x, shot_y) && board.shoot(shot_x, shot_y)!=0)
      {
        sboard.shoot(shot_x, shot_y);
        vboard.setVisible(shot_x, shot_y);
        turns += 1;
        shots += 1;
        // update hitlist
        hitlist[board.shoot(shot_x, shot_y)]+=1;
        if (hitlist[board.shoot(shot_x, shot_y)] == 3)
        {
          System.out.println("\n**********\n|* sunk *| \n**********");
          sunkboats += 1;
        }
        else
          System.out.println("\n*********\n|* hit *| \n*********");
      }

    }
  } while (sunkboats < board.boatCount());

  System.out.println();
  Visualize vision = new Visualize(board, sboard, vboard);
  vision.visualize();

  System.out.println("\nYOU HAVE DEFEATED THE ENEMY!\n");
  System.out.println("Total Turn Count: " + turns);
  System.out.println("Total Shot Count: " + shots);
  }
}
