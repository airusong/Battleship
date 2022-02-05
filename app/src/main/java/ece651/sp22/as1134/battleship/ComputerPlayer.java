package ece651.sp22.as1134.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Random;
/**
   * Constructs a TextPlayer
   * @param theBoard is a user's borad
   * @param view is the board to displayy
   * @param BufferedReader inputReader
   * @param PrintStream out
   * @param shipFactory
   * @param playername
   * @param shipsToPlace:an ArrayList of the ship names that we want to work from    
   * @param shipCreationFns:Hashmap connect ship's name with 
   */
public class ComputerPlayer extends Player {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;
   Random rand=new Random(1); 
  public ComputerPlayer(Board<Character> theBoard, Reader inputSource, PrintStream out,
    AbstractShipFactory<Character> shipFactory, String name) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = (BufferedReader) inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.name=name;
  }
  public String getName(){
      return name;
  }
  public Board<Character> getBoard(){
    return theBoard;
  }
  /*
   *  method to place all the ships
   */
   public void doPlacementPhase() throws IOException {
    Ship<Character> s1 = shipFactory.makeSubmarine(new Placement("a0h"));
    Ship<Character> s2 = shipFactory.makeSubmarine(new Placement("b0h"));
    Ship<Character> s3 = shipFactory.makeDestroyer(new Placement("c0h"));
    Ship<Character> s4 = shipFactory.makeDestroyer(new Placement("d0h"));
    Ship<Character> s5 = shipFactory.makeDestroyer(new Placement("e0h"));
    Ship<Character> s6 = shipFactory.makeBattleship(new Placement("f0r"));
    Ship<Character> s7 = shipFactory.makeBattleship(new Placement("I0r"));
    Ship<Character> s8 = shipFactory.makeBattleship(new Placement("l0r"));
    Ship<Character> s9 = shipFactory.makeCarrier(new Placement("a3u"));
    Ship<Character> s10 = shipFactory.makeCarrier(new Placement("e3u"));
    theBoard.tryAddShip(s1);
    theBoard.tryAddShip(s2);
    theBoard.tryAddShip(s3);
    theBoard.tryAddShip(s4);
    theBoard.tryAddShip(s5);
    theBoard.tryAddShip(s6);
    theBoard.tryAddShip(s7);
    theBoard.tryAddShip(s8);
    theBoard.tryAddShip(s9);
    theBoard.tryAddShip(s10);
  }
  /*
   *  method to generate a random coordinate and fire at the player's board
   */
  public void playOneturn(Board<Character> enemyBoard,String myHeader, String enemyHeader) throws IOException{
    // Random rand=new Random();
    int y=rand.nextInt(10);
    int x=rand.nextInt(20);
    Coordinate c=new Coordinate(x,y);
    enemyBoard.fireAt(c);
  }

}
