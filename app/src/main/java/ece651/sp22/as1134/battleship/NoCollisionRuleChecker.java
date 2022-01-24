package ece651.sp22.as1134.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // TODO Auto-generated method stub
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate c : set) {
      if (theBoard.whatIsAt(c) != null) {
        return false;
      }
    }
    return true;
  }

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

}
