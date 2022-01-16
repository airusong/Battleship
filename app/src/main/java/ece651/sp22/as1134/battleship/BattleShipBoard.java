package ece651.sp22.as1134.battleship;

interface Board{
  public int getWidth();

  public int getHeight();
}
public class BattleShipBoard implements Board{
  private final int width;
  private final int height;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public BattleShipBoard(int w, int h) {
      if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
  }
}
