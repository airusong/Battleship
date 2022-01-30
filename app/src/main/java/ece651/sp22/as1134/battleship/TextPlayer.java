package ece651.sp22.as1134.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
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

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  private final String playername;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  public TextPlayer(Board<Character> theBoard,Reader inputSource, PrintStream out,AbstractShipFactory<Character> shipFactory,String name) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader =(BufferedReader)inputSource;
    this.out = out;
    this.shipFactory=shipFactory;
    this.playername=name;
    this.shipsToPlace=new ArrayList<String>();
    this.shipCreationFns=new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
    setupShipCreationList();
   }
  /*
   * method to creat the hashmap which key is the ships and value is the function to create ships on board
   */
 protected void setupShipCreationMap(){
     shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
     shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
     shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
     shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));

  }
  /*
   * method to create the list of ships of one textplayer
   */
  protected void setupShipCreationList(){
     shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
     shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
     shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
     shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));

  }
public String getName(){
      return playername;
  }
  /*
   * method to read the placement of the ship from user 
   */
  public Placement readPlacement(String prompt) throws IOException {
     Placement p=null;
     out.println(prompt);    
        try{
           String s = inputReader.readLine();
           if(s==null){
              throw new EOFException();
            }
            p=new Placement(s); 
        }catch(IllegalArgumentException error) {
           throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
       }
      return p;
  }
  /*
   * method to read the attack position
   */
  public Coordinate readCoordinate(String prompt) throws IOException{
    Coordinate c=null;
    out.println(prompt);
    String s = inputReader.readLine();
    c=new Coordinate(s);
    return c;
  }
  /*
   * method to place the list of all ships of one textplayer
   */
  public void doPlacementPhase() throws IOException{
    //       BoardTextView view = new BoardTextView(theBoard);
       out.println(view.displayMyOwnBoard());
       out.println("Player "+this.playername+": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical). You have"+"\n"+
       "2 Submarines ships that are 1x2"+"\n"+
       "3 Destroyers that are 1x3"+"\n"+
       "3 Battleships that are 1x4"+"\n"+
       "2 Carriers that are 1x6"+"\n");
       for(String shiptype:shipsToPlace){
         doOnePlacement(shiptype,shipCreationFns.get(shiptype));
       }
  }
  /*
   *method to place one ship
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException{
    String prompt="Player "+this.playername+" Where would you like to place a " + shipName + "?";
     while(true){
       try{
         Placement p = readPlacement(prompt);
         Ship<Character> s  = createFn.apply(p);
         String message=theBoard.tryAddShip(s);
         if(message==null){
            out.println(view.displayMyOwnBoard());
            break;
           }else{
           out.println(message);
           }
       }catch(IllegalArgumentException error){
         out.println("That placement is invalid: it does not have the correct format.");
       }
   }
  }
   /*
    * method to let player1 play a turn,then
   * see if player 2 has lost. Then let player 2 play a turn and see if player 1 has lost.
   * It should repeat this until one player has lost, then report the outcome.
   *
   */
  // public void doAttackingPhase() throws IOException{
    

  // }
  /*  
   * method to do one turn of attacking 
   */
  public void playOneturn(Board<Character> enemyBoard,String myHeader, String enemyHeader) throws IOException{
    String prompt = "Player "+this.playername+" Where would you like to fire at?";
    while(true){
      try{
        Coordinate c=readCoordinate(prompt);
        enemyBoard.fireAt(c);
        BoardTextView enemyView=new BoardTextView(enemyBoard);
        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,myHeader,enemyHeader));
        if(enemyBoard.whatIsAtForEnemy(c)=='X'){
          out.println("you missed");
        }else if(enemyBoard.whatIsAtForEnemy(c)=='s'){
          out.println("you hit a submarine");
        }else if(enemyBoard.whatIsAtForEnemy(c)=='d'){
          out.println("you hit a destroyer");
        }else if(enemyBoard.whatIsAtForEnemy(c)=='b'){
          out.println("you hit a battleship");
        }else{
          out.println("you hit a carrier");
        }
        break;
      }catch(IllegalArgumentException error){
         out.println("The coordinate is out of bound"); 
      }
    }
  }
}
