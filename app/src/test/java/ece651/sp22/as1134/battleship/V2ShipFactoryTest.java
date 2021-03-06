package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);
    int length = expectedLocs.length;
    for (int i = 0; i < length; i++) {
      assertEquals(testShip.getDisplayInfoAt(expectedLocs[i], true), expectedLetter);
    }

  }

  @Test
  public void test_makeShips() {
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    V2ShipFactory f = new V2ShipFactory();
    Ship<Character> dst = f.makeDestroyer(v1_2);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
    Placement v1_3 = new Placement(new Coordinate(1, 3), 'h');
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> sub = f2.makeSubmarine(v1_3);
    checkShip(sub, "Submarine", 's', new Coordinate(1, 3), new Coordinate(1, 4));
    Placement v1_4 = new Placement(new Coordinate(1, 4), 'R');
    V2ShipFactory f3 = new V2ShipFactory();
    Ship<Character> battle = f3.makeBattleship(v1_4);
    checkShip(battle, "Battleship", 'b', new Coordinate(1, 4), new Coordinate(2, 4), new Coordinate(3, 4),
        new Coordinate(2, 5));
    Placement v0_1 = new Placement(new Coordinate(0, 1), 'U');
    V2ShipFactory f4 = new V2ShipFactory();
    Ship<Character> carrier = f4.makeCarrier(v0_1);
    checkShip(carrier, "Carrier", 'c', new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1),
        new Coordinate(3, 1), new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2));

  }

}
