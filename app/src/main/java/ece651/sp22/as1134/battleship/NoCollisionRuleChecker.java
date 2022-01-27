package ece651.sp22.as1134.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // TODO Auto-generated method stub
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate c : set) {
      if (theBoard.whatIsAt(c) != null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

}
