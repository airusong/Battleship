package ece651.sp22.as1134.battleship;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public T whatIsAt(Coordinate where);

  public String tryAddShip(Ship<T> toAdd);
}
