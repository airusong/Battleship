package ece651.sp22.as1134.battleship;

import java.util.ArrayList;
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
  private final T missInfo;
  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
  public T getmissInfo(){
    return missInfo;
  }
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker,T missInfo) {
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
    this.placementChecker = placementChecker;
    this.missInfo=missInfo;
  }

  public BattleShipBoard(int w, int h,T missInfo) {
    this(w, h, new NoCollisionRuleChecker<>(new InBoundsRuleChecker<T>(null)),missInfo);
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
    return whatIsAt(where, false);
  }
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where,isSelf);
      }
    }
    if(isSelf==false && enemyMisses.contains(where)==true){
      return missInfo;
    }
    return null;

  }

  /**
   * method to fire at enemy ship
   */
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        return ship;
      }
    }
    enemyMisses.add(c);
    return null;
  }

}
