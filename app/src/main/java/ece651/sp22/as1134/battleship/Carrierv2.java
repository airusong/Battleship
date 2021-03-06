package ece651.sp22.as1134.battleship;

import java.util.HashSet;

public class Carrierv2<T> extends BasicShip<T> {
  final String name;

  public String getName() {
    return name;
  }

  public Carrierv2(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> eneDisplayInfo) {
    super(makeCoords(upperLeft, orientation), myDisplayInfo, eneDisplayInfo);
    this.name = name;
  }

  public Carrierv2(String name, Coordinate upperLeft, char orientation, T data, T onHit) {
    this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation) {
    HashSet<Coordinate> carrierset = new HashSet<Coordinate>();
    int x = upperLeft.getColumn();
    int y = upperLeft.getRow();
    if (orientation == 'U') {
      carrierset.add(new Coordinate(y, x));
      carrierset.add(new Coordinate(y + 1, x));
      carrierset.add(new Coordinate(y + 2, x));
      carrierset.add(new Coordinate(y + 3, x));
      carrierset.add(new Coordinate(y + 2, x + 1));
      carrierset.add(new Coordinate(y + 3, x + 1));
      carrierset.add(new Coordinate(y + 4, x + 1));
    } else if (orientation == 'R') {
      carrierset.add(new Coordinate(y, x + 1));
      carrierset.add(new Coordinate(y, x + 2));
      carrierset.add(new Coordinate(y, x + 3));
      carrierset.add(new Coordinate(y, x + 4));
      carrierset.add(new Coordinate(y + 1, x));
      carrierset.add(new Coordinate(y + 1, x + 1));
      carrierset.add(new Coordinate(y + 1, x + 2));
    } else if (orientation == 'D') {
      carrierset.add(new Coordinate(y, x));
      carrierset.add(new Coordinate(y + 1, x));
      carrierset.add(new Coordinate(y + 2, x));
      carrierset.add(new Coordinate(y + 1, x + 1));
      carrierset.add(new Coordinate(y + 2, x + 1));
      carrierset.add(new Coordinate(y + 3, x + 1));
      carrierset.add(new Coordinate(y + 4, x + 1));
    } else if (orientation == 'L') {
      carrierset.add(new Coordinate(y, x + 2));
      carrierset.add(new Coordinate(y, x + 3));
      carrierset.add(new Coordinate(y, x + 4));
      carrierset.add(new Coordinate(y + 1, x));
      carrierset.add(new Coordinate(y + 1, x + 1));
      carrierset.add(new Coordinate(y + 1, x + 2));
      carrierset.add(new Coordinate(y + 1, x + 3));
    }else{
      throw new IllegalArgumentException("The orientation does not match this kind of ship");
    }
    return carrierset;
  }
}
