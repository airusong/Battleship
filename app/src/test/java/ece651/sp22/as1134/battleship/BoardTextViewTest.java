package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2,'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  |  A\n" + "B  |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_3by2(){
    Board<Character> b2 = new BattleShipBoard<Character>(3, 2,'X');
    BoardTextView view = new BoardTextView(b2);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      expectedHeader+
      "A  | |  A\n"+
      "B  | |  B\n"+
      expectedHeader;
      assertEquals(expected, view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_3by5(){
    Board<Character> b3 = new BattleShipBoard<Character>(3, 5,'X');
    BoardTextView view = new BoardTextView(b3);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      expectedHeader+
      "A  | |  A\n"+
      "B  | |  B\n"+
      "C  | |  C\n"+
      "D  | |  D\n"+
      "E  | |  E\n"+
      expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  @Test
   public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20,'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27,'X');
    assertThrows(IllegalArgumentException.class,() ->new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }
 @Test
 public void test_board_view(){
    Board<Character> b3 = new BattleShipBoard<Character>(3, 5,'X');
    BoardTextView view = new BoardTextView(b3);
    String expectedHeader = "  0|1|2\n";
    Coordinate c=new Coordinate(0,0);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    String expected=
      expectedHeader+
      "A s| |  A\n"+
      "B  | |  B\n"+
      "C  | |  C\n"+
      "D  | |  D\n"+
      "E  | |  E\n"+
      expectedHeader;
    b3.tryAddShip(s);
    assertEquals(expected, view.displayMyOwnBoard());
 }
  @Test
  public void test_enemy_board(){
    Board<Character> b = new BattleShipBoard<Character>(4, 3,'X');
    BoardTextView view = new BoardTextView(b);
    Placement p1=new Placement(new Coordinate(1,0),'H');
    Placement p2=new Placement(new Coordinate(0,3),'V');
    V1ShipFactory fac=new V1ShipFactory();
    Ship<Character> sub=fac.makeSubmarine(p1);
    Ship<Character> des=fac.makeDestroyer(p2);
    b.tryAddShip(sub);
    b.tryAddShip(des);
    String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    assertEquals(myView, view.displayMyOwnBoard());
    b.fireAt(new Coordinate(0,0));
    b.fireAt(new Coordinate(1,0));
       String enemyView =
      "  0|1|2|3\n" +
      "A X| | |  A\n" +
      "B s| | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";
    assertEquals(enemyView,view.displayEnemyBoard());
  }
}
