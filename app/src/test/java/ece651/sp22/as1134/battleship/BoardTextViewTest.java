package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  |  A\n" + "B  |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_3by2(){
    Board<Character> b2 = new BattleShipBoard<Character>(3, 2);
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
    Board<Character> b3 = new BattleShipBoard<Character>(3, 5);
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
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);
    assertThrows(IllegalArgumentException.class,() ->new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }
 @Test
 public void test_board_view(){
  Board<Character> b3 = new BattleShipBoard<Character>(3, 5);
    BoardTextView view = new BoardTextView(b3);
    String expectedHeader = "  0|1|2\n";
    Coordinate c=new Coordinate(0,0);
    Ship<Character> s=new BasicShip(c);
    String expected=
      expectedHeader+
      "A s| |  A\n"+
      "B  | |  B\n"+
      "C  | |  C\n"+
      "D  | |  D\n"+
      "E  | |  E\n"+
      expectedHeader;
    boolean value=b3.tryAddShip(s);
    assertEquals(expected, view.displayMyOwnBoard());
 }
}
