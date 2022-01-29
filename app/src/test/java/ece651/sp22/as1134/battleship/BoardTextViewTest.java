package ece651.sp22.as1134.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2,'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  |  A\n" + "B  |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_3by2(){
    Board<Character> b2 = new BattleShipBoard<Character>(3, 2,'X');
    BoardTextView view = new BoardTextView(b2);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      expectedHeader+
      "A  | |  A\n"+
      "B  | |  B\n"+
      expectedHeader;
      assertEquals(expected, view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_3by5(){
    Board<Character> b3 = new BattleShipBoard<Character>(3, 5,'X');
    BoardTextView view = new BoardTextView(b3);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      expectedHeader+
      "A  | |  A\n"+
      "B  | |  B\n"+
      "C  | |  C\n"+
      "D  | |  D\n"+
      "E  | |  E\n"+
      expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  @Test
   public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20,'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27,'X');
    assertThrows(IllegalArgumentException.class,() ->new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }
 @Test
 public void test_board_view(){
    Board<Character> b3 = new BattleShipBoard<Character>(3, 5,'X');
    BoardTextView view = new BoardTextView(b3);
    String expectedHeader = "  0|1|2\n";
    Coordinate c=new Coordinate(0,0);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    String expected=
      expectedHeader+
      "A s| |  A\n"+
      "B  | |  B\n"+
      "C  | |  C\n"+
      "D  | |  D\n"+
      "E  | |  E\n"+
      expectedHeader;
    b3.tryAddShip(s);
    assertEquals(expected, view.displayMyOwnBoard());
 }
  @Test
  public void test_enemy_board(){
    Board<Character> b = new BattleShipBoard<Character>(4, 3,'X');
    BoardTextView view = new BoardTextView(b);
    Placement p1=new Placement(new Coordinate(1,0),'H');
    Placement p2=new Placement(new Coordinate(0,3),'V');
    V1ShipFactory fac=new V1ShipFactory();
    Ship<Character> sub=fac.makeSubmarine(p1);
    Ship<Character> des=fac.makeDestroyer(p2);
    b.tryAddShip(sub);
    b.tryAddShip(des);
    String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    assertEquals(myView, view.displayMyOwnBoard());
    b.fireAt(new Coordinate(0,0));
    b.fireAt(new Coordinate(1,0));
       String enemyView =
      "  0|1|2|3\n" +
      "A X| | |  A\n" +
      "B s| | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";
    assertEquals(enemyView,view.displayEnemyBoard());
  }
  @Test
  public void display_two_board(){
    Board<Character>  myBoard= new BattleShipBoard<Character>(10,20,'X');
    Board<Character>  enemyBoard= new BattleShipBoard<Character>(10,20,'X');
    V1ShipFactory fac = new V1ShipFactory();
    Placement p1=new Placement(new Coordinate(0,0), 'V');
    Placement p2=new Placement(new Coordinate(0,2), 'V');
    Ship<Character> sub1=fac.makeSubmarine(p1);
    Ship<Character> sub2=fac.makeSubmarine(p2);
    Placement p3=new Placement(new Coordinate(5,0), 'H');
    Placement p4=new Placement(new Coordinate(3,3), 'V');
    Ship<Character> battle1=fac.makeBattleship(p3);
    Ship<Character> battle2=fac.makeBattleship(p4);
    myBoard.tryAddShip(sub1);
    myBoard.tryAddShip(sub2);
    enemyBoard.tryAddShip(battle1);
    enemyBoard.tryAddShip(battle2);
    BoardTextView myview=new BoardTextView(myBoard);
    BoardTextView enemyview=new BoardTextView(enemyBoard);
    enemyBoard.fireAt(new Coordinate(5,0));
    enemyBoard.fireAt(new Coordinate(0,0));
    myBoard.fireAt(new Coordinate(0,0));
    String res=myview.displayMyBoardWithEnemyNextToIt(enemyview, "Your ocean","Player B's ocean");
    String expected=
"     Your ocean                           Player B's ocean\n"+
"  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n"+   
"A *| |s| | | | | | |  A                A X| | | | | | | | |  A\n"+
"B s| |s| | | | | | |  B                B  | | | | | | | | |  B\n"+
"C  | | | | | | | | |  C                C  | | | | | | | | |  C\n"+
"D  | | | | | | | | |  D                D  | | | | | | | | |  D\n"+
"E  | | | | | | | | |  E                E  | | | | | | | | |  E\n"+
"F  | | | | | | | | |  F                F b| | | | | | | | |  F\n"+
"G  | | | | | | | | |  G                G  | | | | | | | | |  G\n"+
"H  | | | | | | | | |  H                H  | | | | | | | | |  H\n"+
"I  | | | | | | | | |  I                I  | | | | | | | | |  I\n"+
"J  | | | | | | | | |  J                J  | | | | | | | | |  J\n"+
"K  | | | | | | | | |  K                K  | | | | | | | | |  K\n"+
"L  | | | | | | | | |  L                L  | | | | | | | | |  L\n"+
"M  | | | | | | | | |  M                M  | | | | | | | | |  M\n"+
"N  | | | | | | | | |  N                N  | | | | | | | | |  N\n"+
"O  | | | | | | | | |  O                O  | | | | | | | | |  O\n"+
"P  | | | | | | | | |  P                P  | | | | | | | | |  P\n"+
"Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n"+
"R  | | | | | | | | |  R                R  | | | | | | | | |  R\n"+
"S  | | | | | | | | |  S                S  | | | | | | | | |  S\n"+
"T  | | | | | | | | |  T                T  | | | | | | | | |  T\n"+
"  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(expected,res);
  }
}
