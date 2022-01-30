package ece651.sp22.as1134.battleship;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public T getmissInfo();

  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);

  public String tryAddShip(Ship<T> toAdd);

  public Ship<T> fireAt(Coordinate c);

  public boolean checklose();
}
