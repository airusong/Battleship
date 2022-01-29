package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    RectangleShip<Character> s = new RectangleShip<Character>("submarine", new Coordinate(0, 0), 2, 2, 's', '*');
    HashSet<Coordinate> rectangle = new HashSet<Coordinate>();
    rectangle = s.makeCoords(new Coordinate(0, 0), 2, 2);
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(0, 0));
    expected.add(new Coordinate(0, 1));
    expected.add(new Coordinate(1, 0));
    expected.add(new Coordinate(1, 1));
    assertEquals(expected, rectangle);
  }

  @Test
  public void test_hit() {
    BasicShip<Character> s = new RectangleShip<Character>("submarine", new Coordinate(0, 0), 2, 2, 's', '*');
    // no hit
    assertEquals(s.wasHitAt(new Coordinate(0, 0)), false);
    // hit
    s.recordHitAt(new Coordinate(0, 0));
    assertEquals(s.wasHitAt(new Coordinate(0, 0)), true);
    assertThrows(IllegalArgumentException.class, () -> s.checkCoordinateInThisShip(new Coordinate(3, 3)));
    s.recordHitAt(new Coordinate(0, 1));
    s.recordHitAt(new Coordinate(1, 0));
    assertEquals(false, s.isSunk());
    s.recordHitAt(new Coordinate(1, 1));
    assertEquals(true, s.isSunk());
    assertEquals("submarine", s.getName());
  }

  @Test
  public void test_get_display() {
    BasicShip<Character> s = new RectangleShip<Character>("submarine", new Coordinate(0, 0), 2, 2, 's', '*');
    s.recordHitAt(new Coordinate(0, 0));
    assertEquals(s.getDisplayInfoAt(new Coordinate(0, 0), true), '*');
    assertEquals(s.getDisplayInfoAt(new Coordinate(0, 1), true), 's');
    
    assertEquals(s.getDisplayInfoAt(new Coordinate(0, 0), false), 's');
    s.recordHitAt(new Coordinate(0, 1));
    assertEquals(s.getDisplayInfoAt(new Coordinate(0, 1), false), 's');
  }

  @Test
  public void test_coordinate() {
    RectangleShip<Character> s = new RectangleShip<Character>("submarine", new Coordinate(0, 0), 1, 2, 's', '*');
    Iterable<Coordinate> set = new HashSet<Coordinate>();
    set = s.getCoordinates();
    for (Coordinate c : set) {
      System.out.println(c.toString());
    }
  }

}
