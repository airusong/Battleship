package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class Carrierv2Test {
  @Test
  public void test_makeCoords() {
    Carrierv2<Character> c = new Carrierv2<Character>("Carrier", new Coordinate(0, 0), 'U', 'c', '*');
    HashSet<Coordinate> set = new HashSet<Coordinate>();
    set = c.makeCoords(new Coordinate(0, 0), 'U');
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(0, 0));
    expected.add(new Coordinate(1, 0));
    expected.add(new Coordinate(2, 0));
    expected.add(new Coordinate(3, 0));
    expected.add(new Coordinate(2, 1));
    expected.add(new Coordinate(3, 1));
    expected.add(new Coordinate(4, 1));
    assertEquals(expected, set);
    Carrierv2<Character> c2 = new Carrierv2<Character>("Carrier", new Coordinate(0, 0), 'R', 'c', '*');
    HashSet<Coordinate> set2 = new HashSet<Coordinate>();
    set2 = c2.makeCoords(new Coordinate(0, 0), 'R');
    HashSet<Coordinate> expected2 = new HashSet<Coordinate>();
    expected2.add(new Coordinate(0, 1));
    expected2.add(new Coordinate(0, 2));
    expected2.add(new Coordinate(0, 3));
    expected2.add(new Coordinate(0, 4));
    expected2.add(new Coordinate(1, 0));
    expected2.add(new Coordinate(1, 1));
    expected2.add(new Coordinate(1, 2));
    assertEquals(expected2, set2);
    Carrierv2<Character> c3 = new Carrierv2<Character>("Carrier", new Coordinate(0, 0), 'D', 'c', '*');
    HashSet<Coordinate> set3 = new HashSet<Coordinate>();
    set3 = c3.makeCoords(new Coordinate(0, 0), 'D');
    HashSet<Coordinate> expected3 = new HashSet<Coordinate>();
    expected3.add(new Coordinate(0, 0));
    expected3.add(new Coordinate(1, 0));
    expected3.add(new Coordinate(2, 0));
    expected3.add(new Coordinate(1, 1));
    expected3.add(new Coordinate(2, 1));
    expected3.add(new Coordinate(3, 1));
    expected3.add(new Coordinate(4, 1));
    assertEquals(expected3, set3);
    Carrierv2<Character> c4 = new Carrierv2<Character>("Carrier", new Coordinate(0, 0), 'L', 'c', '*');
    HashSet<Coordinate> set4 = new HashSet<Coordinate>();
    set4 = c4.makeCoords(new Coordinate(0, 0), 'L');
    HashSet<Coordinate> expected4 = new HashSet<Coordinate>();
    expected4.add(new Coordinate(0, 2));
    expected4.add(new Coordinate(0, 3));
    expected4.add(new Coordinate(0, 4));
    expected4.add(new Coordinate(1, 0));
    expected4.add(new Coordinate(1, 1));
    expected4.add(new Coordinate(1, 2));
    expected4.add(new Coordinate(1, 3));
    assertEquals(expected4, set4);
    assertThrows(IllegalArgumentException.class,
        () -> new Carrierv2<Character>("Carrier", new Coordinate(0, 0), 'H', 'c', '*'));

    assertEquals(c4.getName(), "Carrier");
  }
}
