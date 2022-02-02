package ece651.sp22.as1134.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Constructs a BattleShipBoard with the specified width and height
 * 
 * @param w           is the width of the newly constructed board.
 * @param h           is the height of the newly constructed board.
 * @param enemyMisses is to to track where the enemy has missed
 * @param myShips     is the list of ships that are already on the board
 * @throws IllegalArgumentException if the width or height are less than or
 *                                  equal to zero.
 * 
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final PlacementRuleChecker<T> placementChecker;
  final ArrayList<Ship<T>> myShips;
  HashSet<Coordinate> enemyMisses;
  HashMap<Coordinate, T> movehit;
  HashMap<Coordinate, T> hitmap;
  private final T missInfo;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public T getmissInfo() {
    return missInfo;
  }

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.enemyMisses = new HashSet<>();
    this.hitmap=new HashMap<>();
    this.movehit = new HashMap<>();
    this.placementChecker = placementChecker;
    this.missInfo = missInfo;
  }

  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new NoCollisionRuleChecker<>(new InBoundsRuleChecker<T>(null)), missInfo);
  }

  /*
   * method to add ship on the board
   */
  public String tryAddShip(Ship<T> toAdd) {
    String placementProblem = placementChecker.checkPlacement(toAdd, this);
    if (placementProblem == null) {

      this.myShips.add(toAdd);
      return null;
    } else {
      return placementProblem;
    }
  }

  /*
   * method to check the content of the board,both for enemy and for self
   */
  public T whatIsAtForEnemy(Coordinate where) {
    if(movehit.containsKey(where)){
      return movehit.get(where);
    }else if(hitmap.containsKey(where)){
      return hitmap.get(where);
    }
    else{
      return whatIsAt(where, false);
    }
  }

  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if (isSelf == false && enemyMisses.contains(where) == true) {
      return missInfo;
    }
    return null;

  }

  /**
   * method to fire at enemy ship
   */
  public Ship<T> fireAt(Coordinate c) {
    if (c.getColumn() >= width || c.getRow() >= height) {
      throw new IllegalArgumentException("The coordinate is out of bound");
    }
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        if(enemyMisses.contains(c)){//if the player misses a loaction before but hit a ship at that place again, remove the
          enemyMisses.remove(c);    // coordinate from the enemyMisses set
        }
        if(hitmap.containsKey(c)){
          hitmap.remove(c);     //if the enemy player hit the newly moved place which has a move marker, the board will
        }                       //display, so remove this coordinate from hitmap
        ship.recordHitAt(c);
        return ship;
      }
    }
    if(movehit.containsKey(c)){
      movehit.remove(c);
    }
    enemyMisses.add(c);
    return null;
  }

  /*
   * method to move the ship to a new place
   */
  public Ship<T> findship(Coordinate c) {
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        //addmovehit(ship);
        //      myShips.remove(ship);
        return ship;
      }
    }
    return null;
  }

  /*
   * construct a new hashset to remain the hit marker relatively
   */
  public void remainnewhitmarker(Coordinate upperleft, Coordinate newplace, Ship<T> ship) {
    Iterable<Coordinate> set = ship.getCoordinates();
    for (Coordinate c : set) {
      if (ship.wasHitAt(c)) {
        int deltax = upperleft.getRow() - newplace.getRow();
        int deltay = upperleft.getColumn() - newplace.getColumn();
        Coordinate newhit=new Coordinate(c.getRow() - deltax, c.getColumn() - deltay);
        fireAt(newhit);
        hitmap.put(newhit,null);
      }
    }

  }

  /*
   * method to find the upperleft position of a ship
   *
   */
  public Coordinate addmovehit(Ship<T> ship) {
    Iterable<Coordinate> set = ship.getCoordinates();
    int x = Integer.MAX_VALUE;
    int y = Integer.MAX_VALUE;
    for (Coordinate c : set) {
      if (ship.wasHitAt(c)) {
        movehit.put(c, whatIsAtForEnemy(c));
      }
      x = Math.min(c.getRow(), x);
      y = Math.min(c.getRow(), y);
    }
    return new Coordinate(x, y);
  }

  /*
   * method to readd the ship if the placement is Illegal
   */
  public void adddeleteship(Ship<T> ship) {
    myShips.remove(ship);
  }

  /*
   * method to check if all the ships on the board are sunk, and this player loses
   */
  public boolean checklose() {
    for (Ship<T> ship : myShips) {
      if (ship.isSunk() == false) {
        return false;
      }
    }
    return true;
  }
}
