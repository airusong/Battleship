package ece651.sp22.as1134.battleship;

import java.util.HashSet;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // TODO Auto-generated method stub
    Iterable<Coordinate> set = theShip.getCoordinates();
    int height = theBoard.getHeight();
    int width = theBoard.getWidth();
    for (Coordinate c : set) {
      if (c.getColumn() >= width) {
        return "That placement is invalid: the ship goes off the right of the board.";
      }
      if (c.getColumn() < 0) {
        return "That placement is invalid: the ship goes off the left of the board.";
      }
      if (c.getRow() >= height) {
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if (c.getRow() < 0) {
        return "That placement is invalid: the ship goes off the top of the board.";
      }
    }
    return null;
  }

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

}
