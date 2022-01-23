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
      if (c.getColumn() >= height || c.getColumn() < 0 || c.getRow() >= width || c.getRow() < 0) {
        return false;
      }
    }
    return true;
  }

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

}
