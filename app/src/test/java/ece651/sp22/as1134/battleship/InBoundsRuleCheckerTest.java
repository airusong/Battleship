package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_inboundsrule() {
    PlacementRuleChecker<Character> placementChecker = new InBoundsRuleChecker<Character>(null);
    Board<Character> board = new BattleShipBoard<>(10, 20, placementChecker);
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(-1, -1), 'V');
    Ship<Character> battle = fac.makeBattleship(p1);
    assertEquals(placementChecker.checkPlacement(battle, board), false);
    Placement p2 = new Placement(new Coordinate(9, 18), 'V');
    Ship<Character> battle2 = fac.makeBattleship(p2);
    assertEquals(placementChecker.checkPlacement(battle2, board), false);
    Placement p3 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> battle3 = fac.makeBattleship(p3);
    assertEquals(placementChecker.checkPlacement(battle3, board), true);

  }

}
