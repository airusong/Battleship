package ece651.sp22.as1134.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public interface Board<T> {

  public void remainnewhitmarker(Coordinate upperleft, Coordinate newplace, Ship<T> ship);

  public int getWidth();

  public int getHeight();

  public T getmissInfo();

  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);

  public String tryAddShip(Ship<T> toAdd);

  public Ship<T> fireAt(Coordinate c);

  public Coordinate addmovehit(Ship<T> ship);

  public boolean checklose();

  public Ship<T> findship(Coordinate c);

  public void adddeleteship(Ship<T> ship);
}
