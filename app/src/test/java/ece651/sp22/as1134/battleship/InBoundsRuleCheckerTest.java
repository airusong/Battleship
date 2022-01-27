package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_inboundsrule() {
    PlacementRuleChecker<Character> placementChecker = new InBoundsRuleChecker<Character>(null);
    Board<Character> board = new BattleShipBoard<>(10, 20, placementChecker);
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(0, -1), 'V');
    Ship<Character> battle = fac.makeBattleship(p1);
    assertEquals(placementChecker.checkPlacement(battle, board),"That placement is invalid: the ship goes off the left of the board.");
    Placement p2 = new Placement(new Coordinate(18, 9), 'V');
    Ship<Character> battle2 = fac.makeBattleship(p2);
    assertEquals(placementChecker.checkPlacement(battle2, board), "That placement is invalid: the ship goes off the bottom of the board.");
    Placement p3 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> battle3 = fac.makeBattleship(p3);
    assertEquals(placementChecker.checkPlacement(battle3, board), null);
    Placement p4 = new Placement(new Coordinate(0, 10), 'H');
    Ship<Character> battle4 = fac.makeBattleship(p4);
    assertEquals(placementChecker.checkPlacement(battle4, board),"That placement is invalid: the ship goes off the right of the board.");
    Placement p5 = new Placement(new Coordinate(-1, 2), 'V');
    Ship<Character> battle5 = fac.makeBattleship(p5);
    assertEquals(placementChecker.checkPlacement(battle5,board),"That placement is invalid: the ship goes off the top of the board.");
    
  }

}
