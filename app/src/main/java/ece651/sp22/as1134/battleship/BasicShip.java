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
  /*
   * method to check if a coordinate is inside of a ship coordinate sets
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException(c.toString() + "is not inside the pieces");
    }
  }
  /*
   *  method to return a ship's coordinate set
   */
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
  /* 
   * method to check if a ship's pieces are all hitted
   */
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
  /*
   * method to record a coordinate inside of a ship is hitted
   */
  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    checkCoordinateInThisShip(where);
    myPieces.replace(where, false, true);
  }
  /*
   * method to get the coordinate inside of a ship is hit or not
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }
  /*
   * method to get the display information inside of a ship, which depends on if it is myShip or enemyShip 
   */
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
