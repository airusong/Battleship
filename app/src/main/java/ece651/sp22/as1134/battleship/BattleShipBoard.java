package ece651.sp22.as1134.battleship;

import java.util.ArrayList;

/**
 * Constructs a BattleShipBoard with the specified width and height
 * 
 * @param w is the width of the newly constructed board.
 * @param h is the height of the newly constructed board.
 * @throws IllegalArgumentException if the width or height are less than or
 *                                  equal to zero.
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final PlacementRuleChecker<T> placementChecker;
  final ArrayList<Ship<T>> myShips;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.placementChecker = placementChecker;
  }

  public BattleShipBoard(int w, int h) {
    this(w, h, new NoCollisionRuleChecker<>(new InBoundsRuleChecker<T>(null)));
  }

  public String tryAddShip(Ship<T> toAdd) {
    String placementProblem = placementChecker.checkPlacement(toAdd, this);
    if (placementProblem==null) {

      this.myShips.add(toAdd);
      return null;
    } else {
      return placementProblem;
    }
  }

  public T whatIsAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }

}
