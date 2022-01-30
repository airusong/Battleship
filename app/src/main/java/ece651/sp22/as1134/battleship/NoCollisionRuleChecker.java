package ece651.sp22.as1134.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  /*
   * method to check the ship to place does not collides with other ship. If the coordinate on the board is not null, it's illegal position.
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // TODO Auto-generated method stub
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate c : set) {
      if (theBoard.whatIsAtForSelf(c) != null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

}
