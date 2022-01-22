package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-5, 30));
  }

  private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expect) {
    int w = b.getWidth();
    int h = b.getHeight();
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        Coordinate c = new Coordinate(i, j);
        assertEquals(expect[i][j], b.whatIsAt(c));
      }
    }
  }

  @Test
  public void test_empty_board() {
    Board<Character> b = new BattleShipBoard<Character>(2, 3);
    Character[][] expect = new Character[2][3];
    checkWhatIsAtBoard(b, expect);
  }

  @Test
  public void test_tryaddship() {
    Board<Character> b = new BattleShipBoard<Character>(2, 3);
    Coordinate c = new Coordinate(0, 0);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    assertEquals(b.tryAddShip(s), true);
  }

  @Test
  public void test_right_coordinate() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(2, 3);
    Coordinate c = new Coordinate(0, 0);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    b.tryAddShip(s);
    assertEquals(b.whatIsAt(c),'s');
    Coordinate c2 = new Coordinate(0, 1);
    RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    b.tryAddShip(s2); 
    assertEquals(b.whatIsAt(c2),'s');

  }
}
