package ece651.sp22.as1134.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  // private final Coordinate myLocation;
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  /*
   * public BasicShip(Coordinate myLocation){ this.myLocation=myLocation; }
   */
  /*
  public BasicShip(Coordinate c) {
    myPieces=new HashMap<Coordinate,Boolean>();
    myPieces.put(c, false);
  }
  */
  public BasicShip(Iterable<Coordinate> where,ShipDisplayInfo<T> myDisplayInfo) {
    this.myDisplayInfo=myDisplayInfo;
     myPieces=new HashMap<Coordinate,Boolean>();          
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
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
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }

}
