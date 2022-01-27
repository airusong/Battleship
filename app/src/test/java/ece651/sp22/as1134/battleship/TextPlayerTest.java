package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    Board<Character> board = new BattleShipBoard<Character>(w, h);
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
   /*  
  @Test
  public void test_do_phase_placement() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(6, 10, "A0H\nB0H\nC0H\nD0H\nE0H\nF0H\nG0H\nH0H\nI0H\nJ0H\n", bytes);
    player.doPlacementPhase();
    String expectedHeader = "  0|1|2|3|4|5\n";
    String expected = expectedHeader +
      "A  | | | | |  A\n" +
      "B  | | | | |  B\n" +
      "C  | | | | |  C\n" +
      "D  | | | | |  D\n" +
      "E  | | | | |  E\n" + 
      "F  | | | | |  F\n" +
      "G  | | | | |  G\n" + 
      "H  | | | | |  H\n" +  
      "I  | | | | |  I\n" + 
      "J  | | | | |  J\n" + 
      expectedHeader;
    String expected2= expectedHeader +
      "A s|s| | | |  A\n" + 
      "B  | | | | |  B\n" + 
      "C  | | | | |  C\n" +   
      "D  | | | | |  D\n" +  
      "E  | | | | |  E\n" +  
      "F  | | | | |  F\n" +
      "G  | | | | |  G\n" + 
      "H  | | | | |  H\n" +   
      "I  | | | | |  I\n" +  
      "J  | | | | |  J\n" +  
      expectedHeader;
     String expected3= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C  | | | | |  C\n" +
       "D  | | | | |  D\n" +
       "E  | | | | |  E\n" +
       "F  | | | | |  F\n" +
       "G  | | | | |  G\n" +
       "H  | | | | |  H\n" +
       "I  | | | | |  I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
     String expected4= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D  | | | | |  D\n" +
       "E  | | | | |  E\n" +
       "F  | | | | |  F\n" +
       "G  | | | | |  G\n" +
       "H  | | | | |  H\n" +
       "I  | | | | |  I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
      String expected5= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D d|d|d| | |  D\n" +
       "E  | | | | |  E\n" +
       "F  | | | | |  F\n" +
       "G  | | | | |  G\n" +
       "H  | | | | |  H\n" +
       "I  | | | | |  I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
     String expected6= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D d|d|d| | |  D\n" +
       "E b|b|b|b| |  E\n" +
       "F  | | | | |  F\n" +
       "G  | | | | |  G\n" +
       "H  | | | | |  H\n" +
       "I  | | | | |  I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
 String expected7= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D d|d|d| | |  D\n" +
       "E d|d|d|d| |  E\n" +
       "F b|b|b|b| |  F\n" +
       "G  | | | | |  G\n" +
       "H  | | | | |  H\n" +
       "I  | | | | |  I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
     String expected8= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D d|d|d| | |  D\n" +
       "E d|d|d| | |  E\n" +
       "F b|b|b|b| |  F\n" +
       "G b|b|b|b| |  G\n" +
       "H  | | | | |  H\n" +
       "I  | | | | |  I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
     String expected9= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D d|d|d| | |  D\n" +
       "E d|d|d| | |  E\n" +
       "F b|b|b|b| |  F\n" +
       "G b|b|b|b| |  G\n" +
       "H b|b|b|b| |  H\n" +
       "I  | | | | |  I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
     String expected10= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D d|d|d| | |  D\n" +
       "E d|d|d| | |  E\n" +
       "F b|b|b|b| |  F\n" +
       "G b|b|b|b| |  G\n" +
       "H b|b|b|b| |  H\n" +
       "I c|c|c|c|c|c I\n" +
       "J  | | | | |  J\n" +
       expectedHeader;
     String expected11= expectedHeader +
       "A s|s| | | |  A\n" +
       "B s|s| | | |  B\n" +
       "C d|d|d| | |  C\n" +
       "D d|d|d| | |  D\n" +
       "E d|d|d| | |  E\n" +
       "F b|b|b|b| |  F\n" +
       "G b|b|b|b| |  G\n" +
       "H b|b|b|b| |  H\n" +
       "I c|c|c|c|c|c I\n" +
       "J c|c|c|c|c|c J\n" +
       expectedHeader;
     

    assertEquals(expected+"\n"+"Player " + player.getName() + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical). You have"+"\n"+
       "2 Submarines ships that are 1x2"+"\n"+
       "3 Destroyers that are 1x3"+"\n"+
       "3 Battleships that are 1x4"+"\n"+
       "2 Carriers that are 1x6"+"\n\n"+
       "Player " + player.getName() + " Where would you like to place a Submarine?"+"\n"+expected2+"\n"+
       "Player " + player.getName() + " Where would you like to place a Submarine?"+"\n"+expected3+"\n"+
       "Player " + player.getName() + " Where would you like to place a Destroyer?"+"\n"+expected4+"\n"+
       "Player " + player.getName() + " Where would you like to place a Destroyer?"+"\n"+expected5+"\n"+
       "Player " + player.getName() + " Where would you like to place a Destroyer?"+"\n"+expected6+"\n"+
       "Player " + player.getName() + " Where would you like to place a Battleship?"+"\n"+expected7+"\n"+
       "Player " + player.getName() + " Where would you like to place a Battleship?"+"\n"+expected8+"\n"+
       "Player " + player.getName() + " Where would you like to place a Battleship?"+"\n"+expected9+"\n"+
       "Player " + player.getName() + " Where would you like to place a Carrier?"+"\n"+expected10+"\n"+
       "Player " + player.getName() + " Where would you like to place a Carrier?"+"\n"+expected11+"\n", bytes.toString());
       }*/
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
}
