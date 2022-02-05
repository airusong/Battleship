package ece651.sp22.as1134.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

public class TextPlayer extends Player{
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  private final String playername;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  int movechoice;
  int scanchoice;
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
    movechoice=2;
    scanchoice=1;
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
  public Board<Character> getBoard(){
    return theBoard;
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
    try{
      String s = inputReader.readLine();
      // c=new Coordinate(s);
       if(s==null){
          throw new EOFException();
       }
       c=new Coordinate(s);
    }catch(IllegalArgumentException error) {
       throw new IllegalArgumentException("The coordinate is invalid");
        }
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
   * method to do one turn of attacking 
   */
  public void playOneturn(Board<Character> enemyBoard,String myHeader, String enemyHeader) throws IOException{
    BoardTextView enemyView=new BoardTextView(enemyBoard);
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,myHeader,enemyHeader));
    out.println("F Fire at a square\n"+
                "M Move a ship to another square ("+movechoice+" remaining)\n"+
                "S Sonar scan ("+scanchoice+" remaining)\n"+
                "Player "+this.playername+", what would you like to do?\n");
    String choice=inputReader.readLine();
     if(choice.equals("M") && movechoice>0){
        boolean result=moveship();
        //BoardTextView enemyView=new BoardTextView(enemyBoard);
        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,myHeader,enemyHeader));
        if(result==true){
          movechoice--;
        }
     }
     else if(choice.equals("S") && scanchoice>0){
      sonarScan(enemyBoard);
      scanchoice--;
    }
    //if the player want have run out the movechioce and scan choice,game will prompt to fire at a space 
     else if(choice.equals("F")||movechoice==0||scanchoice==0){
      String prompt = "Player "+this.playername+" Where would you like to fire at?";
      while(true){
      try{
        Coordinate c=readCoordinate(prompt);
        enemyBoard.fireAt(c);
        //        BoardTextView enemyView=new BoardTextView(enemyBoard);
        //out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,myHeader,enemyHeader));
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
        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,myHeader,enemyHeader));
        break;
      }catch(IllegalArgumentException error){
         out.println("The coordinate is wrong"); 
      }
    }
   }
   
  }
  /*
   *  method to move the ship to a new place, haven't implemented the function to flip or rotate the ship.
   */
  public boolean moveship() throws IOException{
    Ship<Character> shipname=null;//the original ship to be move
    Ship<Character> newmovedone=null;//the new ship after moved
    String prompt="which ship do you want to move?";
    //    out.println(prompt);
    while(true){
      try{
        Coordinate old=readCoordinate(prompt);
        shipname=theBoard.findship(old);
        if(shipname==null){
          out.println("please renter a ship!");
        }else{
          break;
        }
      }catch(IllegalArgumentException error){
        out.println("please renter a ship!");
      }
    }
    try{
      Function<Placement, Ship<Character>> createFn=shipCreationFns.get(shipname.getName());
      Placement newplace=readPlacement("Please enter a new placement for you ship");//get the new placement for the ship
      newmovedone=createFn.apply(newplace);        //constrcut the new ship after moved
      String message=theBoard.tryAddShip(newmovedone);//try to add the new ship and check if it is correct
      if(message!=null){
        out.println(message);
        //theBoard.adddeleteship(shipname);
        return false;
      }
      Coordinate upperleft=theBoard.addmovehit(shipname);
      //get the upperleft coordinate of the original ship, store originally hitted position
      theBoard.remainnewhitmarker(upperleft, newplace.getWhere(), shipname);//store the hit postion of the new ship
      theBoard.adddeleteship(shipname); //delete the original ship
    }catch(IllegalArgumentException error){
      out.println("your new placement is illegal");
      return false;      //      theBoard.adddeleteship(shipname);      
    }
    return true;
  }
  /*
   * method to do a sonar scan
   */
  public void sonarScan(Board<Character> enemyBoard) throws IOException{
    Coordinate c=readCoordinate("Please enter a coordinate to begin sonar scan");
    HashSet<Coordinate> set=new HashSet<>();
    int sub=0;
    int des=0;
    int car=0;
    int bat=0;
    int x=c.getRow();
    int y=c.getColumn();
    int width=theBoard.getWidth();
    int height=theBoard.getHeight();
    set.add(new Coordinate(x,y-3));
    set.add(new Coordinate(x+1,y-2));
    set.add(new Coordinate(x,y-2));
    set.add(new Coordinate(x-1,y-2));
    set.add(new Coordinate(x-2,y-1));
    set.add(new Coordinate(x-1,y-1));
    set.add(new Coordinate(x,y-1));
    set.add(new Coordinate(x+1,y-1));
    set.add(new Coordinate(x+2,y-1));
    set.add(new Coordinate(x-3,y));
    set.add(new Coordinate(x-2,y));
    set.add(new Coordinate(x-1,y));
    set.add(c);
    set.add(new Coordinate(x+1,y));
    set.add(new Coordinate(x+2,y));
    set.add(new Coordinate(x+3,y));
    set.add(new Coordinate(x-2,y+1));
    set.add(new Coordinate(x-1,y+1));
    set.add(new Coordinate(x,y+1));
    set.add(new Coordinate(x+1,y+1));
    set.add(new Coordinate(x+2,y+1));
    set.add(new Coordinate(x-1,y+2));
    set.add(new Coordinate(x,y+2));
    set.add(new Coordinate(x+1,y+2));
    set.add(new Coordinate(x,y+3));
    for(Coordinate s:set){
      if(s.getRow()>=0&&s.getRow()<height&&s.getColumn()>=0&&s.getColumn()<width){
        Ship<Character> ship=theBoard.findship(s);
        if(ship!=null){
          if(ship.getName().equals("Submarine")){
            sub++;
          }
          else if(ship.getName().equals("Destroyer")){
            des++;
          }
          else if(ship.getName().equals("Carrier")){
            car++;
          }
          else{
            bat++;
          }
        }else{
          continue;
        }
      }
    }
    out.println("Submarines occupy "+sub+" squares\n"+
                "Destroyers occupy "+des+" squares\n"+
                "Battleships occupy "+bat+" squares\n"+
                "Carriers occupy "+car+" square\n");
  }
}




