package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {
   private TextPlayer createTextPlayer(int w, int h, String inputData, ByteArrayOutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h,'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer(board, input, output, shipFactory,"A");
  }
  
  @Test
  public void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');
    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }
  @Test
  public void test_read_coordinate() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2\nC8\na4\n", bytes);
    String prompt = "Where would you like to fire at?";
    Coordinate[] expected=new Coordinate[3];
    expected[0] = new Coordinate(1, 2);
    expected[1] = new Coordinate(2, 8);
    expected[2] = new Coordinate(0, 4);
    for (int i = 0; i < expected.length; i++) {
      Coordinate c=player.readCoordinate(prompt);
      assertEquals(c,expected[i]);
      assertEquals(prompt + "\n", bytes.toString());
      bytes.reset();
    }
  }

  @Test
  public void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 4, "A0V\n", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Destroyer", (p)->shipFactory.makeDestroyer(p));
    String expectedHeader = "  0|1\n";
    String expected = expectedHeader +
      "A d|  A\n" +
      "B d|  B\n" +
      "C d|  C\n" +
      "D  |  D\n" +
      expectedHeader;
    assertEquals("Player " + player.getName() + " Where would you like to place a Destroyer?" + "\n"+expected+"\n",bytes.toString());
    bytes.reset();

  }
  @Test
  public void test_do_wrong_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 4, "00\nA0V\n", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Destroyer", (p)->shipFactory.makeDestroyer(p));
    String expectedHeader = "  0|1\n";
    String expected = expectedHeader +
      "A d|  A\n" +
      "B d|  B\n" +
      "C d|  C\n" +
      "D  |  D\n" +
      expectedHeader;
    assertEquals("Player " + player.getName() + " Where would you like to place a Destroyer?" + "\n"+
                 "That placement is invalid: it does not have the correct format."+"\n"+
                 "Player " + player.getName() + " Where would you like to place a Destroyer?"+
                 "\n"+expected+"\n",bytes.toString());
    bytes.reset();
  }
  @Test
  public void test_do_wrong_placement2() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 4, "C0V\nA0V\n", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Destroyer", (p)->shipFactory.makeDestroyer(p));
    String expectedHeader = "  0|1\n";
    String expected = expectedHeader +
      "A d|  A\n" +
      "B d|  B\n" +
      "C d|  C\n" +
      "D  |  D\n" +
      expectedHeader;
    assertEquals("Player " + player.getName() + " Where would you like to place a Destroyer?" + "\n"+
                 "That placement is invalid: the ship goes off the bottom of the board."+"\n"+
                 "Player " + player.getName() + " Where would you like to place a Destroyer?"+
                 "\n"+expected+"\n",bytes.toString());
    bytes.reset();
  }
  
  @Test
  public void test_eofException() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 4, "", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    assertThrows(EOFException.class, ()->player.readPlacement(" Where would you like to place a Destroyer?"));
  }
  //@Disabled
  @Test
  public void test_eofException2() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 4, "", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    assertThrows(EOFException.class, ()->player.readCoordinate(" Where would you like to place a Destroyer?"));
  }
  
  // @Disabled
  @Test
  public void test_play_one_turn() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 1, "F\nA0\n", bytes);
    Board<Character> enemyBoard=new BattleShipBoard<Character>(10, 1, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> sub1=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    enemyBoard.tryAddShip(sub1);
    player.playOneturn(enemyBoard, "Your ocean", "Player B's ocean");
    String expected=
      "     Your ocean                           Player B's ocean"+"\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
      "A  | | | | | | | | |  A                A  | | | | | | | | |  A\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
                    "F Fire at a square\n"+
                    "M Move a ship to another square (2 remaining)\n"+
                    "S Sonar scan (1 remaining)\n"+
                    "Player A, what would you like to do?\n\n"+
      "Player A Where would you like to fire at?"+"\n"+
       "you hit a submarine\n"+
       "     Your ocean                           Player B's ocean"+"\n"+
       "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
       "A  | | | | | | | | |  A                A s| | | | | | | | |  A\n"+
       "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n";
     assertEquals(expected, bytes.toString());
     bytes.reset();
   }
  
  // @Disabled
  @Test
  public void test_play_one_turn2() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 1, "M\nA0\na9h\n", bytes);
    Board<Character> enemyBoard=new BattleShipBoard<Character>(10, 1, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    Ship<Character> sub1=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    Ship<Character> sub2=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    //    enemyBoard.tryAddShip(sub2);
    player.theBoard.tryAddShip(sub1);
    //    player.theBoard.fireAt(new Coordinate(0,0));
    player.theBoard.fireAt(new Coordinate(0,1));
    player.playOneturn(enemyBoard, "Your ocean", "Player B's ocean");
    String expected= "     Your ocean                           Player B's ocean"+"\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
      "A s|*| | | | | | | |  A                A  | | | | | | | | |  A\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
                    "F Fire at a square\n"+
                    "M Move a ship to another square (2 remaining)\n"+
                    "S Sonar scan (1 remaining)\n"+
                    "Player A, what would you like to do?\n\n"+ 
      "which ship do you want to move?"+"\n"+"Please enter a new placement for you ship\n"+"That placement is invalid: the ship goes off the right of the board.\n"+
      "     Your ocean                           Player B's ocean"+"\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
      "A s|*| | | | | | | |  A                A  | | | | | | | | |  A\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n";
    assertEquals(expected, bytes.toString());
    bytes.reset();
  }
  //@Disabled
  @Test
  public void test_hit_carrier() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 1, "F\n00\nA0\n", bytes);
    Board<Character> enemyBoard=new BattleShipBoard<Character>(10, 1, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> sub1=shipFactory.makeCarrier(new Placement(new Coordinate(0,0),'H'));
    enemyBoard.tryAddShip(sub1);
    player.playOneturn(enemyBoard, "Your ocean", "Player B's ocean");
    String expected="     Your ocean                           Player B's ocean"+"\n"+
                    "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
                    "A  | | | | | | | | |  A                A  | | | | | | | | |  A\n"+
                    "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
                    "F Fire at a square\n"+
                    "M Move a ship to another square (2 remaining)\n"+
                    "S Sonar scan (1 remaining)\n"+
                    "Player A, what would you like to do?\n\n"+ 
                    "Player A Where would you like to fire at?"+"\n"+
                    "The coordinate is wrong\n"+
                    "Player A Where would you like to fire at?"+"\n"+
      "you hit a carrier\n"+
      "     Your ocean                           Player B's ocean"+"\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
      "A  | | | | | | | | |  A                A c| | | | | | | | |  A\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n";
    assertEquals(expected, bytes.toString());
    bytes.reset();
  }
  @Test
  public void play_sonar() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 1, "S\nA0\n", bytes);
    Board<Character> enemyBoard=new BattleShipBoard<Character>(10, 1, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> sub1=shipFactory.makeCarrier(new Placement(new Coordinate(0,0),'H'));
    enemyBoard.tryAddShip(sub1);
    player.playOneturn(enemyBoard, "Your ocean", "Player B's ocean");
    assertEquals(player.scanchoice,0);
  }
  @Test
  public void test_fireoutofbound() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 1, "F\nB0\nA0\nM\nA3\nA0H\nF\nA0\n", bytes);
    V2ShipFactory fac=new V2ShipFactory();
    Ship<Character> sub=fac.makeSubmarine(new Placement(new Coordinate(0,3),'H'));
    Ship<Character> sub2=fac.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    player.theBoard.tryAddShip(sub);
    Board<Character> enemyBoard=new BattleShipBoard<Character>(10, 1, 'X');
    enemyBoard.fireAt(new Coordinate(0,0));
    player.playOneturn(enemyBoard, "Your ocean", "Player B's ocean");
    player.playOneturn(enemyBoard, "Your ocean", "Player B's ocean");
    enemyBoard.tryAddShip(sub2);
    //    player.theBoard.fireAt(new Coordinate(0,0));
    player.playOneturn(enemyBoard, "Your ocean", "Player B's ocean");
    String expected="     Your ocean                           Player B's ocean"+"\n"+
                    "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
                    "A  | | |s|s| | | | |  A                A X| | | | | | | | |  A\n"+
                    "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
                    "F Fire at a square\n"+
                    "M Move a ship to another square (2 remaining)\n"+
                    "S Sonar scan (1 remaining)\n"+
                    "Player A, what would you like to do?\n\n"+ 
      "Player A Where would you like to fire at?"+"\n"+
      "The coordinate is wrong"+"\n"+
      "Player A Where would you like to fire at?"+"\n"+
      "you missed\n"+
      "     Your ocean                           Player B's ocean"+"\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
      "A  | | |s|s| | | | |  A                A X| | | | | | | | |  A\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
      "     Your ocean                           Player B's ocean"+"\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
      "A  | | |s|s| | | | |  A                A X| | | | | | | | |  A\n"+
      "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
                    "F Fire at a square\n"+
                    "M Move a ship to another square (2 remaining)\n"+
                    "S Sonar scan (1 remaining)\n"+
                    "Player A, what would you like to do?\n\n"+ 
                    "which ship do you want to move?"+"\n"+
                    "Please enter a new placement for you ship\n"+
                     "     Your ocean                           Player B's ocean"+"\n"+
                     "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
                     "A s|s| | | | | | | |  A                A X| | | | | | | | |  A\n"+
                     "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
                     "     Your ocean                           Player B's ocean"+"\n"+
                     "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
                     "A s|s| | | | | | | |  A                A X| | | | | | | | |  A\n"+
                     "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n"+
                    "F Fire at a square\n"+
                    "M Move a ship to another square (1 remaining)\n"+
                    "S Sonar scan (1 remaining)\n"+
                    "Player A, what would you like to do?\n\n"+ 
                    "Player A Where would you like to fire at?\n"+
                    "you hit a submarine\n"+
                     "     Your ocean                           Player B's ocean"+"\n"+
                     "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+
                     "A s|s| | | | | | | |  A                A s| | | | | | | | |  A\n"+
                     "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n";
    assertEquals(expected,bytes.toString());
    bytes.reset();
  }
  @Test
  public void moveship() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader("A0\nA7H\nA7\nA5H"));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(10, 1,'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer player=new  TextPlayer(board, input, output, shipFactory,"A");
    Ship<Character> sub=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    Ship<Character> sub2=shipFactory.makeSubmarine(new Placement(new Coordinate(0,2),'H'));
    board.tryAddShip(sub);
    board.tryAddShip(sub2);
    board.fireAt(new Coordinate(0,1));
    player.moveship();
    player.moveship();
       BoardTextView view=new BoardTextView(board);
       String expected="  0|1|2|3|4|5|6|7|8|9\n"+
                       "A  | |s|s| |s|*| | |  A\n"+
                       "  0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(view.displayMyOwnBoard(),expected);
    String expected2="  0|1|2|3|4|5|6|7|8|9\n"+
                     "A  |s| | | | | | | |  A\n"+
                     "  0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(view.displayEnemyBoard(),expected2);
    assertEquals(bytes.toString(),"which ship do you want to move?\nPlease enter a new placement for you ship\nwhich ship do you want to move?\nPlease enter a new placement for you ship\n");
    
  }

  @Test
  public void moveship4() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader("AA\nA0\n68h\n"));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(10, 1,'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer player=new  TextPlayer(board, input, output, shipFactory,"A");
    Ship<Character> sub=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    board.tryAddShip(sub);
    player.moveship(); 
    assertEquals(bytes.toString(),"which ship do you want to move?\nplease renter a ship!\nwhich ship do you want to move?\nPlease enter a new placement for you ship\nyour new placement is illegal\n");
  }
    @Test
  public void moveship5() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader("A5\nA0\n68h\n"));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(10, 1,'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer player=new  TextPlayer(board, input, output, shipFactory,"A");
    Ship<Character> sub=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    board.tryAddShip(sub);
    player.moveship(); 
    assertEquals(bytes.toString(),"which ship do you want to move?\nplease renter a ship!\nwhich ship do you want to move?\nPlease enter a new placement for you ship\nyour new placement is illegal\n");
  }
  //  @Disabled
  @Test
  public void moveship2() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader("A0\nA5H\n"));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(10, 1,'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer player=new  TextPlayer(board, input, output, shipFactory,"A");
    Ship<Character> sub=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    Ship<Character> sub2=shipFactory.makeSubmarine(new Placement(new Coordinate(0,2),'H'));
    board.tryAddShip(sub);
    board.tryAddShip(sub2);
    BoardTextView view=new BoardTextView(board); 
    board.fireAt(new Coordinate(0,5));
    player.moveship();
    board.fireAt(new Coordinate(0,5));
    String expected3="  0|1|2|3|4|5|6|7|8|9\n"+
                     "A  | | | | |s| | | |  A\n"+
                     "  0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(view.displayEnemyBoard(),expected3);
    String expected4="  0|1|2|3|4|5|6|7|8|9\n"+
                     "A  | |s|s| |*|s| | |  A\n"+
                     "  0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(view.displayMyOwnBoard(),expected4);
    assertEquals(bytes.toString(),"which ship do you want to move?\n"+
                 "Please enter a new placement for you ship\n");
   }
  @Test
  public void moveship3() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader("A1\nA2H\n"));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(10, 1,'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer player=new  TextPlayer(board, input, output, shipFactory,"A");
    Ship<Character> sub=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'H'));
    Ship<Character> sub2=shipFactory.makeSubmarine(new Placement(new Coordinate(0,2),'H'));
    board.tryAddShip(sub);
    board.tryAddShip(sub2);
    player.moveship();
    assertEquals(bytes.toString(),"which ship do you want to move?\n"+
                                   "Please enter a new placement for you ship\n"+
                                   "That placement is invalid: the ship overlaps another ship.\n");

  }
  @Test
  public void sonarscan() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader("C2\n"));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(5, 5,'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer player=new  TextPlayer(board, input, output, shipFactory,"A");
    Ship<Character> sub=shipFactory.makeSubmarine(new Placement(new Coordinate(0,0),'V'));
    Ship<Character> des=shipFactory.makeDestroyer(new Placement(new Coordinate(0,2),'V'));
    Ship<Character> bat=shipFactory.makeBattleship(new Placement(new Coordinate(2,0),'R'));
    Ship<Character> car=shipFactory.makeCarrier(new Placement(new Coordinate(0,3),'U')); 
    board.tryAddShip(sub);
    board.tryAddShip(des);
    board.tryAddShip(bat);
    board.tryAddShip(car);
    player.sonarScan(board);
    String expected="Please enter a coordinate to begin sonar scan\n"+
                    "Submarines occupy 1 squares\n"+
                    "Destroyers occupy 3 squares\n"+
                    "Battleships occupy 3 squares\n"+
                    "Carriers occupy 6 square\n\n";
    assertEquals(bytes.toString(),expected);
  }
}
