package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(testShip.getName(),expectedName);
    int length=expectedLocs.length;
    for(int i=0;i<length;i++){
      assertEquals(testShip.getDisplayInfoAt(expectedLocs[i],true),expectedLetter);
      //      assertEquals(testShip.getDisplayInfoAt(expectedLocs[i],false),null);
    }    

  }

  @Test
  public void test_makeShips() {
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    V1ShipFactory f=new V1ShipFactory();
    Ship<Character> dst = f.makeDestroyer(v1_2);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
    Placement v1_3 = new Placement(new Coordinate(1, 3), 'v');
    V1ShipFactory f2=new V1ShipFactory();
    Ship<Character> sub = f2.makeSubmarine(v1_3);
    checkShip(sub, "Submarine", 's', new Coordinate(1, 3), new Coordinate(2, 3));
    Placement v1_4 = new Placement(new Coordinate(1, 4), 'H');
    V1ShipFactory f3=new V1ShipFactory();
    Ship<Character> battle = f3.makeBattleship(v1_4);
    checkShip(battle, "BattleShip", 'b', new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(1, 6),new Coordinate(1, 7));
    Placement v0_0 = new Placement(new Coordinate(0, 0), 'h');
    V1ShipFactory f4=new V1ShipFactory();
    Ship<Character> carrier = f4.makeCarrier(v0_0);
    checkShip(carrier, "Carrier", 'c', new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2),new Coordinate(0, 3),new Coordinate(0, 4),new Coordinate(0, 5));
    
  }

}
