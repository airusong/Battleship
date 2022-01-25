package ece651.sp22.as1134.battleship;

import java.util.HashSet;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // TODO Auto-generated method stub
    Iterable<Coordinate> set = theShip.getCoordinates();
    int height = theBoard.getHeight();
    int width = theBoard.getWidth();
    for (Coordinate c : set) {
      if (c.getColumn() >= width || c.getColumn() < 0 || c.getRow() >= height || c.getRow() < 0) {
        System.err.println(c + "is out of bounds:" +c.getColumn() + "/ "+ height + " : "  + c.getRow() + "/"+ width);
        return false;
      }
    }
    return true;
  }

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

}
