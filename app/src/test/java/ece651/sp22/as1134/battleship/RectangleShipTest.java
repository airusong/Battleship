package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    RectangleShip<Character> s=new RectangleShip<Character>(new Coordinate(0,0),2,2,'s', '*');
    HashSet<Coordinate> rectangle=new HashSet<Coordinate>();
    rectangle=s.makeCoords(new Coordinate(0,0),2,2);
    HashSet<Coordinate> expected=new HashSet<Coordinate>();
    expected.add(new Coordinate(0,0));
    expected.add(new Coordinate(0,1));
    expected.add(new Coordinate(1,0));
    expected.add(new Coordinate(1,1));
    assertEquals(expected,rectangle);
  }

}
