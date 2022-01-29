package ece651.sp22.as1134.battleship;

import java.util.HashMap;

/* @param myPieces to record the coordinate set of the ship and the status of hit or not
 * @param myDisplayInfo to record myship
 * @param enemyDisplayInfo to record enemyship
 */
abstract class BasicShip<T> implements Ship<T> {
  // private final Coordinate myLocation;
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException(c.toString() + "is not inside the pieces");
    }
  }

  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /*
   * @Override public boolean occupiesCoordinates(Coordinate where) { // TODO
   * Auto-generated method stub return where.equals(myLocation); }
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    // TODO Auto-generated method stub
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    for (Coordinate c : myPieces.keySet()) {
      if (myPieces.get(c) == false) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    checkCoordinateInThisShip(where);
    myPieces.replace(where, false, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    // TODO this is not right. We need to
    // look up the hit status of this coordinate
    if (myShip) {
      checkCoordinateInThisShip(where);
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    } else {
      checkCoordinateInThisShip(where);
      return enemyDisplayInfo.getInfo(where,wasHitAt(where));
    }
  }

}
