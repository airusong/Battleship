package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_get_info() {
    ShipDisplayInfo<Character> ss=new SimpleShipDisplayInfo<Character>('a','b');
    assertEquals('b',(ss.getInfo(new Coordinate(0,0),true)));
    assertEquals('a',(ss.getInfo(new Coordinate(0,0),false)));
  }

}
