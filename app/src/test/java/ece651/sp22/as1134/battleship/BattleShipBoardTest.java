package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20,'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-5, 30,'X'));
  }

  private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expect) {
    int w = b.getWidth();
    int h = b.getHeight();
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        Coordinate c = new Coordinate(i, j);
        assertEquals(expect[i][j], b.whatIsAtForSelf(c));
      }
    }
  }

  @Test
  public void test_empty_board() {
    Board<Character> b = new BattleShipBoard<Character>(2, 3,'X');
    Character[][] expect = new Character[2][3];
    checkWhatIsAtBoard(b, expect);
  }

  @Test
  public void test_tryaddship() {
    Board<Character> b = new BattleShipBoard<Character>(2, 3,'X');
    Coordinate c = new Coordinate(0, 0);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    assertEquals(b.tryAddShip(s), null);
    PlacementRuleChecker<Character> placementChecker = new InBoundsRuleChecker<Character>(null);
    Board<Character> board = new BattleShipBoard<>(10, 20, placementChecker,'X');
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(19, 0), 'V');
    Ship<Character> battle = fac.makeBattleship(p1);
    assertEquals(board.tryAddShip(battle), "That placement is invalid: the ship goes off the bottom of the board.");
  }

  @Test
  public void test_right_coordinate() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(2, 3,'X');
    Coordinate c = new Coordinate(0, 0);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    b.tryAddShip(s);
    assertEquals(b.whatIsAtForSelf(c),'s');
    Coordinate c2 = new Coordinate(0, 1);
    RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    b.tryAddShip(s2); 
    assertEquals(b.whatIsAtForSelf(c2),'s');

  }

  @Test
  public void test_fire_at(){
    Board<Character> b = new BattleShipBoard<Character>(5, 5,'X');
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> sub = fac.makeSubmarine(p1);
    b.tryAddShip(sub);
    assertEquals(null,b.fireAt(new Coordinate(4,4)));
    assertSame(sub,b.fireAt(new Coordinate(0, 0)));
    assertSame(sub,b.fireAt(new Coordinate(1, 0)));
    assertEquals(true,sub.isSunk());
  }
  @Test
  public void test_whatisatforenemy(){
    Board<Character> b = new BattleShipBoard<Character>(5, 5,'X');
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(0, 0), 'V');
    Ship<Character> sub = fac.makeSubmarine(p1);
    b.tryAddShip(sub);
    b.fireAt(new Coordinate(0,0));
    assertEquals(b.whatIsAtForEnemy(new Coordinate(0,0)),'s');
    b.fireAt(new Coordinate(2,2));
    assertEquals(b.whatIsAtForEnemy(new Coordinate(2,2)),'X');
    assertEquals(b.getmissInfo(),'X');
  }
  @Test
  public void test_allSunk(){
    Board<Character> b = new BattleShipBoard<Character>(5, 5,'X');
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(0, 0), 'V');
    Placement p2 = new Placement(new Coordinate(0, 1), 'V');
    Ship<Character> sub1 = fac.makeSubmarine(p1);
    Ship<Character> sub2 = fac.makeSubmarine(p2);
    b.tryAddShip(sub1);
    b.tryAddShip(sub2);
    b.fireAt(new Coordinate(0,0));
    b.fireAt(new Coordinate(0,1));
    assertEquals(false,b.checklose());
    b.fireAt(new Coordinate(1,0));
    b.fireAt(new Coordinate(1,1));
    assertEquals(true,b.checklose());
  }
    
  @Test
  public void test_moveship(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(5, 5,'X');
    V2ShipFactory fac = new V2ShipFactory();
    Ship<Character> d=fac.makeDestroyer(new Placement(new Coordinate(0,0),'V'));
    Ship<Character> d2=fac.makeDestroyer(new Placement(new Coordinate(1,2),'V'));
    b.tryAddShip(d);
    b.fireAt(new Coordinate(0,0));
    b.addmovehit(b.findship(new Coordinate(0,0)));
    b.remainnewhitmarker(new Coordinate(0,0), new Coordinate(1,2), d);
    assertEquals(b.movehit.containsKey(new Coordinate(0,0)),true);
    assertEquals(b.movehit.get(new Coordinate(0,0)),'d');
    assertEquals(b.movehit.containsKey(new Coordinate(0,2)),false);
    assertEquals(b.hitmap.containsKey(new Coordinate(1,2)),true);
    assertEquals(b.hitmap.get(new Coordinate(1,2)),null);
    b.adddeleteship(d);
    b.fireAt(new Coordinate(0,0));
    assertEquals(b.movehit.containsKey(new Coordinate(0,0)),false);
    b.tryAddShip(d2);
    b.fireAt(new Coordinate(1,2));
    assertEquals(b.hitmap.containsKey(new Coordinate(1,2)),false);
  }
     
}
