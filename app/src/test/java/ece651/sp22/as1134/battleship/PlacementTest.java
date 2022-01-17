package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  Coordinate c1 = new Coordinate(1, 2);
  Coordinate c2 = new Coordinate(1, 2);
  Coordinate c3 = new Coordinate(0, 3);
  Coordinate c4 = new Coordinate(2, 1);

  @Test
  public void test_hashCode() {
    Placement p1 = new Placement("A0V");
    Placement p2 = new Placement("A0v");
    Placement p3 = new Placement(c1, 'V');
    Placement p4 = new Placement(c2, 'v');
    assertEquals(p1.hashCode(), p2.hashCode());
    assertEquals(p3.hashCode(), p4.hashCode());
    Placement p5 = new Placement(c3, 'V');
    assertNotEquals(p5.hashCode(), p1.hashCode());
  }

  @Test
  public void test_equals() {
    Placement p1 = new Placement("A0V");
    Placement p2 = new Placement("A0v");
    Placement p3 = new Placement("B8H");
    Placement p4 = new Placement("C7V");
    assertEquals(p1, p2);
    assertEquals(p1, p1);
    assertNotEquals(p1, p3);
    assertNotEquals(p2, p4);
    assertNotEquals(p1, "D6h");
  }

  @Test
  void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("A0V");
    assertEquals("(0, 0)", p1.getWhere().toString());
    assertEquals('V', p1.getOrientation());
    Placement p2 = new Placement("B8H");
    assertEquals("(1, 8)", p2.getWhere().toString());
    assertEquals('H', p2.getOrientation());
  }
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("000"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A04"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A/"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A:"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A"));
  }
}
