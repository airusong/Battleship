package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class Battleshipv2Test {
  @Test
  public void test_makeCoords() {
    Battleshipv2<Character> s = new Battleshipv2<>("BattleShip", new Coordinate(0, 0), 'U', 'b', '*');
    HashSet<Coordinate> set = new HashSet<>();
    set = s.makeCoords(new Coordinate(0, 0), 'U');
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(0, 1));
    expected.add(new Coordinate(1, 0));
    expected.add(new Coordinate(1, 1));
    expected.add(new Coordinate(1, 2));
    assertEquals(expected, set);
    Battleshipv2<Character> s2 = new Battleshipv2<>("BattleShip", new Coordinate(0, 0), 'R', 'b', '*');
    HashSet<Coordinate> set2 = new HashSet<>();
    set2 = s2.makeCoords(new Coordinate(0, 0), 'R');
    HashSet<Coordinate> expected2 = new HashSet<Coordinate>();
    expected2.add(new Coordinate(0, 0));
    expected2.add(new Coordinate(1, 0));
    expected2.add(new Coordinate(2, 0));
    expected2.add(new Coordinate(1, 1));
    assertEquals(expected2, set2);
    Battleshipv2<Character> s3 = new Battleshipv2<>("BattleShip", new Coordinate(0, 0), 'D', 'b', '*');
    HashSet<Coordinate> set3 = new HashSet<>();
    set3 = s.makeCoords(new Coordinate(0, 0), 'D');
    HashSet<Coordinate> expected3 = new HashSet<Coordinate>();
    expected3.add(new Coordinate(0, 0));
    expected3.add(new Coordinate(0, 1));
    expected3.add(new Coordinate(0, 2));
    expected3.add(new Coordinate(1, 1));
    assertEquals(expected3, set3);
    Battleshipv2<Character> s4 = new Battleshipv2<>("BattleShip", new Coordinate(0, 0), 'L', 'b', '*');
    HashSet<Coordinate> set4 = new HashSet<>();
    set4 = s4.makeCoords(new Coordinate(0, 0), 'L');
    HashSet<Coordinate> expected4 = new HashSet<Coordinate>();
    expected4.add(new Coordinate(0, 1));
    expected4.add(new Coordinate(1, 0));
    expected4.add(new Coordinate(1, 1));
    expected4.add(new Coordinate(2, 1));
    assertEquals(expected4, set4);
    assertEquals(s4.getName(), "BattleShip");
    assertThrows(IllegalArgumentException.class,
        () -> new Battleshipv2<>("BattleShip", new Coordinate(0, 0), 'V', 'b', '*'));
  }

}
