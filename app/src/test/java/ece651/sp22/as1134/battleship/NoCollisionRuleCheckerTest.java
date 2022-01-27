
package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_nocollisionRule() {
    PlacementRuleChecker<Character> inbounds=new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> placementChecker = new NoCollisionRuleChecker<Character>(null);
    Board<Character> board = new BattleShipBoard<>(10, 20, placementChecker);
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> battle = fac.makeBattleship(p1);
    assertEquals(placementChecker.checkPlacement(battle, board),null);
    board.tryAddShip(battle);
    Placement p2 = new Placement(new Coordinate(1, 0), 'V');
    Ship<Character> battle2 = fac.makeBattleship(p2);
    assertEquals(placementChecker.checkPlacement(battle2, board),"That placement is invalid: the ship overlaps another ship.");
  }
  @Test
  public void test_chain(){
    InBoundsRuleChecker<Character> inbounds= new InBoundsRuleChecker<>(null);
    PlacementRuleChecker<Character> nocollision=new NoCollisionRuleChecker<>(inbounds);
    Board<Character> board = new BattleShipBoard<>(10, 20);
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(0, 0), 'V');
    Placement p3 = new Placement(new Coordinate(11, 20), 'V');
     Ship<Character> battle3 = fac.makeBattleship(p3);
    Ship<Character> battle = fac.makeBattleship(p1);
    board.tryAddShip(battle);
    Placement p2 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> battle2 = fac.makeBattleship(p2);
    assertEquals(nocollision.checkPlacement(battle2, board),"That placement is invalid: the ship overlaps another ship.");
    //board.tryAddShip(battle2);
    //board.tryAddShip(battle3);
    //assertEquals(nocollision.checkPlacement(battle3, board),false);

  }
}
