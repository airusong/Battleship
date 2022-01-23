package ece651.sp22.as1134.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  final String name;
  public String getName(){
    return name;
  }
  public RectangleShip(String name,Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo);
    this.name=name;
  }

  public RectangleShip(String name,Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name,upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship",upperLeft, 1, 1, data, onHit);
  }

  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> rectangle = new HashSet<Coordinate>();
    int x = upperLeft.getColumn();
    int y = upperLeft.getRow();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        rectangle.add(new Coordinate(y + j, x + i));
      }
    }
    return rectangle;
  }
}
